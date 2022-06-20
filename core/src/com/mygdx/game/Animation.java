package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;

    public Animation(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        //this for loop divides the frames in one big picture givent the framewidth
        for(int i = 0; i < frameCount; i++){
            frames.add(new TextureRegion(region, i*frameWidth, 0, frameWidth, region.getRegionHeight()));

        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    //delta time change in time between render cycles
    public void update(float dt){
        currentFrameTime += dt; //how long frame time has been in game
        if(currentFrameTime > maxFrameTime){
            frame ++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount){
            frameCount = 0; //animation recycles
        }
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }

}
