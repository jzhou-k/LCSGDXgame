package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class Bullet extends GameObject{



    public Bullet(Texture objectImg, Rectangle objectRect, int speed) {
        super(objectImg, objectRect, speed);
    }

    @Override
    public void update() {
        //moves up
        getRect().y += getSpeed() * Gdx.graphics.getDeltaTime();
    }

}
