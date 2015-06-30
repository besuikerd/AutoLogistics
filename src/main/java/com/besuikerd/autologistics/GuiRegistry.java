package com.besuikerd.autologistics;

import com.besuikerd.autologistics.client.lib.gui.GuiBase;
import com.besuikerd.autologistics.client.lib.gui.IGuiEntry;
import com.besuikerd.autologistics.common.lib.registry.ListRegistry;
import com.besuikerd.autologistics.common.lib.util.ReflectUtils;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class GuiRegistry extends ListRegistry<IGuiEntry> implements IGuiHandler{
    public static final GuiRegistry instance = new GuiRegistry();

    private Map<Integer, IGuiEntry> idToGuiEntryMapping;
    private Map<String, Integer> guiNameToIdMapping;
    private int idOffset;

    public GuiRegistry() {
        this.idToGuiEntryMapping = new HashMap<Integer, IGuiEntry>();
        this.guiNameToIdMapping = new HashMap<String, Integer>();
        this.idOffset = 0;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Container container = null;
        IGuiEntry entry;
        if((entry  = idToGuiEntryMapping.get(ID)) != null && entry.getContainerClass() != null){
            container = ReflectUtils.newInstance(entry.getContainerClass());
            entry.getBinder().bind(null, container, player, world, x, y, z);
        }
        return container;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Container container = null;
        IGuiEntry entry;
        if((entry  = idToGuiEntryMapping.get(ID)) != null){
            GuiBase gui = ReflectUtils.newInstance(entry.getGuiClass());
            if(entry.getContainerClass() != null){
                container = ReflectUtils.newInstance(entry.getContainerClass());
            }
            return entry.getBinder().bind(gui, container, player, world, x, y, z);
        }
        return null;
    }

    @Override
    public void register(IGuiEntry iGuiEntry) {
        super.register(iGuiEntry);
        int offset = this.idOffset++;
        idToGuiEntryMapping.put(offset, iGuiEntry);
        guiNameToIdMapping.put(iGuiEntry.getName(), offset);
    }

    public int getGuiId(String name){
        return guiNameToIdMapping.get(name);
    }


}
