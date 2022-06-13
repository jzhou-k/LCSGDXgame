package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//texture
//rectangle
//need vector
public class GameObject{
    private Texture objectImg;
    private Rectangle objectRect;
    Vector2 objectPosition;


    public GameObject(Texture objectImg, Rectangle objectRect){
        this.objectImg = objectImg;
        this.objectRect = objectRect;
        this.objectPosition = new Vector2(objectRect.x, objectRect.y);
    }

    public Texture getObjectImg (){

        return  objectImg;
    }

    public Rectangle getRect(){
        return objectRect;
    }

}
