package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.lib.gui.event.IEventHandler;

public class ElementRootContainerDelegate extends ElementRootContainer {
    private ElementRootContainer delegate;

    public ElementRootContainerDelegate(ElementRootContainer delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean requestFocus(Element element) {
        return delegate.requestFocus(element);
    }

    @Override
    public void releaseFocus(Element element) {
        delegate.releaseFocus(element);
    }

    @Override
    public Element getFocusedElement() {
        return delegate.getFocusedElement();
    }

    @Override
    public boolean handleMouseInput(int mouseX, int mouseY) {
        return delegate.handleMouseInput(mouseX, mouseY);
    }

    @Override
    public boolean handleKeyboardInput() {
        return delegate.handleKeyboardInput();
    }

    @Override
    public void setEventHandler(IEventHandler eventHandler) {
        delegate.setEventHandler(eventHandler);
    }

    @Override
    public IEventHandler getEventHandler() {
        return delegate.getEventHandler();
    }
}
