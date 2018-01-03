package com.coinman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background; // background variable
	Texture[] man;

	//Character
	int manState = 0;
	int pause = 0;
	float gravity = 0.4f;
	float velocity = 0;
	int manY = 0;
	Rectangle manBox;

	//Coins
	ArrayList<Integer> coinsXs = new ArrayList<Integer>();
	ArrayList<Integer> coinsYs = new ArrayList<Integer>();
	ArrayList<Rectangle> coinRectangles = new ArrayList<Rectangle>();
	Texture coin;
	int coinCounter;
	Random random;

	//Bombs
	ArrayList<Integer> bombXs = new ArrayList<Integer>();
	ArrayList<Integer> bombYs = new ArrayList<Integer>();
	ArrayList<Rectangle> bombRectangles = new ArrayList<Rectangle>();
	Texture bomb;
	int bombCounter;

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
		//Defines Y initial position of character
		manY = Gdx.graphics.getHeight()/2;

		coin = new Texture("coin.png");
		bomb = new Texture("bomb.png");
		random = new Random();
	}

	//Creating coins method
	public void makeCoin(){
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		coinsYs.add((int)height);
		coinsXs.add(Gdx.graphics.getWidth());
	}

	//Creating bomb method
	public void makeBomb(){
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		bombYs.add((int)height);
		bombXs.add(Gdx.graphics.getWidth());
	}

	@Override
	public void render () {
		batch.begin();

		//defining image to show, position x, position y, size w and h matching any screen
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//BOMBS
		if(bombCounter < 100){
			bombCounter++;
		}else{
			bombCounter = 0;
			makeBomb();
		}

		bombRectangles.clear();
		for(int i = 0; i < bombXs.size();i++){
			batch.draw(bomb, bombXs.get(i), bombYs.get(i));
			bombXs.set(i, bombXs.get(i) - 8);
			//defining bombs hitboxes
			bombRectangles.add(new Rectangle(bombXs.get(i), bombYs.get(i), bomb.getHeight(), bomb.getWidth()));
		}

		//COINS
		if(coinCounter < 100){
			coinCounter++;
		}else{
			coinCounter = 0;
			makeCoin();
		}

		coinRectangles.clear();
		for(int i = 0; i < coinsXs.size();i++){
			batch.draw(coin, coinsXs.get(i), coinsYs.get(i));
			coinsXs.set(i, coinsXs.get(i) - 4);
			//defining coins hitboxes
			coinRectangles.add(new Rectangle(coinsXs.get(i), coinsYs.get(i), coin.getWidth(), coin.getHeight()));
		}

		if(Gdx.input.justTouched()){
			velocity = -30;
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

		//defining man hitbox
		manBox = new Rectangle(Gdx.graphics.getWidth()/2 - man[manState].getWidth()/2, manY, man[manState].getWidth(), man[manState].getHeight());

		//verifying hits of bombs and coins
		for ( int i = 0; i < coinRectangles.size();i++){
			if(Intersector.overlaps(manBox, coinRectangles.get(i))){
				Gdx.app.log("COIN", "COLISION");
			}
		}
		for ( int i = 0; i < bombRectangles.size();i++){
			if(Intersector.overlaps(manBox, bombRectangles.get(i))){
				Gdx.app.log("BOMB", "COLISION");
			}
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
