package com.besuikerd.autologistics.common.tile.logistic;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.*;

public class LazyTileFinder<A> implements Iterator<A> {
    private Class<A> cls;
    private Class<?> cableCls;
    private World world;
    private TileEntity current;
    private A next;
    private Set<TileEntity> processed;
    private Stack<TileEntity> toProcess;
    private List<A> found;

    private int currentFacing;

    public LazyTileFinder(Class<A> cls, Class<?> cableCls, TileEntity start) {
        this.cls = cls;
        this.cableCls = cableCls;
        this.world = start.getWorldObj();
        this.current = start;
        this.currentFacing = 0;
        this.processed = new HashSet<TileEntity>();
        this.toProcess = new Stack<TileEntity>();
        this.found = new ArrayList<A>();

        if(cls.isInstance(start)){
            this.next = cls.cast(start);
            processed.add(start);
        } else{
            findNext();
        }
    }

    private void findNext(){
        while(next == null){
            if(currentFacing < 6){ //we haven't checked out all sides yet
                EnumFacing facing = EnumFacing.values()[currentFacing++];
                TileEntity tile = world.getTileEntity(current.xCoord + facing.getFrontOffsetX(), current.yCoord + facing.getFrontOffsetY(), current.zCoord + facing.getFrontOffsetZ());
                if(cls.isInstance(tile) && !processed.contains(tile)){
                    processed.add(tile);
                    next = cls.cast(tile);
                }
                if(cableCls.isInstance(tile) && !processed.contains(tile)){
                    toProcess.push(tile);
                    processed.add(tile);
                }
            } else {
                currentFacing = 0;
                if(!toProcess.isEmpty()){
                    current = toProcess.pop();
                } else{
                    break;
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public A next() {
        A next = this.next;
        found.add(next);
        this.next = null;
        findNext();
        return next;
    }

    public List<A> allValues(){
        while(hasNext()){
            next();
        }
        return found;
    }
}
