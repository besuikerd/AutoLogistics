package com.besuikerd.autologistics.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Besuikerd's client only Logger
 * @author Besuikerd
 *
 */
public class ClientLogger {

	public static final Logger logger = LogManager.getLogger(ModConstants.modName());
	public static final Side side = Side.CLIENT;

	public static void log(Level level, Object msg, Object... params){
		Side effectiveSide = FMLCommonHandler.instance().getEffectiveSide();
		if(effectiveSide == side){
			logger.log(level, String.format(msg.toString(), params));
		}
	}

	public static void log(Level level, Object msg){
		Side effectiveSide = FMLCommonHandler.instance().getEffectiveSide();
		if(effectiveSide == side){
			logger.log(level, msg);
		}
	}

	public static void warn(Object msg, Object... params){
		log(Level.WARN, msg, params);
	}

	public static void warn(Object msg){
		log(Level.WARN, msg);
	}

	public static void error(Object msg, Object... params){
		log(Level.ERROR, msg, params);
	}

	public static void error(Object msg){
		log(Level.ERROR, msg);
	}

	public static void info(Object msg, Object... params){
		log(Level.INFO, msg, params);
	}

	public static void info(Object msg){
		log(Level.INFO, msg);
	}

	public static void debug(Object msg, Object... params){
		log(Level.DEBUG, msg, params);
	}

	public static void debug(String msg){
		log(Level.DEBUG, msg);
	}

	public static void all(Object msg, Object... params){
		log(Level.ALL, msg, params);
	}

	public static void all(String msg){
		log(Level.ALL, msg);
	}

	public static void fatal(Object msg, Object... params){
		log(Level.FATAL, msg, params);
	}

	public static void fatal(String msg){
		log(Level.FATAL, msg);
	}

	public static void trace(Object msg, Object... params){
		log(Level.TRACE, msg, params);
	}

	public static void trace(String msg){
		log(Level.TRACE, msg);
	}
}
