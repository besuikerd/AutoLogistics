package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.lib.gui.event.Trigger;
import com.besuikerd.autologistics.client.lib.gui.styler.ElementStylerTexture;
import com.besuikerd.autologistics.client.lib.gui.texture.Texture;
import com.besuikerd.autologistics.client.lib.gui.texture.scalable.ScalableTexture;

public class ElementCheckbox extends Element{

        protected boolean checked;
        
        public ElementCheckbox(boolean checked) {
                super(16, 16);
                this.styler = new ElementStylerTexture(Texture.CHECK_MARK){
                        @Override
                        public void style(Element e) {
                                if(isChecked()){
                                        super.style(e);
                                }
                        }
                };
                this.checked = checked;
        }

        public void onChecked(ElementRootContainer root, boolean checked){
                doTrigger(Trigger.CHECKED, checked);
        }
        
        public boolean isChecked() {
                return checked;
        }
        
        @Override
        public void draw() {
                drawBackgroundFromTextures(ScalableTexture.SLOT);
                super.draw();
        }
        
        @Override
        protected void onReleased(int x, int y, int which) {
                this.checked = !checked;
                onChecked(getRoot(), checked);
                super.onPressed(x, y, which);
        }
}