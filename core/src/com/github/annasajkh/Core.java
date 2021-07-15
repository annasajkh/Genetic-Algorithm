package com.github.annasajkh;

import java.util.Comparator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class Core extends ApplicationAdapter
{
	
	ShapeRenderer shapeRenderer;
	
	Array<Entity> entities;
	public static Array<Enemy> enemies;
	
	public static Circle target;
	public static int populationSize = 2000;
	public static int bestSize = (int) (populationSize * 0.2f);

	@Override
	public void create()
	{
		shapeRenderer = new ShapeRenderer();
		
		entities = new Array<>();
		enemies = new Array<>();
		
		for(int i = 0; i < populationSize; i++)
		{
			entities.add(new Entity(Gdx.graphics.getWidth() / 2, 5, true));
		}
		
		target = new Circle(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() - 30, 30, Color.GREEN);
		
		for(int i = 0; i < 5; i++)
		{
			Enemy enemy = new Enemy();
			
			while(enemy.intersect(target))
			{
				enemy = new Enemy();
			}
			
			for(Enemy otherEnemy : enemies)
			{
				if(!otherEnemy.equals(enemy))
				{					
					while(enemy.intersect(new Circle(otherEnemy.x, otherEnemy.y, otherEnemy.radius + 30, Color.WHITE)))
					{
						enemy = new Enemy();
					}
				}
			}
			
			enemies.add(enemy);
		}
		
	}
	
	public void update(float delta)
	{
		
		boolean allDead = true;
		
		for(Entity entity : entities)
		{
			entity.update(delta);
			
			if(entity.index < entity.DNA.length)
			{
				allDead = false;
			}
		}
		
		if(allDead)
		{
			for(Entity entity : entities)
			{
				entity.score = Vector2.dst(entity.x, entity.y, Core.target.x, Core.target.y);
				
				if(entity.dieByEnemy)
				{
					entity.score += 100;
				}
			}
			
			
			
			entities.sort(Comparator.comparing(e -> e.getScore()));
			entities.removeRange(bestSize, entities.size - 1);
			Array<Entity> bestEntities = new Array<Entity>(entities);
			entities.clear();
			
			for(int i = bestSize; i < (populationSize - bestSize); i++)
			{
				Entity entity = new Entity(Gdx.graphics.getWidth() / 2, 5, false);
				
				Array<Entity> bestCopy = new Array<>(bestEntities);
				
				Entity parent = bestCopy.get(MathUtils.random(0, bestCopy.size - 1));
				bestCopy.removeValue(parent, false);
				Entity parent1 = bestCopy.get(MathUtils.random(0, bestCopy.size - 1));
				
				entity.DNA = Entity.mutate(Entity.crossover(parent,parent1), 0.6f);
				
				
				entities.add(entity);
			}
			
			
			for(Entity entity : bestEntities)
			{
				entity.score = 0;
				entity.index = 0;
				entity.x = Gdx.graphics.getWidth() / 2;
				entity.y = 5;
			}
			
			entities.addAll(bestEntities);
			
			
		}
	}

	@Override
	public void render()
	{
		update(Gdx.graphics.getDeltaTime());
		
		ScreenUtils.clear(0, 0, 0, 1);
		
		shapeRenderer.begin(ShapeType.Filled);
		
		for(Entity entity : entities)
		{
			entity.draw(shapeRenderer);
		}
		
		for(Enemy enemy : enemies)
		{
			enemy.draw(shapeRenderer);
		}
		
		target.draw(shapeRenderer);
		
		shapeRenderer.end();
	}

	@Override
	public void dispose()
	{
		shapeRenderer.dispose();
	}
}
