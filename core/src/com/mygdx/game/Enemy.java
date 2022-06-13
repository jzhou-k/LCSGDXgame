package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends GameObject{

    int health;
    int speed;
    Vector2 direction;
    Vector2 objectDirection;

    public Enemy(Texture objectImg, Rectangle objectRect) {
        super(objectImg, objectRect);
    }

    public void chaseObject(GameObject chaseObject){

    }
}
