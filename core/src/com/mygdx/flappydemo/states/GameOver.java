package com.mygdx.flappydemo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.flappydemo.FlappyDemo;

public class GameOver extends State {

    private Texture background;
    private Texture gameover;
    private BitmapFont font;
    private int score;

    public GameOver(GameStateManager gsm, int score) {
        super(gsm);
        this.score = score;
        camera.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        background = new Texture("bg.png");
        gameover = new Texture("gameover.png");
        font = new BitmapFont();
    }

    @Override
    protected void handleInput() {
        // if user touches the screen we run the game (call PlayState)
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, FlappyDemo.WIDTH, FlappyDemo.HEIGHT);
        sb.draw(gameover, camera.position.x - (gameover.getWidth() / 2), camera.position.y);
        font.draw(sb, "Your final score is: " + score, camera.position.x - (gameover.getWidth() / 2), camera.position.y - 100);
        sb.end();
    }

    @Override
    public void dispose() {
        gameover.dispose();
        background.dispose();
        font.dispose();
        // if user touches the screen we run the game (call PlayState)
        System.out.println("GameOverMenu Disposed");
    }
}
