package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.lib.gui.styler.ElementStylerText;
import com.besuikerd.autologistics.client.lib.gui.styler.IElementStyler;
import com.besuikerd.autologistics.client.lib.gui.texture.ElementState;
import com.besuikerd.autologistics.client.lib.gui.texture.IStateFulBackground;
import com.besuikerd.autologistics.client.lib.gui.texture.StateFulBackground;
import com.besuikerd.autologistics.common.BLogger;


public class ElementButton extends ElementStatefulBackground{
        
        public ElementButton(int x, int y, int width, int height, IStateFulBackground<ElementState> bg, IElementStyler styler) {
                super(bg, x, y, width, height);
                this.styler = styler;
        }
        
        public ElementButton(int width, int height, IStateFulBackground<ElementState> bg, IElementStyler styler) {
                this(0, 0, width, height, bg, styler);
        }
        
        
        public ElementButton(int x, int y, int width, int height, IElementStyler styler) {
                this(x, y, width, height, StateFulBackground.BUTTON, styler);
        }
        
        public ElementButton(int width, int height, IElementStyler styler) {
                this(0, 0, width, height, styler);
        }
        
        public ElementButton(int width, int height) {
                this(0, 0, width, height, (IElementStyler) null);
        }
        
        public ElementButton(int x, int y, int width, int height, String text) {
                this(x, y, width, height, new ElementStylerText(text));
        }
        
        public ElementButton(int width, int height, String text) {
                this(0, 0, width, height, text);
        }
        
        public ElementButton(String text){
                this(0, 0, 0, 0, text);
                this.width = fontRenderer.getStringWidth(text) + 12;
                this.height = fontRenderer.FONT_HEIGHT + 6;
        }
        
        @Override
        protected boolean onPressed(int x, int y, int which) {
                super.onPressed(x, y, which);
                BLogger.debug("onPressed (%d, %d, %d)", x, y, which);
                getRoot().requestFocus(this);
                return true;
        }
}