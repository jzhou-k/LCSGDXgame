package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

//texture
//rectangle
//need vector
public class GameObject{
    private Texture objectImg;
    private Rectangle objectRect;

    public GameObject(Texture objectImg, Rectangle objectRect){
        this.objectImg = objectImg;
        this.objectRect = objectRect;

    }

    public Texture getObjectImg (){

        return  objectImg;
    }

    public Rectangle getRect(){
        return objectRect;
    }

}
