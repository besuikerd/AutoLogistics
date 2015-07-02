package com.besuikerd.autologistics.client.render;

import net.minecraft.client.renderer.texture.DynamicTexture;

import java.awt.image.BufferedImage;

public class SingleDynamicTexture extends DynamicTexture
{
    public SingleDynamicTexture(BufferedImage image) {
        super(image);
    }

    @Override
    public void updateDynamicTexture() {
        super.updateDynamicTexture();
    }
}
