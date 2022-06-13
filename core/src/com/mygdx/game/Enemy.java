package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends GameObject{

    int health;
    int speed;

    public Enemy(Texture objectImg, Rectangle objectRect) {
        super(objectImg, objectRect);
    }
}
