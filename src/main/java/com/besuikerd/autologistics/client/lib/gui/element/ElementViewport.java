package com.besuikerd.autologistics.client.lib.gui.element;

import static org.lwjgl.opengl.GL11.GL_ALWAYS;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_KEEP;
import static org.lwjgl.opengl.GL11.GL_REPLACE;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glColorMask;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glStencilFunc;
import static org.lwjgl.opengl.GL11.glStencilMask;
import static org.lwjgl.opengl.GL11.glStencilOp;

import com.besuikerd.autologistics.client.lib.gui.layout.LayoutDimension;

/**
 * Element that can contain a container. the container is only visible within
 * the bounds of the viewport. the container can be larger than the viewport.
 * the viewport acts as a root for the container within the viewport, to make
 * sure mouse events are handled properly
 *
 * @author Besuikerd
 */
public class ElementViewport extends Element {

    /**
     * container that is visible within this viewport
     */
    protected Element element;

    /**
     * root delegate
     */
    protected ElementRootContainer rootDelegate;

    /**
     * x-offset for container within viewport; can also be negative
     */
    protected int xOffset;

    /**
     * y-offset for container within viewport; can also be negative
     */
    protected int yOffset;


    public ElementViewport(int width, int height, Element element) {
        super(width, height);
        this.element = element;
    }

    @Override
    protected void onAdded() {
        rootDelegate = new ElementRootContainerDelegate(getRoot());
        rootDelegate
            .add(element)
            .width(width)
            .height(height)
            .padding(0);
    }

    @Override
    public void dimension() {
        super.dimension();
        //move container to the correct spot within the viewport
        element.x = xOffset;
        element.y = yOffset;

        //layout element and fix dimensions
        element.dimension();

        if (widthDimension == LayoutDimension.WRAP_CONTENT) {
            this.width = element.getPaddedWidth();
        }
        if (heightDimension == LayoutDimension.WRAP_CONTENT) {
            this.height = element.getPaddedHeight();
        }

        //limit root delegate within bounds of the real root
        this.rootDelegate.width = Math.min(width, getRoot().width);
        this.rootDelegate.height = Math.min(height, getRoot().height);
        this.rootDelegate.dx = Math.max(absX(), getRoot().absX());
        this.rootDelegate.dy = Math.max(absY(), getRoot().absY());
    }

    @Override
    protected boolean handleMouseInput(int mouseX, int mouseY) {
        super.handleMouseInput(mouseX, mouseY);
        this.rootDelegate.focusedElement = getRoot().focusedElement; //copy focus from real root
        this.rootDelegate.scrollMovement = getRoot().scrollMovement; //copy scroll movement from real root
        this.rootDelegate.eventHandler = getRoot().eventHandler; //copy eventhandler from real root
        return element.handleMouseInput(mouseX - xOffset, mouseY - yOffset);
    }

    @Override
    public void update() {
        super.update();
        rootDelegate.update();
    }

    @Override
    public void onEvent(String name, Object[] args, Element e) {
        rootDelegate.onEvent(name, args, e);
    }

    @Override
    public void draw() {
        element.dx = absX();
        element.dy = absY();
        super.draw();
        glEnable(GL_STENCIL_TEST);
        glStencilFunc(GL_ALWAYS, 0x1, 0xff);
        glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
        glColorMask(false, false, false, false); //no color mask
        glDepthMask(false); //no depth mask
        glStencilMask(0xff); // draw to mask
        glClear(GL_STENCIL_BUFFER_BIT); //clear stencil
        glColor4f(1, 1, 1, 1);
        drawRect(Math.max(absX(), rootDelegate.absX()), Math.max(absY(), rootDelegate.absY()), Math.min(absX() + width, rootDelegate.absX() + rootDelegate.width), Math.min(absY() + height, rootDelegate.absY() + rootDelegate.height), 0xffffffff); //draw square mask
        glStencilMask(0x0); //don't draw to mask
        glStencilFunc(GL_EQUAL, 0x1, 0xff);
        glColorMask(true, true, true, true); //restore color mask
        glDepthMask(true); //restore depth mask
        element.draw();
        glDisable(GL_STENCIL_TEST);
    }
}