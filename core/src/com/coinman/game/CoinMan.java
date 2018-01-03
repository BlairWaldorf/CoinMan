package com.coinman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background; // background variable
	Texture[] man;
	int manState = 0;
	int pause = 0;
	float gravity = 0.2f;
	float velocity = 0;
	int manY = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();

		// creating backgroung object
		background = new Texture("bg.png");

		// character animation
		// Array created where each of the "animations images" are defined in one position of the array. It's gives a movement illusion.
		man = new Texture[4];
		man[0] = new Texture("frame-1.png");
		man[1] = new Texture("frame-2.png");
		man[2] = new Texture("frame-3.png");
		man[3] = new Texture("frame-4.png");

		manY = Gdx.graphics.getHeight()/2;
	}

	@Override
	public void render () {
		batch.begin();

		//defining image to show, position x, position y, size w and h matching any screen
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if(Gdx.input.justTouched()){
			velocity = -10;
		}

		if(pause < 8)
		{
			pause++;
		}
		else
		{
			pause = 0;
			if (manState < 3)
			{
				manState++;
			}
			else
			{
				manState = 0;
			}
		}

		velocity += gravity;
		manY -= velocity;

		if(manY <= 0){
			manY = 0;
		}

		//Send character to screen
		batch.draw(man[manState], Gdx.graphics.getWidth()/2 - man[manState].getWidth()/2, manY);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
