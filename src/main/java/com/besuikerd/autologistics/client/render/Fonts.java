package com.besuikerd.autologistics.client.render;

import com.besuikerd.autologistics.client.render.font.DefaultFontRenderer;
import com.besuikerd.autologistics.client.render.font.IFontRenderer;
import com.besuikerd.autologistics.common.BLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Fonts {

    public static final ResourceLocation FONT_LOCATION = new ResourceLocation("autologistics", "fonts/consola.ttf");
    public static final IFontRenderer CONSOLAS;
    public static final IFontRenderer COURIER;

    static{
        CONSOLAS = new DefaultFontRenderer(tryCreateFont(new ResourceLocation(FONT_LOCATION.toString(), "/consola.ttf"), "Consolas").deriveFont(40f));
        COURIER = new DefaultFontRenderer(tryCreateFont(new ResourceLocation(FONT_LOCATION.toString(), "/consola.ttf"), "Courier New").deriveFont(40f));
    }

    public static Font tryCreateFont(InputStream is, String fallback){
        try {
            return Font.createFont(Font.TRUETYPE_FONT, is);
        } catch(IOException e){
            BLogger.warn("Could not load font (%s), falling back to system font %s", e.getMessage(), fallback);
        } catch(FontFormatException e){
            BLogger.warn("Could not load font (%s), falling back to system font %s", e.getMessage(), fallback);
        }
        return fallback(fallback);
    }

    public static Font tryCreateFont(ResourceLocation resource, String fallback){
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(resource).getInputStream();
            Font f = tryCreateFont(is, fallback);
            is.close();
            return f;
        } catch (IOException e) {
            BLogger.warn("Could not load font (%s), falling back to system font %s", e.getMessage(), fallback);
            e.printStackTrace();
        }
        return fallback(fallback);
    }

    public static Font tryCreateFont(String path, String fallback){
        try {
            return tryCreateFont(new FileInputStream(path), fallback);
        } catch (FileNotFoundException e) {
            BLogger.warn("Could not load font (%s), falling back to system font %s", e.getMessage(), fallback);
        }
        return fallback(fallback);
    }

    public static Font fallback(String name){
        return new Font(name, Font.TRUETYPE_FONT, 20);
    }
}
