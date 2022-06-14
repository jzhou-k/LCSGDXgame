package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends GameObject{

    int health;
    Vector2 direction;

    Vector2 objectDirection;

    public Enemy(Texture objectImg, Rectangle objectRect, int speed, int health) {
        super(objectImg, objectRect, speed);
        this.health = health;
    }

    public void chaseObject(GameObject chaseObject){
        objectDirection = new Vector2();
        objectDirection.x = (chaseObject.getRect().x - this.getRect().x);
        objectDirection.y = (chaseObject.getRect().y - this.getRect().y);
        objectDirection.nor();
        this.getRect().x += objectDirection.x*getSpeed();
        this.getRect().y += objectDirection.y*getSpeed();

    }
}
