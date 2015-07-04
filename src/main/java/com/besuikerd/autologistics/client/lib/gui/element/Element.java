package com.besuikerd.autologistics.client.lib.gui.element;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.besuikerd.autologistics.common.lib.data.IStreamDeserializable;
import com.besuikerd.autologistics.common.lib.data.IStreamSerializable;
import com.besuikerd.autologistics.common.lib.util.INamed;
import com.besuikerd.autologistics.common.lib.util.MathUtil;
import com.besuikerd.autologistics.common.lib.util.tuple.Vector4;

import static com.besuikerd.autologistics.common.lib.util.tuple.TupleUtils.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.besuikerd.autologistics.client.lib.gui.event.IEventAction;
import com.besuikerd.autologistics.client.lib.gui.event.ITrigger;
import com.besuikerd.autologistics.client.lib.gui.event.Trigger;
import com.besuikerd.autologistics.client.lib.gui.layout.Alignment;
import com.besuikerd.autologistics.client.lib.gui.layout.LayoutDimension;
import com.besuikerd.autologistics.client.lib.gui.styler.IElementStyler;
import com.besuikerd.autologistics.client.lib.gui.texture.ElementState;
import com.besuikerd.autologistics.client.lib.gui.texture.IBorderTexture;
import com.besuikerd.autologistics.client.lib.gui.texture.IStateFulBackground;
import com.besuikerd.autologistics.client.lib.gui.texture.ITexture;
import com.besuikerd.autologistics.client.lib.gui.texture.scalable.IScalableTexture;

public abstract class Element extends Gui implements IStreamSerializable, IStreamDeserializable {

    protected ResourceLocation textures = new ResourceLocation("autologistics", "textures/gui/elements.png");

    public static final int ENABLED = 1;
    public static final int HOVERING = 2;
    public static final int LEFT_CLICKED = 4;
    public static final int RIGHT_CLICKED = 8;
    public static final int MIDDLE_CLICKED = 16;
    public static final int FOCUSED = 32;

    public static final int BUTTON_LEFT = 0;
    public static final int BUTTON_RIGHT = 1;
    public static final int BUTTON_MIDDLE = 2;
    public static final Integer[] BUTTONS = new Integer[]{LEFT_CLICKED, RIGHT_CLICKED, MIDDLE_CLICKED};

    /**
     * should the dimensions of this element be saved?
     */
    protected boolean saveDimensions = false;

    /**
     * delay for double presses
     */
    public static final long THRESHOLD_DOUBLE_PRESS = 200l;

    public static final long THRESHOLD_INITIAL_KEY_TYPED = 400l;
    public static final long THRESHOLD_NEXT_KEY_TYPED = 50l;

    public static final Map<Integer, Integer> mouseMap = Collections.unmodifiableMap(new HashMap<Integer, Integer>() {
        {
            put(LEFT_CLICKED, BUTTON_LEFT);
            put(RIGHT_CLICKED, BUTTON_RIGHT);
            put(MIDDLE_CLICKED, BUTTON_MIDDLE);
        }
    });

    protected Map<Integer, Long> lastClicks = new HashMap<Integer, Long>() {
        {
            put(LEFT_CLICKED, 0l);
            put(RIGHT_CLICKED, 0l);
            put(MIDDLE_CLICKED, 0l);
        }
    };

    protected Minecraft mc;
    protected FontRenderer fontRenderer;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected int paddingTop;
    protected int paddingRight;
    protected int paddingBottom;
    protected int paddingLeft;

    private String id;

    /**
     * parent container
     */
    protected ElementContainer parent;
    private ElementRootContainer root;


    protected LayoutDimension widthDimension;
    protected LayoutDimension heightDimension;

    protected Alignment alignment;

    /**
     * x offset the parent container has
     */
    protected int dx;

    /**
     * y offset the parent container has
     */
    protected int dy;

    /**
     * index assigned by container while adding it to a container
     */
    protected int index;

    /**
     * state flags are stored in this variable
     */
    protected int state;

    protected int xOffsetButtonPress;
    protected int yOffsetButtonPress;

    /**
     * last character typed
     */
    protected char lastChar;

    /**
     * last code typed;
     */
    protected int lastCode = -1;

    /**
     * time when the next character should be typed
     */
    protected long nextChar;

    /**
     * actions this element will perform when a given event is triggered
     */
    protected Map<String, List<IEventAction>> actions;

    /**
     * Triggers this element will trigger
     */
    protected Map<ITrigger, String> triggers;

