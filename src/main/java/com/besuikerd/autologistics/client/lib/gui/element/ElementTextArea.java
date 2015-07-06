package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.render.Colors;
import com.besuikerd.autologistics.client.util.KeyboardUtils;
import com.besuikerd.autologistics.common.BLogger;
import com.besuikerd.autologistics.common.lib.util.ClipBoard;
import com.besuikerd.autologistics.common.lib.util.StringUtils;
import com.besuikerd.autologistics.common.lib.util.tuple.Vector2;
import org.lwjgl.input.Keyboard;
import scala.Option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElementTextArea extends Element implements CaretPositionUpdatedListener{

    public static final Pattern SMART_INDENT_PATTERN = Pattern.compile("^( +)");

    protected StringBuilder text;
    protected List<String> textToRender;

    protected Caret caret;

    private int heightGap;

    private boolean inSelection;
    private Vector2 selectStart;

    public ElementTextArea(String text, int x, int y, int width) {
        super(x, y, width, 0);
        this.text = new StringBuilder(text);
        textToRender = new ArrayList<String>();

        this.heightGap = 1;
        padding(4);
        caret = new Caret(this, textToRender, '_', getLineHeight());
        caret.addCaretPositionUpdatedListener(this);
        selectStart = caret.getCaretPosition();
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

        if(!selectStart.equals(caret.getCaretPosition())){
            int selectionColor = Colors.lightGray;
            Vector2 cPos = caret.getCaretPosition();
            Vector2 first = cPos.min(selectStart);
            Vector2 last = first.equals(cPos) ? selectStart : cPos;

            if(first.y == last.y){
                String line = textToRender.get(first.y);
                int offset = getCharacterOffset(line, first.x);
                int width = getCharacterOffset(line, first.x, last.x);
                drawRectangle(paddingLeft + offset, paddingTop + first.y * getLineHeight(), width, getLineHeight(), selectionColor);
            } else{
                for(int row = first.y ; row <= last.y ; row++){
                    String currentLine = textToRender.get(row);
                    if(row == first.y){
                        int offset = getCharacterOffset(currentLine, first.x);
                        drawRectangle(offset + paddingLeft, paddingTop + row * getLineHeight(), fontRenderer.getStringWidth(currentLine) - offset, getLineHeight(), selectionColor);
                    }
                    if(row == last.y){
                        int offset = getCharacterOffset(currentLine, last.x);
                        drawRectangle(paddingLeft, paddingTop + row * getLineHeight(), offset, getLineHeight(), selectionColor);
                    }
                    if(row != first.y && row != last.y){
                        drawRectangle(paddingLeft, paddingTop + row * getLineHeight(), fontRenderer.getStringWidth(currentLine), getLineHeight(), selectionColor);
                    }
                }
            }

        }

        int yOffset = 0;
        for(String row : textToRender) {
//            if(row.endsWith("\n")){
//                row = row.substring(0, row.length() - 1);
//            }

//            drawRectangle(paddingLeft, paddingTop + yOffset, fontRenderer.getStringWidth(row), fontRenderer.FONT_HEIGHT, 0xffff0000);
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

        int characterOffset;

        if(KeyboardUtils.isCtrlKeyDown()){
            switch(code){
                case Keyboard.KEY_X:
                    ClipBoard.set(getSelectedText());
                    removeSelection(true);
                    break;
                case Keyboard.KEY_C:
                    ClipBoard.set(getSelectedText());
                    break;
                case Keyboard.KEY_V:
                    Option<String> optPaste = ClipBoard.get();
                    if(optPaste.nonEmpty()){
                        String paste = optPaste.get();
                        addText(paste);
                    }
                    invalidate();
                    break;
                case Keyboard.KEY_A:
                    int lastLine = textToRender.size() - 1;
                    String lineText = textToRender.get(lastLine);
                    caret.setCaretPosition(lineText.length() - caret.newLineFix(lineText), lastLine);
                    selectStart = new Vector2(0,0);
                    break;
                default:
            }
        } else{
            caret.keyTyped(key, code);
            switch(code){
                case Keyboard.KEY_BACK:
                    if(removeSelection() == 0){
                        characterOffset = caret.getCharacterOffset();
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
                    }
                    break;
                case Keyboard.KEY_DELETE:
                    if(removeSelection() == 0) {
                        characterOffset = caret.getCharacterOffset();
                        if (characterOffset != text.length()) {
                            text.deleteCharAt(characterOffset);
                            invalidate();
                        }
                    }
                    break;
                case Keyboard.KEY_RETURN:
                    removeSelection(false);
                    String toInsert = "\n";
                    String currentLine = textToRender.get(caret.getCaretPosition().y);
                    Matcher m = SMART_INDENT_PATTERN.matcher(currentLine);
                    if(m.find()){
                        char[] ws = new char[m.group().length()];
                        Arrays.fill(ws, ' ');
                        toInsert += new String(ws);
                    }
                    characterOffset = caret.getCharacterOffset();
                    text.insert(characterOffset, toInsert);
                    invalidate();
                    caret.moveCaretHorizontally(1); //newline
                    caret.moveCaretHorizontally(toInsert.length() - 1); //smart indent
                    break;
                case Keyboard.KEY_TAB:
                    removeSelection(false);
                    characterOffset = caret.getCharacterOffset();
                    text.insert(characterOffset, "  ");
                    invalidate();
                    caret.moveCaretHorizontally(2);
                    break;
                default:
                    if(StringUtils.isASCII(key)){
                        removeSelection(false);
                        characterOffset = caret.getCharacterOffset();
                        text.insert(characterOffset, key);
                        invalidate();
                        caret.moveCaretHorizontally(1);
                    }
            }
        }



        return true;
    }

    public void addText(String text){
        removeSelection(false);
        int characterOffset = caret.getCharacterOffset();
        this.text.insert(characterOffset, text);
        caret.moveCaretHorizontally(text.length());
        invalidate();
    }


    public int removeSelection(){
        return removeSelection(true);
    }

    public int removeSelection(boolean invalidate){
        int removed = 0;
        if(caret.getCaretPosition() != selectStart){
            Vector2 first = caret.getCaretPosition().min(selectStart);
            Vector2 last = first == selectStart ? caret.getCaretPosition() : selectStart;
            if(first.y == last.y) {
                removed = last.x - first.x;
            } else{
                for(int row = first.y ; row <= last.y ; row++){
                    String currentLine = textToRender.get(row);
                    if(row != first.y && row != last.y) { //we remove the whole line
                        removed += currentLine.length();
                    }
                    if(row == first.y) { // we remove first.x until end of line
                        removed += currentLine.length() - first.x;
                    }
                    if(row == last.y) { // we remove start of line until last.x
                        removed += last.x;
                    }
                }
            }
            int characterOffset = caret.getCharacterOffset(first);
            text.delete(characterOffset, characterOffset + removed);
            caret.setCaretPosition(first);
            if(invalidate) {
                invalidate();
            }
        }
        return removed;
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
        moveCaretPosition(x, y);
        getRoot().requestFocus(this);
        inSelection = true;
        selectStart = caret.getCaretPosition();
        return true;
    }

    @Override
    protected boolean onMove(int x, int y, int which) {
        moveCaretPosition(x, y);

        if(!inSelection){
            inSelection = true;
            selectStart = caret.getCaretPosition();
        }

        return true;
    }

    private void moveCaretPosition(int x, int y){
        int lineNumber = Math.max(0, Math.min((y - paddingTop) / getLineHeight(), textToRender.size() - 1));
        String lineText = textToRender.get(lineNumber);
        int xPos = 0;
        if(!lineText.isEmpty()){
            int lineWidth = fontRenderer.getStringWidth(lineText);
            if(lineWidth <= x){
                caret.setCaretPosition(lineText.length() - caret.newLineFix(lineText), lineNumber);
            } else {
                caret.setCaretPosition(caret.nearestCharacter(x, lineText), lineNumber);
            }
        }
    }

    private int getCharacterOffset(String text, int start, int end){
        int offset = 0;
        for(int i = start ; i < text.length() && i < end ; i++){
            offset += fontRenderer.getCharWidth(text.charAt(i));
        }
        return offset;
    }

    private int getCharacterOffset(String text, int characterInLine){
        return getCharacterOffset(text, 0, characterInLine);
    }

    public String getSelectedText(){
        Vector2 first = selectStart.min(caret.getCaretPosition());
        Vector2 last = first == selectStart ? caret.getCaretPosition() : selectStart;
        int firstIndex = caret.getCharacterOffset(first);
        int lastIndex = caret.getCharacterOffset(last);
        return text.substring(firstIndex, lastIndex);
    }

    @Override
    protected void onReleased(int x, int y, int which) {
        super.onReleased(x, y, which);
        inSelection = false;
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

    @Override
    public void onPositionUpdated(Vector2 oldPosition, Vector2 newPosition) {
        BLogger.debug(Keyboard.getEventKey());
        if(!inSelection && (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || !caret.isCaretMovementKey(Keyboard.getEventKey()))) {
            this.selectStart = newPosition;
        }
    }
}
