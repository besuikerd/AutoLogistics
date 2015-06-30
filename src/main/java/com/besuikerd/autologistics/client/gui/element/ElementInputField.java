package com.besuikerd.autologistics.client.gui.element;

import java.util.regex.Pattern;

import com.besuikerd.autologistics.common.BLogger;
import org.lwjgl.input.Keyboard;

import com.besuikerd.autologistics.client.gui.layout.LayoutDimension;
import com.besuikerd.autologistics.client.gui.texture.scalable.ScalableTexture;

public class ElementInputField extends ElementStyledContainer {

	protected ElementContainer inputFieldContainer;
	protected ElementViewport viewPort;
	protected ElementInputLabel inputFieldLabel;

	public ElementInputField(int width, String text) {
		this(width, text, "");
	}
	
	public ElementInputField(int width){
		this(width, "", "");
	}
	
	public ElementInputField(int width, String text, String regex){
		super(0,0,width,0, ScalableTexture.SLOT);
		this.height = fontRenderer.FONT_HEIGHT + 3;
		this.inputFieldContainer = new ElementContainer(0,0, width - 2 , fontRenderer.FONT_HEIGHT+1);
		inputFieldContainer.padding(1).paddingBottom(0);
		this.inputFieldLabel = new ElementInputLabel(0, 0, width - 4, text, regex);
		inputFieldContainer.add(inputFieldLabel);
		this.viewPort = new ElementViewport(0, fontRenderer.FONT_HEIGHT, inputFieldContainer);
		viewPort.widthDimension(LayoutDimension.WRAP_CONTENT);
		add(viewPort);
	}
	
	@Override
	protected boolean onPressed(int x, int y, int which) {
		super.onPressed(x, y, which);
		return inputFieldLabel.onPressed(x, y, which);
	}

	private class ElementInputLabel extends Element {

		public static final int COLOR_DISABLED = 0xFF404040;
		public static final int COLOR_UNFOCUSSED = 0xFFF0F0F0;
		public static final long FLICKER_TIME = 800L;

		protected StringBuilder textBuilder;
		protected String acceptedChars = "";
		
		protected long oldTime = 0;
		protected boolean blockInput = false;		
		protected int color = 0xFFFFFFFF;
		protected int cursorOffset = 0;
		protected int hideOffset = 1;
		protected int widthDiff = 0;

		public ElementInputLabel(int x, int y, int width, String text){
			super(x, y, width, 0);
			this.textBuilder = new StringBuilder();
			this.height = fontRenderer.FONT_HEIGHT;
			textBuilder.append(text);
		}
		
		/**
		 * constructor
		 * @param x	
		 * @param y	
		 * @param width	
		 * @param acceptedChars	Regex to accept more chars than only numbers and digits
		 * @param text Initial text
		 */
		public ElementInputLabel(int x, int y, int width, String acceptedChars, String text){
			this(x, y, width, text);
			this.acceptedChars = acceptedChars;
		}
		
		@Override
		protected boolean onPressed(int x, int y, int which) {
			super.onPressed(x, y, which);
			if(isEnabled()){
				int i = 0;
				int previous = Integer.MAX_VALUE;
				int current = Integer.MAX_VALUE;
				//TODO needs to be finetuned after viewport is implemented right!
				if(!isFocused()){
					widthDiff = 0;
					getRoot().requestFocus(this);
				}
				while((current = fontRenderer.getStringWidth(textBuilder.substring(0,textBuilder.length()-i))) > x + widthDiff){
					previous = current;
					i++;
					if(i>=textBuilder.length()){
						current = Integer.MAX_VALUE;
						break;
					}
				}
				if(Math.abs((current-x-widthDiff))>=Math.abs((previous-x-widthDiff))){
					i--;
				}
				cursorOffset = textBuilder.length()-i;
				return true;
			}
			return false;
		}
		
		@Override
		protected boolean onReleaseFocus() {
			BLogger.debug("releasefocus");
			cursorOffset = textBuilder.length();
			widthDiff = fontRenderer.getStringWidth(textBuilder.toString())-width;
			if(widthDiff < 0){
				widthDiff = 0;
			}
			return super.onReleaseFocus();
		}

