package com.besuikerd.autologistics.common.lib.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import cpw.mods.fml.common.network.ByteBufUtils;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ReportedException;

public class NBTUtils {
	/**
	 * reads the keyset of an nbt tag
	 * @param nbt
	 * @return
	 */
	public static Set<String> keyset(NBTTagCompound nbt){
		ByteBuf buf = Unpooled.buffer();
		DataOutput out = new ByteBufOutputStream(buf);
		try {
			CompressedStreamTools.write(nbt, out);
			DataInput in = new ByteBufInputStream(buf);
			
			in.readByte();
			in.readUTF();
			
			Set<String> keyset = new HashSet<String>();
			int type;
			while(true){
				type = in.readByte();
				if(type == 0){ 
					break;
				}
				String key = in.readUTF();
				NBTBase value = readFromType(in, type);
				keyset.add(key);
			}
			return keyset;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static NBTBase readFromType(DataInput in, int type) throws IOException{
		switch (type)
        {
            case 0:
                return new NBTTagEnd();
            case 1:
                return new NBTTagByte(in.readByte());
            case 2:
                return new NBTTagShort(in.readShort());
            case 3:
                return new NBTTagInt(in.readInt());
            case 4:
                return new NBTTagLong(in.readLong());
            case 5:
                return new NBTTagFloat(in.readFloat());
            case 6:
                return new NBTTagDouble(in.readDouble());
            case 7:
            	int length = in.readInt();
                byte[] array = new byte[length];
                for(int i = 0 ; i < array.length ; i++){
                	array[i] = in.readByte();
                }
                return new NBTTagByteArray(array);
            case 8:
                return new NBTTagString(in.readUTF());
            case 9:
            	type = in.readByte();
            	length = in.readInt();
            	NBTTagList list = new NBTTagList();
            	for(int i = 0 ; i < length ; i++){
            		list.appendTag(readFromType(in, type));
            	}
            	return list;
            case 10:
            	NBTTagCompound tag = new NBTTagCompound();
            	while((type = in.readByte()) != 0){
            		tag.setTag(in.readUTF(), readFromType(in, type));
            	}
            	return tag;
            case 11:
            	length = in.readInt();
            	int[] intArray = new int[length];
            	for(int i = 0 ; i < intArray.length ; i++){
            		intArray[i] = in.readInt();
            	}
            	return new NBTTagIntArray(intArray);
            default:
                return null;
        }
	}
}
