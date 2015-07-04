package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.common.lib.util.tuple.Vector2;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Caret extends Element{
    private int caretAnimationState;
    private boolean caretVisible = false;
    private int lineHeight;

    private List<CaretPositionUpdatedListener> caretPositionUpdatedListeners;

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
        this.caretPositionUpdatedListeners = new ArrayList<CaretPositionUpdatedListener>();
        setCaretPosition(0, 0);
    }

    @Override
    public void update(){
        caretAnimationState = ++caretAnimationState % 20;
        caretVisible = caretAnimationState < 10;
    }

    public void moveCaretHorizontally(int amount){
        ensureCaretInBounds();
        String line = lines.get(caretPosition.y);
        int newLineOffset = line.endsWith("\n") ? 1 : 0;
        if(caretPosition.x + amount > line.length() - newLineOffset){ //possibly go down a line
            if(caretPosition.y < lines.size() - 1){
                setCaretPosition(line.endsWith("\n") ? 0 : amount, caretPosition.y + 1);
            }
            //to account for word wrap

        } else if(caretPosition.x + amount < 0){ // possibly go up a line
            if(caretPosition.y > 0){
                String previousLine = lines.get(caretPosition.y - 1);
                setCaretPosition(previousLine.length() - 1, caretPosition.y - 1);
            }
        } else{
            setCaretPosition(caretPosition.x + amount, caretPosition.y);
        }

        int x = 2;
    }

    public void moveCaretVertically(int amount){
        ensureCaretInBounds();
        String currentLine = lines.get(caretPosition.y);
        if(amount > 0){
            if(caretPosition.y < lines.size() - 1){
                String nextLine = lines.get(caretPosition.y + amount);
//                int xPos = (Math.min(nextLine.length() - newLineFix(nextLine), caretPosition.x));
                setCaretPosition(Math.min(nextLine.length() - newLineFix(nextLine), caretPosition.x), caretPosition.y + Math.min(amount, lines.size() - caretPosition.y - 1));
            }
        } else if(amount < 0){
            int newCaretPosition = Math.max(0, caretPosition.y + amount);
            if(caretPosition.y != newCaretPosition){
                String previousLine = lines.get(newCaretPosition);
                setCaretPosition(Math.min(previousLine.length() - 1, caretPosition.x), Math.max(0, caretPosition.y + amount));
            }
        }
    }

    private void ensureCaretInBounds(){
        if(caretPosition.y >= lines.size()){
            int y = lines.size() - 1;
            int x = lines.get(y).length();
            setCaretPosition(x, y);
        }
    }

    public int getCharacterOffset(){
        int offset = 0;
        for(int i = 0 ; i < caretPosition.y ; i++){
            offset += lines.get(i).length();
        }
        return offset + caretPosition.x;
    }

    public int nearestCharacter(String currentLine, int currentPosition, String nextLine){
        int currentWidth = fontRenderer.getStringWidth(currentLine.substring(0, Math.min(currentLine.length(), currentPosition)));
        return nearestCharacter(currentWidth, nextLine);
    }

    public int nearestCharacter(int xOffset, String nextLine){
        int totalWidth = fontRenderer.getCharWidth(nextLine.charAt(0)) / 2;
        int currentCharacter = 0;
        for(int i = currentCharacter + 1; i < nextLine.length() &&  totalWidth < xOffset; i++){
            totalWidth += fontRenderer.getCharWidth(nextLine.charAt(currentCharacter));
            if(totalWidth < xOffset) {
                currentCharacter++;
            }
        }
        return currentCharacter + (1 - newLineFix(nextLine));
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

            int caretPositionX = caretPosition.x;
            if(caretPositionX > line.length()){ // fix rendering on a line without a newline (last line)
                caretPositionX = line.length();
            }
            String preCaretString = line.substring(0, caretPositionX);

            int xOffset = fontRenderer.getStringWidth(preCaretString) - characterOffset;//fontRenderer.getCharWidth(caretPosition) / 2;
            parent.drawString("_", xOffset + parent.paddingLeft, caretPosition.y * lineHeight + parent.paddingTop, 0xffffffff);
        }
    }

    public void setCaretPosition(int x, int y){
        setCaretPosition(new Vector2(x,y));
    }

    public void setCaretPosition(Vector2 position){
        Vector2 oldPos = caretPosition;
        this.caretPosition = position;
        for(CaretPositionUpdatedListener listener: caretPositionUpdatedListeners){
            listener.onPositionUpdated(oldPos, position);
        }
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
    protected boolean keyTyped(char key, int code) {
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
                if(currentLine.length() > 0){
                    moveCaretHorizontally(currentLine.length() - caretPosition.x - newLineFix(currentLine));
                }

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

    /**
     * 1 if line ends with a newline, 0 otherwise
     * @return
     */
    public int newLineFix(String s){
        return s.endsWith("\n") ? 1 : 0;
    }

    public void addCaretPositionUpdatedListener(CaretPositionUpdatedListener listener){
        caretPositionUpdatedListeners.add(listener);
    }
}
