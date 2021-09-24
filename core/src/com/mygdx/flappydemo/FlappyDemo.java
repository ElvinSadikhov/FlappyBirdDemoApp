package com.mygdx.flappydemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.flappydemo.states.GameStateManager;
import com.mygdx.flappydemo.states.MenuState;

public class FlappyDemo extends ApplicationAdapter {

    // width & height of a screen
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    private GameStateManager gsm;
    private SpriteBatch batch;
    private Music backgroundMusic;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        ScreenUtils.clear(1, 0, 0, 1);
        gsm.push(new MenuState(gsm)); // running starting menu
    }

    @Override
    public void render() {
        backgroundMusic.setLooping(true); // making it loop forever (endless)
        backgroundMusic.setVolume(0.1f); // 10% volume
        backgroundMusic.play();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
