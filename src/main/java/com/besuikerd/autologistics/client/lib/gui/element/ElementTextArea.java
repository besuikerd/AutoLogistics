package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.render.Colors;
import com.besuikerd.autologistics.common.lib.util.tuple.Vector2;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ElementTextArea extends Element{

    private StringBuilder text;
    private List<String> textToRender;

    private Caret caret;

    private int heightGap;
    boolean invalidated;

    private int caretStart;
    private int caretEnd;

    public ElementTextArea(String text, int x, int y, int width) {
        super(x, y, width, 0);
        this.text = new StringBuilder(text);
        textToRender = new ArrayList<String>();
        invalidated = true;
        this.heightGap = 1;
        padding(4);
        caret = new Caret(this, textToRender, '_', fontRenderer.FONT_HEIGHT + heightGap);

        this.text.setLength(0);

        int n = 5;
        for(int i = 0 ; i < n ; i++) {
            this.text.append(
                    "item = <minecraft:log>\n" +
                            "bla = <minecraft:charcoal:1>\n" +
                            "item >> bla"
            );
            if(i != n - 1){
                this.text.append('\n');
            }
        }
    }

    public ElementTextArea(String initialText, int columns){
        this(initialText, 0, 0, columns);
    }



    public ElementTextArea(int columns){
        this("", columns);
    }


    @Override
    public void update() {
        caret.update();
    }

    @Override
    public void dimension() {
        super.dimension();


        if(invalidated){
            textToRender.clear();
            String[] lines = text.toString().split("\n|\r\n");
            for(String line : lines){
                String[] rows = line.split("(?<=\\\\G.{4})");
                for(String row : rows){
                    int stringWidth = fontRenderer.getStringWidth(row);
                    if(stringWidth > width){ // we need to chop up the string;
                        int numberOfCharacters = 0;
                        while(numberOfCharacters < row.length()){
                            int start = numberOfCharacters;
                            int length = 0;
                            while(numberOfCharacters < row.length()){
                                char c = row.charAt(numberOfCharacters);
                                length += fontRenderer.getCharWidth(c);
                                if(length < width) {
                                    numberOfCharacters++;
                                } else{
                                    break;
                                }
                            }
                            textToRender.add(row.substring(start, numberOfCharacters));
                        }
                    } else{
                        textToRender.add(row);
                    }
                }
            }
            invalidated = false;
        }

        this.height = textToRender.size() * (fontRenderer.FONT_HEIGHT + heightGap);
    }

    @Override
    public void draw() {

        drawRectangle(0, 0, width + paddingLeft + paddingRight, height + paddingTop + paddingBottom, 0xff000000);

        int yOffset = 0;
        for(String row : textToRender) {
            drawString(row, paddingLeft, paddingTop + yOffset, Colors.white);
            yOffset += fontRenderer.FONT_HEIGHT + heightGap;
        }

        caret.draw();

        super.draw();
    }

    private Vector2 getPosition(int characterOffset){
        int charactersCounted = 0;
        for(int i = 0 ; i < textToRender.size() ; i++){
            int lineLength = textToRender.get(i).length();
            charactersCounted += lineLength;
            if(charactersCounted > characterOffset){
                return new Vector2(characterOffset - (charactersCounted - lineLength), i);
            }
        }
        return new Vector2(0,0);
    }

    @Override
    protected boolean keyPressed(char key, int code) {
        super.keyPressed(key, code);
        caret.keyPressed(key, code);

        switch(code){
            case Keyboard.KEY_BACK:
                
                text.deleteCharAt(caret.getCharacterOffset());
                invalidated = true;
                caret.moveCaretHorizontally(-1);

                break;
        }

        return true;
    }


    @Override
    protected boolean onPressed(int x, int y, int which) {
        return super.onPressed(x, y, which);
//        getRoot().requestFocus(this);
//        return true;
    }
}
