package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.render.Colors;
import com.besuikerd.autologistics.common.lib.util.StringUtils;
import com.besuikerd.autologistics.common.lib.util.tuple.Vector2;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ElementTextArea extends Element{

    protected StringBuilder text;
    protected List<String> textToRender;

    protected Caret caret;

    private int heightGap;
    private int caretStart;
    private int caretEnd;

    public ElementTextArea(String text, int x, int y, int width) {
        super(x, y, width, 0);
        this.text = new StringBuilder(text);
        textToRender = new ArrayList<String>();

        this.heightGap = 1;
        padding(4);
        caret = new Caret(this, textToRender, '_', getLineHeight());

        invalidate();
    }

    public ElementTextArea(String initialText, int columns){
        this(initialText, 0, 0, columns);
    }



    public ElementTextArea(int columns){
        this("", columns);
    }


    @Override
    public void update() {
        super.update();
        caret.update();
    }

    @Override
    public void draw() {

        drawRectangle(0, 0, width + paddingLeft + paddingRight, height + paddingTop + paddingBottom, 0xff000000);

        int yOffset = 0;
        for(String row : textToRender) {
            if(row.endsWith("\n")){
                row = row.substring(0, row.length() - 1);
            }

            drawString(row, paddingLeft, paddingTop + yOffset, Colors.white);
            yOffset += getLineHeight();
        }

        if(isFocused()) {
            caret.draw();
        }

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
    protected boolean keyTyped(char key, int code) {
        super.keyTyped(key, code);
        caret.keyTyped(key, code);
        switch(code){
            case Keyboard.KEY_BACK:
                int characterOffset = caret.getCharacterOffset();
                if(characterOffset != 0){
                    boolean removeNewline = caret.getCaretPosition().x == 0;
                    if(removeNewline){
                        caret.moveCaretHorizontally(-1);
                    }

                    text.deleteCharAt(characterOffset - 1);
                    invalidate();

                    if(!removeNewline) {
                        caret.moveCaretHorizontally(-1);
                    }
                }
                break;
            case Keyboard.KEY_DELETE:
                characterOffset = caret.getCharacterOffset();
                if(characterOffset != text.length()){
                    text.deleteCharAt(characterOffset);
                    invalidate();
                }
                break;
            case Keyboard.KEY_RETURN:
                characterOffset = caret.getCharacterOffset();
                text.insert(characterOffset, '\n');
                invalidate();
                caret.moveCaretHorizontally(1);

                break;
            case Keyboard.KEY_TAB:
                characterOffset = caret.getCharacterOffset();
                text.insert(characterOffset, "  ");
                invalidate();
                caret.moveCaretHorizontally(2);
                break;
            default:
                if(StringUtils.isASCII(key)){
                    characterOffset = caret.getCharacterOffset();
                    text.insert(characterOffset, key);
                    invalidate();
                    caret.moveCaretHorizontally(1);
                }
        }

        return true;
    }

    public void invalidate(){
        textToRender.clear();
        String[] lines = text.toString().split("\n|\r\n", -1);
        for(int i = 0 ; i < lines.length ; i++){
            String line = lines[i];
            if(i != lines.length - 1){
                line += '\n';
            }
            int stringWidth = fontRenderer.getStringWidth(line);
            if(stringWidth > width){ // we need to chop up the string;
                int numberOfCharacters = 0;
                while(numberOfCharacters < line.length()){
                    int start = numberOfCharacters;
                    int length = 0;
                    while(numberOfCharacters < line.length()){
                        char c = line.charAt(numberOfCharacters);
                        length += fontRenderer.getCharWidth(c);
                        if(length < width) {
                            numberOfCharacters++;
                        } else{
                            break;
                        }
                    }
                    textToRender.add(line.substring(start, numberOfCharacters));
                }
            } else{
                textToRender.add(line);
            }
        }
        this.height = textToRender.size() * (fontRenderer.FONT_HEIGHT + heightGap);
    }


    @Override
    protected boolean onPressed(int x, int y, int which) {
        super.onPressed(x, y, which);
        System.out.println("pressed");
        getRoot().requestFocus(this);
        return true;
    }

    @Override
    public boolean handleKeyboardInput() {
        super.handleKeyboardInput();
        return true;
    }

    public String getText(){
        return text.toString();
    }

    public ElementTextArea text(String text){
        this.text.setLength(0);
        this.text.append(text);
        invalidate();
        return this;
    }

    public int getLineHeight(){
        return fontRenderer.FONT_HEIGHT + heightGap;
    }
}