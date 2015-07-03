package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.render.Colors;

public class ElementScrollableTextArea extends ElementScrollContainer {
    private ElementTextArea textArea;


    public ElementScrollableTextArea(int width, int height) {
        super(height);
        this.textArea = new ElementTextArea(width);
        container
            .padding(1);
        add(textArea);
    }

    @Override
    public void draw() {
        drawRectangle(0, 0, width - scrollBar.width, height, 0xff000000);
        super.draw();
        drawColoredBorder(0, 0, width - scrollBar.width, height, 1, Colors.gray);
    }

    @Override
    protected boolean onPressed(int x, int y, int which) {
        super.onPressed(x, y, which);
        return textArea.onPressed(x, y, which); //TODO fix coordinates
    }

    public String getText(){
        return textArea.getText();
    }

    public ElementScrollContainer text(String text){
        textArea.text(text);
        return this;
    }
}
