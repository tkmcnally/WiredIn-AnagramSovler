package com.tkmcnally.wordunscrambler.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import aurelienribon.tweenengine.TweenAccessor;

public class TextFieldAccessor implements TweenAccessor<TextField> {

	public static final int HEIGHT_SCALE = 0;
	public static final int POSITION = 1;
	public static final int ALPHA = 2;

	@Override
	public int getValues(TextField target, int tweenType, float[] returnValues) {
		switch(tweenType) {
		case HEIGHT_SCALE:
			returnValues[0] = target.getHeight();
			return 1;
		case POSITION:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 3;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(TextField target, int tweenType, float[] newValues) {
		switch(tweenType) {
		case HEIGHT_SCALE:			
			target.setHeight(newValues[0]);
			break;
		case POSITION:			
			target.setPosition(newValues[0], newValues[1]);
			break;
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		default:
			assert false;
		}	
		
	}

}
