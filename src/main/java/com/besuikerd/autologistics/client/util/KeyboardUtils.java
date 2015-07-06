package com.besuikerd.autologistics.client.util;

import static org.lwjgl.input.Keyboard.*;

public class KeyboardUtils {
    public static boolean isShiftKeyDown(){
        return isKeyDown(KEY_LSHIFT) || isKeyDown(KEY_RSHIFT);
    }

    public static boolean isCtrlKeyDown(){
        return isKeyDown(KEY_LCONTROL) || isKeyDown(KEY_RCONTROL) || isKeyDown(KEY_LMETA) || isKeyDown(KEY_RMETA);
    }
}
