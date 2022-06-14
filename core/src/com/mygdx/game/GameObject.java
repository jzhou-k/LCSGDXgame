package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//texture
//rectangle
//need vector
public class GameObject{
    private Texture objectImg;
    private Rectangle objectRect;
    Vector2 objectPosition;

    private int speed;


    public GameObject(Texture objectImg, Rectangle objectRect, int speed){
        this.objectImg = objectImg;
        this.objectRect = objectRect;
        this.objectPosition = new Vector2(objectRect.x, objectRect.y);
        this.speed = speed;
    }

    public Texture getObjectImg (){

        return  objectImg;
    }

    public void update(){

    }

    public void draw(SpriteBatch batch){

    }

    public Rectangle getRect(){
        return objectRect;
    }

    public int getSpeed(){
        return  speed;
    }
}