    /**
     * styles the element with a given styler. Can be null
     */
    protected IElementStyler styler;

    public Element(int x, int y, int width, int height) {
        this.mc = Minecraft.getMinecraft();
        this.fontRenderer = mc.fontRenderer;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.widthDimension = LayoutDimension.ABSOLUTE;
        this.heightDimension = LayoutDimension.ABSOLUTE;

        this.actions = new HashMap<String, List<IEventAction>>();
        this.triggers = new HashMap<ITrigger, String>();

        this.state = ENABLED;

        dx = 0;
        dy = 0;
    }

    public Element(int width, int height) {
        this(0, 0, width, height);
    }

    public void draw() {
        bindTexture();
    }

    public void bindTexture() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (textures != null) {
            mc.getTextureManager().bindTexture(textures);
        }
    }

    public void bindTexture(String s) {

    }

    public void style() {
        if (styler != null) {
            styler.style(this);
        }
    }

    /**
     * callback before dimensioning the Element. Enables changing element's
     * properties before rendering them.
     */
    public void update() {
        if (lastCode != -1 && nextChar < System.currentTimeMillis()) {
            keyTyped(lastChar, lastCode);
            nextChar = System.currentTimeMillis() + THRESHOLD_NEXT_KEY_TYPED;
        }
        doTrigger(Trigger.UPDATE);
    }

    /**
     * callback before drawing the Element. Enables the repositioning of
     * elements before actually drawing them
     */
    public void dimension() {
        int x = 2;
    }

    /**
     * Absolute x coordinate in the screen
     *
     * @return Absolute x coordinate in the screen
     */
    public int absX() {
        return (parent != null ? parent.getPaddingLeft() : 0) + x + dx;
    }

    /**
     * Absolute y coordinate in the screen
     *
     * @return Absolute y coordinate in the screen
     */
    public int absY() {
        return (parent != null ? parent.getPaddingTop() : 0) + y + dy;
    }

    /**
     * callback when a key is typed
     *
     * @return true if key consumes keyboard input
     */
    protected boolean keyPressed(char key, int code) {
        return false;
    }

    protected boolean keyTyped(char key, int code) {
        return false;
    }

    protected void keyReleased(int code) {
    }

    public boolean handleKeyboardInput() {
        boolean consume = false;
        char key = Keyboard.getEventCharacter();
        int code = Keyboard.getEventKey();
        if (Keyboard.getEventKeyState()) {
            consume = keyPressed(key, code) || consume;
            consume = keyTyped(key, code) || consume;
            this.lastChar = key;
            this.lastCode = code;
            this.nextChar = System.currentTimeMillis() + THRESHOLD_INITIAL_KEY_TYPED;
        } else {
            keyReleased(Keyboard.getEventKey());
            if (lastCode == code) {
                this.lastCode = -1;
                this.lastChar = 0;
            }
        }
        return consume;
    }

    /**
     * callback when the element is clicked on
     */
    protected boolean onPressed(int x, int y, int which) {
        return doTrigger(Trigger.PRESSED, x, y, which);
    }

    /**
     * callback when mouse scroll wheel is changed when hovering over this
     * element
     */
    protected boolean onScrolled(int x, int y, int amount) {
        return doTrigger(Trigger.SCROLLED, x, y, amount);
    }

    /**
     * callback when the element is released
     *
     * @param x
     * @param y
     */
    protected void onReleased(int x, int y, int which) {
        doTrigger(Trigger.RELEASED, x, y, which);
    }

    /**
     * callback when the mouse hovers over this element
     */
    protected void onHover(int x, int y) {
        doTrigger(Trigger.HOVER, x, y);
    }

    /**
     * callback when the mouse clicks twice on this element
     */
    protected boolean onDoublePressed(int x, int y, int which) {
        return doTrigger(Trigger.DOUBLE_PRESSED, x, y, which);
    }

    /**
     * callback when this element has been clicked on and the mouse is moved
     */
    protected boolean onMoveRelative(int x, int y, int which) {
        return doTrigger(Trigger.MOVERELATIVE, x, y, which);
    }

    protected boolean onMove(int x, int y, int which) {
        return doTrigger(Trigger.MOVE, x, y, which);
    }

    protected void onFocus() {
        doTrigger(Trigger.FOCUS);
        //TODO quick fix for bug when focus is lost while holding a key
        this.lastCode = -1;
    }

    /**
     * callback before focus is being released
     *
     * @return whether this element allows focus to be released
     */
    protected boolean onReleaseFocus() {
        doTrigger(Trigger.FOCUSLOST);

        return true;
    }

    protected void onAdded() {
        if (id != null) {
            getRoot().register(id, this);
        }
    }

    protected void onRemoved() {
    }

    protected boolean doTrigger(ITrigger trigger, Object... args) {
        String triggerName = triggers.get(trigger);
        if (triggerName != null) {
            trigger.trigger(triggerName, getRoot(), this, args);
            return true;
        }
        return false;
    }

    /**
     * @param mouseX mouse x coordinate relative to the parent container
     * @param mouseY mouse y coordinate relative to the parent container
     * @return true to consume mouse input
     */
    protected boolean handleMouseInput(int mouseX, int mouseY) {
        boolean consumeMouseInput = false; //should mouse input be consumed?

        for (int buttonFlag : BUTTONS) {
            if (Mouse.isButtonDown(mouseMap.get(buttonFlag))) {
                if (is(buttonFlag) /*
                                     * && xOffsetButtonPress - mouseX != x &&
									 * yOffsetButtonPress - mouseY != y
									 */) { //element is moved
                    consumeMouseInput |= onMove(mouseX, mouseY, buttonFlag);
                    consumeMouseInput |= onMoveRelative(x + mouseX - xOffsetButtonPress, y + mouseY - yOffsetButtonPress, buttonFlag);
                }
            } else if (is(buttonFlag)) {
                toggleOff(buttonFlag);
                onReleased(mouseX, mouseY, buttonFlag);
            }
        }
        Element root = getRoot();
        int x0 = root.absX();
        int x1 = root.absX() + root.width;
        int absMouseX = absX() + mouseX - root.getPaddingLeft();

        int y0 = root.absY();
        int y1 = root.absY() + root.height;
        int absMouseY = absY() + mouseY - root.getPaddingTop();

//		boolean inRootRange = MathUtil.inRange2D(absX() + mouseX, getRoot().absX(), getRoot().absX() + getRoot().width, absY() + mouseY, getRoot().absY(), getRoot().absY() + getRoot().height);
        boolean inRootRange = MathUtil.inRange2D(absMouseX, x0, x1, absMouseY, y0, y1);
        boolean inRange = MathUtil.inRange2D(mouseX, 0, width, mouseY, 0, height);
        if (inRange && inRootRange) { //check if mouse touches the element

            boolean aButtonIsDown = false; //is a button pressed?
            for (int buttonFlag : BUTTONS) {
                if (Mouse.isButtonDown(mouseMap.get(buttonFlag))) {
                    aButtonIsDown = true;
                    if (!is(buttonFlag) && is(HOVERING)) {
                        toggleOn(buttonFlag);
                        xOffsetButtonPress = mouseX;
                        yOffsetButtonPress = mouseY;
                        consumeMouseInput = onPressed(mouseX, mouseY, buttonFlag) || consumeMouseInput;

                        //handle double clicks
                        long lastClicked = lastClicks.get(buttonFlag);
                        long currentTime = System.currentTimeMillis();
                        lastClicks.put(buttonFlag, currentTime);
                        if (currentTime - lastClicked < THRESHOLD_DOUBLE_PRESS) {
                            consumeMouseInput = onDoublePressed(mouseX, mouseY, buttonFlag) || consumeMouseInput;
                        }

                        break; //exit looping through buttons; only 1 button press is allowed at a time
                    }
                }
            }

            //handle hovering
            if (!aButtonIsDown) {
                toggleOn(HOVERING);
                onHover(mouseX, mouseY);
            }

            //handle scroll input
            if (getRoot().scrollMovement != 0) {
                consumeMouseInput = onScrolled(x, y, getRoot().scrollMovement) || consumeMouseInput;
            }

        } else {
            toggleOff(HOVERING);
        }

        return consumeMouseInput;
    }

    public boolean is(int flag) {
        return (state & flag) == flag;
    }

    public boolean isEnabled() {
        return is(ENABLED);
    }

    @Override
    public String toString() {
        return String.format("%s[x=%d, y=%d, width=%d, height=%d, dx=%d, y=%d]", getClass().getCanonicalName(), x, y, width, height, dx, dy);
    }

    public boolean isLeftClicked() {
        return is(LEFT_CLICKED);
    }

    public boolean isRightClicked() {
        return is(RIGHT_CLICKED);
    }

    public boolean isMiddleClicked() {
        return is(MIDDLE_CLICKED);
    }

    public boolean isHovering() {
        return is(HOVERING);
    }

    public boolean isFocused() {
        return is(FOCUSED);
    }

    public Element enabled(boolean enabled) {
        toggle(ENABLED, enabled);
        return this;
    }

    public Element toggle(int s, boolean on) {
        this.state = on ? state | s : ((Integer.MAX_VALUE - s) & state);
        return this;
    }

    public Element toggle(int s) {
        return toggle(s, !is(s));
    }

    public Element toggleOff(int s) {
        toggle(s, false);
        return this;
    }

    public Element toggleOn(int s) {
        toggle(s, true);
        return this;
    }

    public boolean inRange(int x, int y) {
        return MathUtil.inRange2D(x, absX(), absX() + width, y, absY(), absY() + height);
    }

    public Element width(int width) {
        this.width = width;
        return this;
    }

    public Element height(int height) {
        this.height = height;
        return this;
    }

    public Element widthDimension(LayoutDimension dimension) {
        this.widthDimension = dimension;
        return this;
    }

    public Element heightDimension(LayoutDimension dimension) {
        this.heightDimension = dimension;
        return this;
    }

    public Element x(int x) {
        this.x = x;
        return this;
    }

    public Element y(int y) {
        this.y = y;
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getPaddedHeight() {
        return paddingTop + height + paddingBottom;
    }

    public int getWidth() {
        return width;
    }

    public int getPaddedWidth() {
        return paddingLeft + width + paddingRight;
    }

    public LayoutDimension getWidthDimension() {
        return widthDimension;
    }

    public LayoutDimension getHeightDimension() {
        return heightDimension;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public ElementContainer getParent() {
        return parent;
    }

    public ElementRootContainer getRoot() {
        if (root == null) {
            root = parent.getRoot();
        }
        return root;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public Element padding(int padding) {
        return padding(padding, padding, padding, padding);
    }

    public Element padding(int top, int right, int bottom, int left) {
        this.paddingTop = top;
        this.paddingRight = right;
        this.paddingBottom = bottom;
        this.paddingLeft = left;
        return this;
    }

    public Element paddingTop(int padding) {
        this.paddingTop = padding;
        return this;
    }

    public Element paddingRight(int padding) {
        this.paddingRight = padding;
        return this;
    }

    public Element paddingBottom(int padding) {
        this.paddingBottom = padding;
        return this;
    }

    public Element paddingLeft(int padding) {
        this.paddingLeft = padding;
        return this;
    }

    public Element paddingHorizontal(int padding) {
        return paddingLeft(padding).paddingRight(padding);
    }

    public Element paddingVertical(int padding) {
        return paddingTop(padding).paddingBottom(padding);
    }

    public Element align(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    protected void renderBorder(int thickness, int color) {
        //top border
        drawRectangle(thickness, 0, width - thickness, thickness, color);
        //bottom border
        drawRectangle(thickness, height - thickness, width - thickness, height, color);
        //left border
        drawRectangle(0, 0, thickness, height, color);
        //right border
        drawRectangle(width - thickness, 0, width, height, color);
    }

    public void drawTexturedModalRect(int xOffset, int yOffset, int u, int v, int width, int height) {
        super.drawTexturedModalRect(absX() + xOffset, absY() + yOffset, u, v, width, height);
    }

    public void drawRectangle(int xOffset, int yOffset, int width, int height, int color) {
        int xLeft = absX() + xOffset;
        int yLeft = absY() + yOffset;
        Gui.drawRect(xLeft, yLeft, xLeft + width, yLeft + height, color);
    }

    protected void drawBorderFromTextures(Vector4 edgeTop, Vector4 edgeRight, Vector4 edgeBottom, Vector4 edgeLeft, Vector4 cornerTL, Vector4 cornerTR, Vector4 cornerBR, Vector4 cornerBL) {

        //draw top edge
        int toDraw = getPaddedWidth() - (xDiff(cornerTL) + xDiff(cornerTR));
        while (toDraw > 0) {
            //actual width being drawn this iteration
            int drawWidth = toDraw < xDiff(edgeTop) ? toDraw : xDiff(edgeTop);
            drawTexturedModalRect(getPaddedWidth() - (toDraw + xDiff(cornerTR)), 0, edgeTop.x, edgeTop.y, drawWidth, yDiff(edgeTop));
            toDraw -= drawWidth;
        }

        //draw bottom edge
        toDraw = getPaddedWidth() - (xDiff(cornerBL) + xDiff(cornerBR));
        while (toDraw > 0) {
            //actual width being drawn this iteration
            int drawWidth = toDraw < xDiff(edgeBottom) ? toDraw : xDiff(edgeBottom);
            drawTexturedModalRect(getPaddedWidth() - (toDraw + xDiff(cornerBR)), getPaddedHeight() - (yDiff(edgeBottom)), edgeBottom.x, edgeBottom.y, drawWidth, yDiff(edgeBottom));
            toDraw -= drawWidth;
        }

        //draw left edge
        toDraw = getPaddedHeight() - (yDiff(cornerTL) + yDiff(cornerBL));
        while (toDraw > 0) {
            //actual height being drawn this iteration
            int drawHeight = toDraw < yDiff(edgeLeft) ? toDraw : yDiff(edgeLeft);
            drawTexturedModalRect(0, getPaddedHeight() - (toDraw + yDiff(cornerBL)), edgeLeft.x, edgeLeft.y, xDiff(edgeLeft), drawHeight);
            toDraw -= drawHeight;
        }

        //draw right edge
        toDraw = getPaddedHeight() - (yDiff(cornerTR) + yDiff(cornerBR));
        while (toDraw > 0) {
            //actual height being drawn this iteration
            int drawHeight = toDraw < yDiff(edgeRight) ? toDraw : yDiff(edgeRight);
            drawTexturedModalRect(getPaddedWidth() - xDiff(edgeRight), getPaddedHeight() - (toDraw + yDiff(cornerBR)), edgeRight.x, edgeRight.y, xDiff(edgeRight), drawHeight);
            toDraw -= drawHeight;
        }

        //draw top left corner
        drawTexturedModalRect(0, 0, cornerTL.x, cornerTL.y, xDiff(cornerTL), yDiff(cornerTL));

        //draw top right corner
        drawTexturedModalRect(getPaddedWidth() - xDiff(cornerTR), 0, cornerTR.x, cornerTR.y, xDiff(cornerTR), yDiff(cornerTR));

        //draw bottom left corner
        drawTexturedModalRect(0, getPaddedHeight() - yDiff(cornerBL), cornerBL.x, cornerBL.y, xDiff(cornerBL), yDiff(cornerBL));

        //draw bottom right corner
        drawTexturedModalRect(getPaddedWidth() - xDiff(cornerBR), getPaddedHeight() - yDiff(cornerBR), cornerBR.x, cornerBR.y, xDiff(cornerBR), yDiff(cornerBR));
    }

    protected void drawBorderFromTexture(IBorderTexture border) {
        drawBorderFromTextures(border.edgeTop(), border.edgeRight(), border.edgeBottom(), border.edgeLeft(), border.cornerTL(), border.cornerTR(), border.cornerBR(), border.cornerBL());
    }

    protected void drawBackgroundFromTexture(Vector4 bg) {
        drawBackgroundFromTextures(bg, nullVector4, nullVector4, nullVector4, nullVector4, nullVector4, nullVector4, nullVector4, nullVector4);
    }

    protected void drawBackgroundFromTextures(Vector4 bg, Vector4 edgeTop, Vector4 edgeRight, Vector4 edgeBottom, Vector4 edgeLeft, Vector4 cornerTL, Vector4 cornerTR, Vector4 cornerBR, Vector4 cornerBL) {

        //height to render
        int toDrawY = getPaddedHeight() - (yDiff(edgeTop) + yDiff(edgeBottom));
        //draw a horizontal row
        while (toDrawY > 0) {
            //actual height being drawn this iteration
            int drawHeight = toDrawY < yDiff(bg) ? toDrawY : yDiff(bg);
            int toDrawX = getPaddedWidth() - (xDiff(edgeLeft) + xDiff(edgeRight));
            while (toDrawX > 0) {
                //actual width being drawn this iteration
                int drawWidth = toDrawX < xDiff(bg) ? toDrawX : xDiff(bg);
                drawTexturedModalRect(getPaddedWidth() - (toDrawX + xDiff(edgeRight)), getPaddedHeight() - (toDrawY + yDiff(edgeBottom)), bg.x, bg.y, drawWidth, drawHeight);
                toDrawX -= drawWidth;
            }
            toDrawY -= drawHeight;
        }
        drawBorderFromTextures(edgeTop, edgeRight, edgeBottom, edgeLeft, cornerTL, cornerTR, cornerBR, cornerBL);
    }

    protected void drawBackgroundFromTextures(IScalableTexture texture) {
        drawBackgroundFromTextures(texture.getTexture(), texture.edgeTop(), texture.edgeRight(), texture.edgeBottom(), texture.edgeLeft(), texture.cornerTL(), texture.cornerTR(), texture.cornerBR(), texture.cornerBL());
    }

    protected void drawStatefulBackgroundFromTextures(IStateFulBackground<ElementState> bg, ElementState state) {
        drawBackgroundFromTextures(bg.backgroundForState(state));
    }

    protected void drawStatefulBackgroundFromTextures(IStateFulBackground<ElementState> bg) {
        ElementState state = isEnabled() ? isLeftClicked() ? ElementState.ACTIVATED : isHovering() ? ElementState.HOVERING : ElementState.NORMAL : ElementState.DISABLED;
        drawStatefulBackgroundFromTextures(bg, state);
    }

    protected void drawTexture(ITexture texture, int x, int y, int width, int height, int du, int dv) {
        drawTexturedModalRect(x, y, texture.getTexture().x + du, texture.getTexture().y + dv, width, height);
    }

    protected void drawTexture(ITexture texture, int x, int y, int width, int height) {
        drawTexture(texture, x, y, width, height, 0, 0);
    }

    protected void drawTexture(ITexture texture, int x, int y) {
        drawTexture(texture, x, y, xDiff(texture.getTexture()), yDiff(texture.getTexture()));
    }

    protected void drawTexture(ITexture texture) {
        drawTexture(texture, 0, 0);
    }


    protected void drawTextureCentered(ITexture texture) {
        drawTexture(texture, (width - xDiff(texture.getTexture())) / 2, (height - yDiff(texture.getTexture())) / 2);
    }

    public void drawColoredBorder(int x, int y, int width, int height, int borderSize, int color) {

        int drawWidthHorizontal = width;
        int drawHeightHorizontal = borderSize;

        int drawWidthVertical = borderSize;
        int drawHeightVertcial = height - 2 * borderSize;


        drawRectangle(x, y, drawWidthHorizontal, drawHeightHorizontal, color);
        drawRectangle(x, y + height - borderSize, drawWidthHorizontal, drawHeightHorizontal, color);

        drawRectangle(x + width - borderSize, y + borderSize, drawWidthVertical, drawHeightVertcial, color);
        drawRectangle(x, y + borderSize, drawWidthVertical, drawHeightVertcial, color);
    }

    protected void drawStringWithShadow(String s, int x, int y, int color) {
        if ((color & 0xff) == 0) {
            color |= 255;
        }
        fontRenderer.drawStringWithShadow(s, absX() + x, absY() + y, color);
    }

    protected void drawString(String s, int x, int y, int color) {
        if ((color & 0xff) == 0) {
            color |= 255;
        }
        fontRenderer.drawString(s, absX() + x, absY() + y, color);
    }

    @Override
    public void deserialize(DataInput in) throws IOException {
        if (saveDimensions) {
            this.x = in.readInt();
            this.y = in.readInt();
            this.width = in.readInt();
            this.height = in.readInt();
        }
    }

    @Override
    public void serialize(DataOutput out) throws IOException {
        if (saveDimensions) {
            out.writeInt(x);
            out.writeInt(y);
            out.writeInt(width);
            out.writeInt(height);
        }
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public Minecraft getMinecraft() {
        return mc;
    }

    public ResourceLocation getTextures() {
        return textures;
    }

    public Element trigger(ITrigger trigger, String name) {
        triggers.put(trigger, name);
        return this;
    }

    public Element trigger(ITrigger trigger, INamed name) {
        return trigger(trigger, name.getName());
    }

    public Element styler(IElementStyler styler) {
        this.styler = styler;
        return this;
    }

    public Element action(String name, IEventAction action) {
        List<IEventAction> eventActions = actions.get(name);
        if (eventActions == null) {
            eventActions = new ArrayList<IEventAction>();
        }
        eventActions.add(action);
        actions.put(name, eventActions);
        return this;
    }

    public Element action(INamed name, IEventAction action) {
        return action(name.getName(), action);
    }

    public void onEvent(String name, Object[] args, Element e) {
        List<IEventAction> eventActions = actions.get(name);
        if (eventActions != null) {
            for (IEventAction action : eventActions) {
                action.onEvent(name, args, getRoot(), e);
            }
        }
    }

    public Element id(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public <E extends Element> E lookup(String id, Class<E> cls) {
        Element found = getRoot().lookup(id);
        return found != null && cls.isInstance(found) ? cls.cast(found) : null;
    }
}
