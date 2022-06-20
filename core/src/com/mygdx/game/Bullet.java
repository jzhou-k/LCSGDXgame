package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


import java.util.Iterator;

public class Bullet extends GameObject{

    Vector2 directionVector;
    float angle;
    Sprite bulletSprite;

    public Bullet(Texture objectImg, Rectangle objectRect, int speed, Vector2 directionVector, float radian) {
        super(objectImg, objectRect, speed);
        this.directionVector = directionVector;
        this.angle = (float)Math.toDegrees(radian);
        bulletSprite = new Sprite(objectImg);
        bulletSprite.setOriginCenter();
        //System.out.println(angle);
        bulletSprite.setRotation(-angle);
    }

    @Override
    public void update() {

    }

    public boolean checkBound(){
        return (getRect().x <= 0 || getRect().y <= 0 ) || (getRect().x + getRect().width >= 800 || getRect().y + getRect().height >= 800);
    }

    public void move(){
        getRect().x += getSpeed() * directionVector.x * Gdx.graphics.getDeltaTime();
        getRect().y += getSpeed() * directionVector.y * Gdx.graphics.getDeltaTime();
    }

}
