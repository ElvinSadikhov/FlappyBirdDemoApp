package com.mygdx.flappydemo.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {

    public static final int MOVEMENT = 100;
    public static final int GRAVITY = -15;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Sound sound;

    private Texture bird;
    private Texture texture; // a pic of flying bird

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        bird = new Texture("bird.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));

        texture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);

        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());

    }

    public void update(float dt) {
        birdAnimation.update(dt);
        // gravity
        if (position.y > 0) {
            velocity.add(0, GRAVITY, 0);
        }

        velocity.scl(dt);// multiplies scl speed to delta time
        position.add(MOVEMENT * dt, velocity.y, 0);

        // don't let a bird get down from the screen
        if (position.y < 0) position.y = 0;

        // falling bird
        velocity.scl(1 / dt);

        bounds.setPosition(position.x, position.y);

    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return birdAnimation.getFrame();
    }

    public void jump() {
        velocity.y = 250;
        sound.play();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        bird.dispose();
        texture.dispose();
    }
}
