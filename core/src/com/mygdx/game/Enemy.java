package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends GameObject{

    int health;
    Vector2 direction;

    Vector2 objectDirection;
    private Animation enemyAnimation;

    public Enemy(Texture objectImg, Rectangle objectRect, int speed, int health) {
        super(objectImg, objectRect, speed);
        this.health = health;
        Texture texture = new Texture("enemyAnimation.png");
        enemyAnimation = new Animation(new TextureRegion(texture), 4, 0.5f);

    }

    public void update(float dt){
        enemyAnimation.update(dt);
    }

    public TextureRegion getFrame() {
        return enemyAnimation.getFrame();
    }

    public void chaseObject(GameObject chaseObject){
        objectDirection = new Vector2();
        objectDirection.x = (chaseObject.getRect().x - this.getRect().x);
        objectDirection.y = (chaseObject.getRect().y - this.getRect().y);
        objectDirection.nor();
        this.getRect().x += objectDirection.x*getSpeed();
        this.getRect().y += objectDirection.y*getSpeed();

    }

    public boolean checkCollision(Player player){
        return (this.getRect().overlaps(player.getRect()));
    }
}
