package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.lib.gui.event.Trigger;
import com.besuikerd.autologistics.client.lib.gui.layout.Orientation;
import com.besuikerd.autologistics.client.lib.gui.texture.ITexture;
import com.besuikerd.autologistics.client.lib.gui.texture.Texture;
import com.besuikerd.autologistics.common.lib.util.MathUtil;
import com.besuikerd.autologistics.common.lib.util.tuple.TupleUtils;

public class ElementProgressBar extends Element{

	protected int progress;
	protected int max;

	/**
	 * background texture behind the progress
	 */
	protected ITexture backgroundTexture;
	
	/**
	 * texture that will be rendered as far as the progress is
	 */
	protected ITexture progressTexture;
	
	/**
	 * orientation the progress will progress to
	 */
	protected Orientation orientation;
	
	public ElementProgressBar(ITexture backgroundTexture, ITexture progressTexture, Orientation orientation, int progress, int max) {
		super(TupleUtils.xDiff(backgroundTexture.getTexture()), TupleUtils.yDiff(backgroundTexture.getTexture()));
		this.backgroundTexture = backgroundTexture;
		this.progressTexture = progressTexture;
		this.orientation = orientation;
		this.progress = progress;
		this.max = max;
	}
	
	public ElementProgressBar(ITexture backgroundTexture, ITexture progressTexture, Orientation orientation) {
		this(backgroundTexture, progressTexture, orientation, 0, 100);
	}
	
	@Override 
	public void draw() {
		super.draw();
		
		//render background
		drawTexture(backgroundTexture);
		
		//render progress texture
		switch(orientation){
			case HORIZONTAL:
				drawTexture(progressTexture, 0, 0, (int) Math.min(((double) progress / max) * TupleUtils.xDiff(progressTexture.getTexture()), TupleUtils.xDiff(progressTexture.getTexture())), TupleUtils.yDiff(progressTexture.getTexture()));
				break;
			case VERTICAL:
				double yOffset = (1 - getProgressPercentage()) * TupleUtils.yDiff(progressTexture.getTexture());
				drawTexture(progressTexture, 0, (int) yOffset , TupleUtils.xDiff(progressTexture.getTexture()), (int) Math.min(((double) progress / max) * TupleUtils.yDiff(progressTexture.getTexture()), TupleUtils.yDiff(progressTexture.getTexture())), 0, (int) Math.ceil(yOffset));
				break;
		}
	}
	
	public void onProgressChanged(double oldProgress, double oldMax, double newProgress, double newMax){
		doTrigger(Trigger.PROGRESS_CHANGED, oldProgress, oldMax, newProgress, newMax);
	}
	
	public int getProgress() {
		return progress;
	}
	
	public int getMax() {
		return max;
	}
	
	public double getProgressPercentage(){
		return (double) progress / max;
	}

	public ElementProgressBar progress(int progress) {
		return set(progress, this.max);
	}
	
	public ElementProgressBar progressPercentage(double percentage){
		return progress((int) percentage * max);
	}
	
	public ElementProgressBar max(int max){
		return set(this.progress, max);
	}
	
	public ElementProgressBar increment(int i){
		return progress(this.progress + i);
	}
	
	public ElementProgressBar increment(){
		return increment(1);
	}
	
	public ElementProgressBar decrement(){
		return increment(-1);
	}
	
	public ElementProgressBar reset(){
		return progress(0);
	}
	
	public ElementProgressBar set(int progress, int max){
		double oldProgress = this.progress;
		double oldMax = this.max;
		this.max = Math.max(0, max);
		this.progress = MathUtil.align(0, progress, max);
		
		onProgressChanged(oldProgress, oldMax, this.progress, this.max);
		return this;
	}

	public static ElementProgressBar progressBarArrow(){
		return new ElementProgressBar(Texture.PROGRESS_ARROW_BG, Texture.PROGRESS_ARROW_FULL, Orientation.HORIZONTAL);
	}
	
	public static ElementProgressBar progressBarBurn(){
		return new ElementProgressBar(Texture.PROGRESS_BURN_BG, Texture.PROGRESS_BURN_FULL, Orientation.VERTICAL);
	}
}
