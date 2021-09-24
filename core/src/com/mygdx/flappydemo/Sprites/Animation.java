package com.mygdx.flappydemo.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {

    private Array<TextureRegion> frames;
    private float maxFrameTime; // maximum frame time
    private float curFrameTime; // frame time so far
    private int frameCount; // num of total frames
    private int frame; // index of frame (from 0 to 2)

    public Animation(TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<>();
        // width of each bird pic
        int eachFrameWidth = region.getRegionWidth() / frameCount;

        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * eachFrameWidth, 0, eachFrameWidth, region.getRegionHeight()));
        }
        // num of total frames
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
    }

    public void update(float dt) {
        curFrameTime += dt;
        // if current frame time is more that maximum time
        if (curFrameTime > maxFrameTime) {
            frame++;
            curFrameTime = 0;
        }
        // if we got to the last pic we go to the first one
        if (frame >= frameCount)
            frame = 0;
    }

    public TextureRegion getFrame() {
        return frames.get(frame);
    }
}