		@Override
		protected boolean keyTyped(char key, int code) {
			super.keyTyped(key, code);
			if ((!isEnabled() && code != Keyboard.KEY_RETURN) || !isFocused()) {
				return false;
			}
			if (key != 0 && (Character.isLetterOrDigit(key) || (!acceptedChars.equals("") && Pattern.matches(acceptedChars, Character.toString(key))))) {
				if (!blockInput || fontRenderer.getStringWidth(textBuilder.toString() + key) + 2 < width) {
					textBuilder.insert(cursorOffset, key);
					cursorOffset++;
					if(fontRenderer.getStringWidth(textBuilder.substring(0,cursorOffset)+"|") - 2>widthDiff+width){
						widthDiff += fontRenderer.getStringWidth(Character.toString(key));
					}
				}
			} else {
				switch (code) {
				case Keyboard.KEY_BACK:
					if (cursorOffset > 0) {
						widthDiff -= fontRenderer.getStringWidth(Character.toString(textBuilder.charAt(cursorOffset - 1)));
						if(widthDiff < 0){
							widthDiff = 0;
						}
						textBuilder.deleteCharAt(cursorOffset - 1);
						hideOffset = 1;
						cursorOffset--;
					}
					break;
				case Keyboard.KEY_DELETE:
					if (cursorOffset < textBuilder.length()) {
						textBuilder.deleteCharAt(cursorOffset);
						hideOffset = 1;
					}
					break;
				case Keyboard.KEY_LEFT:
					if (cursorOffset > 0) {
						cursorOffset--;
						if(fontRenderer.getStringWidth(textBuilder.substring(0,cursorOffset))<widthDiff){
							widthDiff -= fontRenderer.getStringWidth(Character.toString(textBuilder.charAt(cursorOffset)));
						}
						if(widthDiff < 0){
							widthDiff = 0;
						}
					}
					break;
				case Keyboard.KEY_RIGHT:
					if (cursorOffset < textBuilder.length()) {
						cursorOffset++;
						if(fontRenderer.getStringWidth(textBuilder.substring(0,cursorOffset)+"|") - 2>widthDiff+width){
							widthDiff += fontRenderer.getStringWidth(Character.toString(textBuilder.charAt(cursorOffset - 1)));
						}
					}
					break;
				case Keyboard.KEY_END:
					cursorOffset = textBuilder.length();
					widthDiff = fontRenderer.getStringWidth(textBuilder.toString()+"|") - 2 -width;
					if(widthDiff < 0){
						widthDiff = 0;
					}
					break;
				case Keyboard.KEY_HOME:
					cursorOffset = 0;
					widthDiff = 0;
					break;
				case Keyboard.KEY_TAB:
				case Keyboard.KEY_RETURN:
					BLogger.debug("enter");
					getRoot().releaseFocus(this);
					break;
				default:
					return false;
				}
			}
			return true;
		}
		
		@Override
		public void update() {
			super.update();
			if(lastCode != -1){
				oldTime = System.currentTimeMillis();
			}
		}

		@Override
		public void draw() {
			super.draw();
			if (this.isEnabled() && isFocused()) {
				if (System.currentTimeMillis() - FLICKER_TIME > oldTime) {
					if (System.currentTimeMillis() - 2 * FLICKER_TIME > oldTime) {
						oldTime = System.currentTimeMillis();
					}
				} else {
					fontRenderer.drawString("|", absX() - widthDiff + fontRenderer.getStringWidth(textBuilder.substring(0, cursorOffset))-1, absY(), 0xFF000000);
				}
			}else if(fontRenderer.getStringWidth(textBuilder.toString()) > width){
				while(fontRenderer.getStringWidth(textBuilder.substring(0,textBuilder.length()-hideOffset-1)+"...") > width){
					hideOffset++;
				}
				fontRenderer.drawString(textBuilder.substring(0,textBuilder.length()-hideOffset-1)+"...", absX(), absY() + ((height - fontRenderer.FONT_HEIGHT) / 2), this.isEnabled() ?  COLOR_UNFOCUSSED : COLOR_DISABLED);
				return;
			}
			fontRenderer.drawString(textBuilder.toString(), absX() - widthDiff , absY(), this.isEnabled() ? isFocused() ? color : COLOR_UNFOCUSSED : COLOR_DISABLED);
		}
	}

}
