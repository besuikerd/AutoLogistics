package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.common.lib.util.tuple.Vector2;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class Caret extends Element{
    private int caretAnimationState;
    private boolean caretVisible = false;
    private int lineHeight;

    private Vector2 caretPosition;

    private List<String> lines;
    private char caretCharacter;

    private Element parent;

    public Caret(Element parent, List<String> lines, char caretCharacter, int lineHeight) {
        super(0, 0, Minecraft.getMinecraft().fontRenderer.getCharWidth(caretCharacter), lineHeight);
        this.lines = lines;
        this.parent = parent;
        this.caretCharacter = caretCharacter;
        this.lineHeight = lineHeight;
        caretPosition = new Vector2(0,0);
    }

    @Override
    public void update(){
        caretAnimationState = ++caretAnimationState % 20;
        caretVisible = caretAnimationState < 10;
    }

    public void moveCaretHorizontally(int amount){
        ensureCaretInBounds();
        String line = lines.get(caretPosition.y);
        if(caretPosition.x + amount > line.length()){ //possibly go down a line
            if(caretPosition.y < lines.size() - 1){
                caretPosition = new Vector2(0, caretPosition.y + 1);
            }
            //to account for word wrap

        } else if(caretPosition.x + amount < 0){ // possibly go up a line
            if(caretPosition.y > 0){
                String previousLine = lines.get(caretPosition.y - 1);
                caretPosition = new Vector2(previousLine.length(), caretPosition.y - 1);
            }
        } else{
            caretPosition = new Vector2(caretPosition.x + amount, caretPosition.y);
        }
    }

    public void moveCaretVertically(int amount){
        ensureCaretInBounds();
        if(amount > 0){
            if(caretPosition.y < lines.size() - 1){
                String nextLine = lines.get(caretPosition.y + 1);
                caretPosition = new Vector2(Math.min(nextLine.length(), caretPosition.x), caretPosition.y + Math.min(amount, lines.size() - caretPosition.y - 1));
            }
        } else if(amount < 0){
            if(caretPosition.y > 0){
                String previousLine = lines.get(caretPosition.y - 1);
                caretPosition = new Vector2(Math.min(previousLine.length(), caretPosition.x), Math.max(0, caretPosition.y + amount));
            }
        }
    }

    private void ensureCaretInBounds(){
        if(caretPosition.y >= lines.size()){
            int y = lines.size() - 1;
            int x = lines.get(y).length();
            this.caretPosition = new Vector2(x, y);
        }
    }

    public int getCharacterOffset(){
        int offset = 0;
        for(int i = 0 ; i < caretPosition.y ; i++){
            offset += lines.get(i).length();
            offset++; //add newlines to the offset
        }
        return offset + caretPosition.x;
    }

    @Override
    public void draw() {
        if(caretVisible) {
            String line = lines.get(caretPosition.y);
            int characterOffset = 0;

            if(caretPosition.x < line.length()){
                char c = line.charAt(caretPosition.x);
                characterOffset = (fontRenderer.getCharWidth(caretCharacter) - fontRenderer.getCharWidth(c)) / 2;
            }

            int xOffset = fontRenderer.getStringWidth(line.substring(0, caretPosition.x)) - characterOffset;//fontRenderer.getCharWidth(caretPosition) / 2;
            parent.drawString("_", xOffset + parent.paddingLeft, caretPosition.y * lineHeight + parent.paddingTop, 0xffffffff);
        }
    }

    public void setCaretPosition(Vector2 position){
        this.caretPosition = position;
    }

    public Vector2 getCaretPosition() {
        return caretPosition;
    }

    public boolean isCaretVisible() {
        return caretVisible;
    }

    public char getCaretCharacter() {
        return caretCharacter;
    }

    @Override
    protected boolean keyPressed(char key, int code) {
        super.keyPressed(key, code);
        switch(code){
            case Keyboard.KEY_LEFT:
                moveCaretHorizontally(-1);
                break;
            case Keyboard.KEY_RIGHT:
                moveCaretHorizontally(1);
                break;
            case Keyboard.KEY_DOWN:
                moveCaretVertically(1);
                break;
            case Keyboard.KEY_UP:
                moveCaretVertically(-1);
                break;
            case Keyboard.KEY_HOME:
                moveCaretHorizontally(-caretPosition.x);
                break;
            case Keyboard.KEY_END:
                String currentLine = lines.get(caretPosition.y);
                moveCaretHorizontally(currentLine.length() - caretPosition.x);
                break;
            case Keyboard.KEY_PRIOR: //page up
                moveCaretVertically(-caretPosition.y);
                break;
            case Keyboard.KEY_NEXT: //page down
                moveCaretVertically(lines.size() - caretPosition.y - 1);
                break;
        }
        return false;
    }
}
