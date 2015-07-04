package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.lib.gui.layout.HorizontalLayout;
import com.besuikerd.autologistics.client.lib.gui.layout.Orientation;
import com.besuikerd.autologistics.client.lib.gui.layout.VerticalLayout;
import com.besuikerd.autologistics.client.lib.gui.styler.ElementStylerTexture;
import com.besuikerd.autologistics.client.lib.gui.texture.StateFulBackground;
import com.besuikerd.autologistics.client.lib.gui.texture.Texture;
import com.besuikerd.autologistics.client.lib.gui.texture.scalable.IScalableTexture;
import com.besuikerd.autologistics.client.lib.gui.texture.scalable.ScalableTexture;
import com.besuikerd.autologistics.client.lib.gui.texture.scalable.ScalableTextureVerticalScroller;
import com.besuikerd.autologistics.common.lib.util.MathUtil;

public class ElementScrollBar extends ElementContainer {

    protected ElementScrollBar self = this;

    protected ElementContainer containerScroller;
    protected ElementScroller scroller;
    protected ElementButton buttonUp;
    protected ElementButton buttonDown;

    protected Orientation orientation;

    /**
     * number between 0 and 1 that defines how far down this scrollbar is scrolled
     */
    protected double progress;

    public ElementScrollBar(int dimension, Orientation orientation) {
        boolean vertical = orientation == Orientation.VERTICAL;

        this.layout = vertical ? new VerticalLayout() : new HorizontalLayout();
        this.orientation = orientation;

        int buttonDimension = 11;

        this.buttonUp = new ElementButton(vertical ? dimension : buttonDimension, vertical ? buttonDimension : dimension, StateFulBackground.SOLID_BUTTON, new ElementStylerTexture(vertical ? Texture.ARROW_UP : Texture.ARROW_LEFT)) {
            @Override
            protected boolean onPressed(int x, int y, int which) {
                self.onScrolled(x, y, 120);
                return true;
            }
        };
        this.buttonDown = new ElementButton(vertical ? dimension : buttonDimension, vertical ? buttonDimension : dimension, StateFulBackground.SOLID_BUTTON, new ElementStylerTexture(vertical ? Texture.ARROW_DOWN : Texture.ARROW_RIGHT)) {
            @Override
            protected boolean onPressed(int x, int y, int which) {
                self.onScrolled(x, y, -120);
                return true;
            }
        };
        this.containerScroller = new ElementScrollerContainer(vertical ? dimension : 0, vertical ? 0 : dimension);
        this.scroller = new ElementScroller(vertical ? dimension : 0, vertical ? 0 : dimension);
        this.containerScroller.add(scroller);
        add(buttonUp);
        add(containerScroller);
        add(buttonDown);

        switch(orientation){
            case HORIZONTAL:
                this.width = dimension;
                break;
            case VERTICAL:
                this.height = dimension;
                break;
        }
    }

    public int getScrollerSize() {
        return orientation == Orientation.VERTICAL ? containerScroller.height / 4 : containerScroller.width / 4;
    }

    public ElementScrollBar(int width) {
        this(width, Orientation.VERTICAL);
    }

    @Override
    public void dimension() {
        super.dimension();
    }

    @Override
    public void draw() {

        if (orientation == Orientation.VERTICAL) {
            containerScroller.height = parent.getHeight() - buttonUp.getHeight() - buttonDown.getHeight() - parent.paddingTop - parent.paddingBottom;
            scroller.height = getScrollerSize();
        } else {
            containerScroller.width = parent.getWidth() - buttonUp.getWidth() - buttonDown.getWidth() - parent.paddingLeft - parent.paddingRight;
            scroller.width = getScrollerSize();
        }

        super.draw();
    }

    private class ElementScrollerContainer extends ElementStyledContainer {


        public ElementScrollerContainer(int width, int height) {
            super(width, height, ScalableTexture.SLOT);
            padding(0); //no padding is fine
        }

        @Override
        protected boolean onPressed(int x, int y, int which) {
            if (self.isEnabled() && !MathUtil.inRange2D(x, scroller.x, scroller.x + scroller.width, y, scroller.y, scroller.y + scroller.height)) { // only do this when scroller isn't in range

                if (orientation == Orientation.VERTICAL) {
                    setProgress((double) (y - scroller.height / 2) / (height - scroller.height));
                } else {
                    setProgress((double) (x - scroller.width / 2) / (width - scroller.width));
                }
            }
            return true;
        }
    }

    private class ElementScroller extends ElementStatefulBackground {
        public ElementScroller(int width, int height) {
            super(orientation == Orientation.VERTICAL ? StateFulBackground.SCROLLER_VERTICAL : StateFulBackground.SCROLLER_HORIZONTAL, width, height);
        }

        @Override
        protected boolean onMoveRelative(int x, int y, int which) {
            if (self.isEnabled()) {
                if (orientation == Orientation.VERTICAL) {
                    setProgress((double) y / (parent.height - scroller.height));
                } else {
                    setProgress((double) x / (parent.width - scroller.width));
                }
            }
            return true;
        }

        @Override
        public void update() {
            statefulBackground = orientation == Orientation.VERTICAL ? StateFulBackground.SCROLLER_VERTICAL : StateFulBackground.SCROLLER_HORIZONTAL;
        }

        @Override
        public void dimension() {
            super.dimension();

            if (orientation == Orientation.VERTICAL) {
                this.y = (int) Math.round(progress * (parent.height - height));
            } else {
                this.x = (int) Math.round(progress * (parent.width - width));
            }
        }

        @Override
        public void draw() {
            IScalableTexture bg = self.isEnabled() ? isLeftClicked() ? ScalableTextureVerticalScroller.ACTIVATED : ScalableTextureVerticalScroller.NORMAL : ScalableTextureVerticalScroller.DISABLED;
            drawBackgroundFromTextures(bg);
            super.draw();
        }
    }

    public boolean setProgress(double progress) {
        double old = this.progress;
        this.progress = progress > 1 ? 1 : progress < 0 ? 0 : progress;
        if (old != this.progress) {
            onProgressChange(old, this.progress);
            return true;
        }
        return false;
    }

    public void onProgressChange(double old, double progress) {

    }

    @Override
    public boolean onScrolled(int x, int y, int amount) {
        return setProgress(progress + (-0.1d * (amount / 120))); //consume input if progress changed
    }
}