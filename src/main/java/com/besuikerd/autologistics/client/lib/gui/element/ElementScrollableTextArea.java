package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.render.Colors;
import com.besuikerd.autologistics.common.lib.util.tuple.Vector2;

public class ElementScrollableTextArea extends ElementScrollContainer implements CaretPositionUpdatedListener{
    private ElementTextArea textArea;


    public ElementScrollableTextArea(int width, int height) {
        super(height);
        this.textArea = new ElementTextArea(width){
            @Override
            protected boolean handleMouseInput(int mouseX, int mouseY) {
                int textAreaHeight = height + getPaddingTop() + container.getPaddingTop();
                if(textAreaHeight < viewport.height && mouseY > textAreaHeight){ //we clicked outside the bounds of the textarea
                    return super.handleMouseInput(mouseX, textAreaHeight - textArea.getLineHeight());
                } else{
                    return super.handleMouseInput(mouseX, mouseY);
                }
            }
        };
        container
            .padding(1);
        add(textArea);
        textArea.caret.addCaretPositionUpdatedListener(this);
    }

    @Override
    public void draw() {
        drawRectangle(0, 0, width - scrollBar.width, height, 0xff000000);
        super.draw();

        drawColoredBorder(0, 0, width - scrollBar.width, height, 1, Colors.gray);
    }

    public String getText(){
        return textArea.getText();
    }

    public ElementScrollContainer text(String text){
        textArea.text(text);
        return this;
    }

    @Override
    public void onPositionUpdated(Vector2 oldPosition, Vector2 newPosition) {
        int lineHeight = textArea.getLineHeight();
        int caretPosition = newPosition.y * (lineHeight) + textArea.getPaddingTop() + container.getPaddingTop();
        int inViewportPosition = viewport.height - viewport.yOffset;
        if(caretPosition + lineHeight > inViewportPosition){
            double progress = (caretPosition - viewport.height + lineHeight + textArea.getPaddingBottom() + container.getPaddingBottom()) / (double) (textArea.getPaddedHeight() - viewport.height);
            scrollBar.setProgress(progress);
        } else if(caretPosition < inViewportPosition - viewport.height){
            double progress = (caretPosition - lineHeight + textArea.getPaddingTop() + container.getPaddingTop()) / (double) (textArea.getPaddedHeight() - viewport.height);
            scrollBar.setProgress(progress);
        }
    }
}
