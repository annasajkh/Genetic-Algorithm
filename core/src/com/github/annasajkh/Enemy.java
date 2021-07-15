package com.github.annasajkh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class Enemy extends Circle
{

	public Enemy()
	{
		super(MathUtils.random(Gdx.graphics.getWidth()), MathUtils.random(Gdx.graphics.getHeight()), MathUtils.random(10,100), Color.RED);
	}
	
	
	public boolean intersect(Circle other)
	{
		final float dx = other.x - x;
		final float dy = other.y - y;
		
		return (this.radius + other.radius) * (this.radius + other.radius) > (dx * dx) + (dy * dy);
	}


}
