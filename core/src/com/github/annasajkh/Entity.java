package com.github.annasajkh;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class Entity extends Circle
{
	float[] DNA = new float[300];
	int index;
	float score;
	float speed = 200;
	boolean dieByEnemy = false;
	

	public Entity(float x, float y, boolean generateDNA)
	{
		super(x, y, 5, new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1));
		
		if(generateDNA)
		{
			for(int i = 0; i < DNA.length; i++)
			{
				DNA[i] = MathUtils.random() * MathUtils.PI2;
			}			
		}
	}
	

	@Override
	public void update(float delta)
	{
		if(index < DNA.length)
		{
			float addX = MathUtils.cos(DNA[index]);
			float addY = MathUtils.sin(DNA[index]);
			
			this.x += addX * speed * delta;
			this.y += addY * speed * delta;
			
			index++;
		}
		
		for(Enemy enemy : Core.enemies)
		{
			if(enemy.intersect(this))
			{
				index = DNA.length;
				dieByEnemy = true;
			}
		}
	}
	
	
	public static float[] crossover(Entity entity, Entity otherEntity)
	{
		int crossPoint = MathUtils.random(0, entity.DNA.length - 1);
		
		
		float[] newDNA = new float[entity.DNA.length];
		
		for(int i = 0; i < crossPoint; i++)
		{
			newDNA[i] = entity.DNA[i];
		}
		
		for(int i = crossPoint; i < entity.DNA.length; i++)
		{
			newDNA[i] = otherEntity.DNA[i];
		}
		
		return newDNA;
	}
	
	
	public static float[] mutate(float[] DNA, float chance)
	{
		for(int i = 0; i < DNA.length; i++)
		{
			if(MathUtils.random() <= chance)
			{
				DNA[i] = DNA[i] + new float[] {0.1f, -0.1f}[MathUtils.random(0, 1)];
				DNA[i] = MathUtils.clamp(DNA[i], 0, MathUtils.PI2);
			}
		}
		return DNA;
	}
	
	public float getScore()
	{
		return score;
	}
}
