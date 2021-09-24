package com.mygdx.flappydemo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.flappydemo.FlappyDemo;
import com.mygdx.flappydemo.Sprites.Bird;
import com.mygdx.flappydemo.Sprites.Tube;

public class PlayState extends State {

    public static final int TUBE_SPACING = 125; // distance between tubes(horizontal)
    public static final int TUBE_COUNT = 2;
    public static final int GROUND_Y_OFFSET = -30;

    private int score;
    private BitmapFont font;

    private Bird bird;
    private Texture background;
    private Texture ground;
    private Vector2 groundPos1, groundPos2; // we use 2 images

    private Array<Tube> tubes;
    private Array<Tube> tubesCHECKED; // keep track of tubes, counted for getting score


    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);
        background = new Texture("bg.png");
        camera.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);

        font = new BitmapFont();

        // 2 ground textures go one after another
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2(camera.position.x - camera.viewportWidth / 2 + ground.getWidth(), GROUND_Y_OFFSET);

        // creating tubes
        tubes = new Array<>();
        for (int i = 0; i < TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        tubesCHECKED = new Array<>();
        score = -1; // idk
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        camera.position.x = bird.getPosition().x + 80;

        for (int i = 0; i < tubes.size; i++) {
            Tube tube = tubes.get(i);
            // process of creating next tube (reposition a new one)
            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
                tubesCHECKED.removeValue(tube, false);
            }
            // if bird and tube collide or bird touches ground - YOU LOSE
            if (tube.collides(bird.getBounds()) || bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
                gsm.set(new GameOver(gsm, score));
            }
            // scoring system
            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPosTopTube().x && !tubesCHECKED.contains(tube, false)) {
                score++;
                tubesCHECKED.add(tube);
            }
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        // drawing background
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        // bird background
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
        // tube drawing
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        // ground drawing
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        // score drawing
        font.draw(sb, "Current score: " + score, camera.position.x - camera.viewportWidth / 2, camera.viewportHeight);

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        ground.dispose();
        font.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
        // just to make sure
        System.out.println("PlayState Disposed");
    }

    private void updateGround() {
        // in order to make ground textures go one after another
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }
}
