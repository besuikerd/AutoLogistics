package com.besuikerd.autologistics.client.render.font;

import com.besuikerd.autologistics.client.lib.gui.element.Element;
import com.besuikerd.autologistics.client.render.Colors;
import com.besuikerd.autologistics.client.render.Fonts;
import com.besuikerd.autologistics.client.render.SingleDynamicTexture;
import com.besuikerd.autologistics.common.lib.util.tuple.Tuple2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import scala.xml.Elem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.lwjgl.opengl.GL11.*;

public class DefaultFontRenderer implements IFontRenderer {

    private TextureManager textureManager;
    private FontMetrics fontMetrics;

    private UVMapping2D[] glyphs;
    private String fontId;
    private ResourceLocation textureLocation;
    private int from;

    private int color;
    private float scaleX;
    private float scaleY;
    private int horizontalSpacing;
    private int verticalSpacing;

    public DefaultFontRenderer(Font font, int from, int to){
        this.color = 0xffffffff;
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.horizontalSpacing = 2;
        this.verticalSpacing = 2;
        this.from = from;

        Tuple2<BufferedImage, GlyphData> imageAndData = createImage(font, from, to);
        this.fontMetrics = imageAndData._1.getGraphics().getFontMetrics();
        this.glyphs = imageAndData._2.uvMappings;

        this.fontId = font.getFontName() + font.getSize();

        try{
            ImageIO.write(imageAndData._1, "png", new File("C:\\Users\\Nick\\Pictures\\img.png"));
        }catch(Exception e){

        }

        this.textureManager = Minecraft.getMinecraft().getTextureManager();
        this.textureLocation = textureManager.getDynamicTextureLocation(fontId, new SingleDynamicTexture(imageAndData._1));
    }

    public static void main(String[] args) {
        new DefaultFontRenderer(new Font("Consolas", Font.TRUETYPE_FONT, 40));
    }

    public DefaultFontRenderer(Font font){
        this(font, 32, 126);
    }

    public Tuple2<BufferedImage, GlyphData> createImage(Font font, int from, int to) {
        int numberOfCharacters = to - from + 1;

        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, 1, 1);
        g.setColor(Color.white);
        this.fontMetrics = g.getFontMetrics();


        char[] allChars = new char[numberOfCharacters];
        for(int i = from ; i <= to ; i++){
            allChars[i - from] = (char) i;
        }

        int dim = (int) Math.sqrt(fontMetrics.stringWidth(new String(allChars)) * fontMetrics.getHeight()) + fontMetrics.getHeight();

        dim = 256;

        img = new BufferedImage(dim, dim, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, dim, dim);
        g.setColor(Color.white);


        GlyphData glyphData = drawGlyphs(g, dim, from, to);

        float spaceLeft = (float) (dim - glyphData.totalHeight) / dim;

        if(spaceLeft > 0.5){
            dim = (int) Math.sqrt(glyphData.totalHeight * dim) + g.getFontMetrics().getHeight();

            img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            g = (Graphics2D) img.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(font);
            g.setColor(new Color(255, 255, 255, 0));
            g.fillRect(0, 0, 1, 1);
            g.setColor(Color.white);


            drawGlyphs(g, dim, from, to);
        }
        return Tuple2.create(img, glyphData);
    }

    private BufferedImage createEmpty(int width, int height, Font font){
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D imgGraphics = (Graphics2D) img.getGraphics();
        imgGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        imgGraphics.setFont(font);
        return img;
    }

    public GlyphData drawGlyphs(Graphics g, int dim, int from, int to){
        FontMetrics metrics = g.getFontMetrics();
        int charHeight = metrics.getHeight() - metrics.getMaxDescent();
        int xOffset = 0;
        int yOffset = charHeight; //metrics.getMaxAscent() + metrics.getMaxDescent() + metrics.getHeight() / 2;

        UVMapping2D[] uvMappings = new UVMapping2D[to - from + 1];

        g.setColor(Color.white);
        for (int c = from ; c <= to ; c++) {
            int charWidth = metrics.charWidth(c);
            if(xOffset + charWidth >= dim){
                xOffset = 0;
                yOffset += charHeight;
            }
            uvMappings[c - from] = new UVMapping2D(xOffset, yOffset - charHeight + metrics.getMaxDescent(), xOffset + charWidth, yOffset + metrics.getMaxDescent());
            g.drawString("" + (char) c, xOffset, yOffset);
            xOffset += charWidth;
        }
        int totalHeight = yOffset + metrics.getDescent();
        return new GlyphData(uvMappings, totalHeight, charHeight);
    }


    @Override
    public void renderStringWithShadow(Gui gui, String text, int x, int y) {
        prepareRender();
        int darker = Colors.darken(color, 0.2f);

        prepareColor(darker);
        renderString0(gui, text, x + 1, y + 1);

        prepareColor(color);
        renderString0(gui, text, x, y);
    }



    @Override
    public void renderString(Gui gui, String text, int x, int y) {
        prepareRender();
        renderString0(gui, text, x, y);
    }

    private void renderString0(Gui gui, String s, int x, int y){
        int xOffset = x;
        int yOffset = y;
        for(int i = 0 ; i < s.length() ; i++){
            char c = s.charAt(i);
            if(c == '\\'){
                switch(s.charAt(i + 1)){
                    case 'n':
                        yOffset += fontMetrics.getHeight() - fontMetrics.getMaxDescent();
                        break;
                }
                i++;
            } else{
                UVMapping2D mapping = renderChar0(gui, c, xOffset, yOffset);
                xOffset += mapping.u - mapping.x;
            }
        }
    }

    private UVMapping2D renderChar0(Gui gui, char character, int x, int y){
        char c = (char) (character - from);

        UVMapping2D mapping = c < glyphs.length ? glyphs[c] : glyphs[0];

        ((Element) gui).drawRectangle(x, y, mapping.u - mapping.x, mapping.v - mapping.y, 0xff0000ff);
        prepareRender();
        gui.drawTexturedModalRect(x, y, mapping.x, mapping.y, mapping.u - mapping.x, mapping.v - mapping.y);
        return mapping;
    }

    @Override
    public void renderChar(Gui gui, char character, int x, int y) {
        prepareRender();
        renderChar0(gui, character, x, y);
    }

    private void prepareRender(){
        textureManager.bindTexture(textureLocation);
        prepareColor(this.color);

        //TODO scaling
    }

    private void prepareColor(int color){
        float red = (float)(color >> 24 & 0xff) / 255F;
        float green = (float)(color >> 16 & 0xff) / 255F;
        float blue = (float)(color >> 8 & 0xff) / 255F;
        float alpha = (float)(color & 0xff) / 255F;
        glColor4f(red, green, blue, alpha);
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public float getScaleX() {
        return scaleX;
    }

    @Override
    public float getScaleY() {
        return scaleY;
    }

    @Override
    public void setHorizontalSpacing(int amount) {
        this.horizontalSpacing = amount;
    }

    @Override
    public int getHorizontalSpacing() {
        return horizontalSpacing;
    }

    @Override
    public void setVerticalSpacing(int amount) {
        this.verticalSpacing = amount;
    }

    @Override
    public int getVerticalSpacing() {
        return verticalSpacing;
    }

    @Override
    public int measureStringWidth(String s) {
        return fontMetrics.stringWidth(s);
    }

    @Override
    public int measureStringHeight(String s) {
        return fontMetrics.stringWidth(s);
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }
}
