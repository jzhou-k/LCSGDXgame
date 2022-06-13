package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
import java.util.concurrent.RecursiveAction;

//yes this is sprint2

public class MyGdxGame extends ApplicationAdapter {

	Texture img;
	private Texture dropImage;
	private Texture bucketImage;
	private Texture rockImage;
	private Rectangle rock;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	//creating hitbox and making that move
	private Rectangle bucket;

	private Array<Rectangle> raindrops;
	private Array<Rectangle> rocks;
	private long lastDropTime;
	private long rockLastDropTime;
	Player bucketObject;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		//loading images
		dropImage = new Texture(Gdx.files.internal("drop.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.jpg"));
		rockImage = new Texture(Gdx.files.internal("rockegg.jpg"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// looping music
		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 800);

		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		bucketObject = new Player(bucketImage,bucket,0,0);

		raindrops = new Array<Rectangle>();
		rocks = new Array<Rectangle>();
		spawnRaindrop();
		spawnRock();

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//batch.draw(bucketImage,bucket.x,bucket.y,64,64);
		batch.draw(bucketObject.getObjectImg(),bucketObject.getRect().x,bucketObject.getRect().y,32,32);
		for (Rectangle rock: rocks){
			batch.draw(rockImage,rock.x,rock.y,64,64); //displaying egg smaller
		}

		for(Rectangle raindrop: raindrops){
			batch.draw(dropImage, raindrop.x,raindrop.y);
		}
		batch.end();

		//checking input based on on mouse location
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2; //64/2 because the bucket should be in the center of the cursor
		}


		bucketObject.checkMove();

		//checking how much time passed, if 1 second passed since last drop time, spawn raindrop
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
			spawnRaindrop();
			spawnRock();
		}

		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
			Rectangle raindrop = iter.next();
			//check if raindrop overlaps bucket then removes raindrop
			if(raindrop.overlaps(bucket)){
				dropSound.play();
				iter.remove();
			}
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0) iter.remove();
		}

		for(Iterator<Rectangle> iter = rocks.iterator(); iter.hasNext();){
			Rectangle rock = iter.next();
			//making  rockegg move
			Vector2 bucketPos = new Vector2(bucket.getPosition(new Vector2()));
			Vector2 direction = new Vector2();
			direction.x = (bucket.x - rock.x);
			direction.y = (bucket.y - rock.y);
			direction.nor(); //returns a unit vector, essentially a direction vector
			rock.x += direction.x  * 2; //5 is speed
			rock.y += direction.y * 2;

		}



	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	private void spawnRock(){
		Rectangle rock = new Rectangle(0,0,64,64);
		rocks.add(rock);
		Vector2 rockeggPos = new Vector2(rock.getPosition(new Vector2()));
		rockLastDropTime = TimeUtils.nanoTime();
	}


	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();

	}
}
