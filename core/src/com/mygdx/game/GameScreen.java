package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
import java.util.Random;

public class GameScreen implements Screen {

    private MyGdxGame game;

    Texture img;
    private Texture dropImage;
    private Texture bucketImage;
    private Texture rockImage;
    private Rectangle rock;
    private Sound dropSound;
    private Music rainMusic;
    public OrthographicCamera camera;
    public SpriteBatch batch;

    //creating hitbox and making that move
    private Rectangle bucket;

    private Array<Rectangle> raindrops;
    private Array<Enemy> rocks;

    private long lastDropTime;
    private long rockLastDropTime;
    Player bucketObject;

    Random rand = new Random();
    Texture bulletImg;
    //Bullet is where player is
    Rectangle bulletRect = new Rectangle(400,400,32,32);
    Sprite playerSprite;
    Bullet bullet1;

    private Texture background;


    public GameScreen(MyGdxGame game){
        this.game = game;

        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");

        //loading images
        dropImage = new Texture(Gdx.files.internal("drop.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.jpg"));
        rockImage = new Texture(Gdx.files.internal("rockegg.jpg"));
        bulletImg = new Texture(Gdx.files.internal("bullet.png"));
        background = new Texture(Gdx.files.internal("grass tile 1.png"));



        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // looping music



        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);

        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        bucketObject = new Player(bucketImage,bucket,200,0,0, camera);
        bucketObject.generateSprite();

        raindrops = new Array<Rectangle>();
        rocks = new Array<Enemy>();
        spawnRaindrop();
        spawnRock();

        //bucketObject.getSprite().setRotation(34);
        playerSprite = bucketObject.getSprite();

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

        //get random number
        int randSpawn = rand.nextInt(800);
        int xOry = rand.nextInt(2);
        //random spawning side
        if(xOry == 0){
            Rectangle rockRect = new Rectangle(randSpawn,0,64,64);
            Enemy rock = new Enemy(rockImage,rockRect,2,10);
            rocks.add(rock);
        }else{
            Rectangle rockRect = new Rectangle(0,randSpawn,64,64);
            Enemy rock = new Enemy(rockImage,rockRect,2,10);
            rocks.add(rock);
        }




        rockLastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {
        //start play music when screen is shown
        rainMusic.play();
    }

    @Override
    public void render(float delta) {
        //drawing sprites
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //batch.draw(background,0,0,800,800);
        //draws the bullet
        bucketObject.draw(batch);



        //set origin center
        // set rotation
        playerSprite.setSize(32,32);
        playerSprite.setPosition(bucketObject.getRect().x,bucketObject.getRect().y);
        playerSprite.setOriginCenter();
        playerSprite.draw(batch);
        playerSprite.setRotation(-(float)Math.toDegrees(bucketObject.playerToMouse(camera)));




        //batch.draw(bucketImage,bucket.x,bucket.y,64,64);
        //batch.draw(bucketObject.getObjectImg(),bucketObject.getRect().x,bucketObject.getRect().y,32,32);
        for (Enemy rock: rocks){
            batch.draw(rockImage,rock.getRect().x,rock.getRect().y,64,64); //displaying egg smaller
        }

        for(Rectangle raindrop: raindrops){
            batch.draw(dropImage, raindrop.x,raindrop.y);
        }


        //checking input based on on mouse location
		/*
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2; //64/2 because the bucket should be in the center of the cursor
		}
		*/

        //checks for player movement keyboard input and make sure it dont go outta bounds
        bucketObject.checkMove();
        bucketObject.checkBound(800,800);
        bucketObject.update(camera);
        bucketObject.bulletCollison(rocks);



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



        /**
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
         */

        for(Iterator<Enemy> iter = rocks.iterator(); iter.hasNext();){
            Enemy rock = iter.next();

            //checks if rock collided with player
            if(rock.checkCollision(bucketObject)){
                //System.out.println(true);
                game.setScreen(new DeathScreen(game));
            }
            rock.chaseObject(bucketObject);
        }



        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        rockImage.dispose();
        bucketImage.dispose();
        rainMusic.dispose();
        batch.dispose();
    }
}
