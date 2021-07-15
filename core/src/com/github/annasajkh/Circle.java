package com.github.annasajkh;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Circle extends GameObject
{
	
	float radius;
	Color color;
	
	public Circle(float x, float y, float radius, Color color)
	{
		super(x, y);
		
		this.radius = radius;
		this.color = color;
	}
	
	@Override
	public void update(float delta)
	{
		
	}
	
	@Override
	public void draw(ShapeRenderer shapeRenderer)
	{
		shapeRenderer.setColor(color);
		shapeRenderer.circle(x, y, radius);
	}

}
