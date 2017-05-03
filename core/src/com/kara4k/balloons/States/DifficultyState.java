package com.kara4k.balloons.States;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.kara4k.balloons.AdMobHandler;
import com.kara4k.balloons.Balloons;

public class DifficultyState extends State implements InputProcessor {

    private Texture easy;
    private Texture medium;
    private Texture hard;
    private Texture bg;
    private Rectangle easyRect;
    private Rectangle mediumRect;
    private Rectangle hardRect;

    public DifficultyState(GameStateManager gsm,  AdMobHandler handler) {
        super(gsm, handler);
        camera.setToOrtho(false, Balloons.WIDTH, Balloons.HEIGHT);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);


        bg = new Texture("statsbg.jpg");
        easy = new Texture("easy.png");
        medium = new Texture("medium.png");
        hard = new Texture("hard.png");

        easyRect();
        mediumRect();
        hardRect();

    }

    private void hardRect() {
        hardRect = new Rectangle();
        hardRect.x = 0;
        hardRect.y = Balloons.HEIGHT / 2 - easy.getHeight() / 2 - 100;
        hardRect.width = Balloons.WIDTH;
        hardRect.height = hard.getHeight() + 5;
    }

    private void mediumRect() {
        mediumRect = new Rectangle();
        mediumRect.x = 0;
        mediumRect.y = Balloons.HEIGHT / 2 - easy.getHeight() / 2 ;
        mediumRect.width = Balloons.WIDTH;
        mediumRect.height = medium.getHeight() + 5;
    }

    private void easyRect() {
        easyRect = new Rectangle();
        easyRect.x = 0;
        easyRect.y = Balloons.HEIGHT / 2 - easy.getHeight() / 2 + 100;
        easyRect.width = Balloons.WIDTH;
        easyRect.height = easy.getHeight() + 5;
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouse);
            if (easyRect.contains(mouse.x, mouse.y)) {
                gsm.set(new PlayState(gsm,handler, 1, 5, PlayState.EASY));
            }
            if (mediumRect.contains(mouse.x, mouse.y)) {
                gsm.set(new PlayState(gsm,handler, 1, 5, PlayState.MEDIUM));
            }
            if (hardRect.contains(mouse.x, mouse.y)) {
                gsm.set(new PlayState(gsm, handler, 1, 5, PlayState.HARD));
            }

        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bg, 0,0);
        batch.draw(easy, Balloons.WIDTH / 2 - easy.getWidth() / 2, Balloons.HEIGHT / 2 - easy.getHeight() / 2 + 100);
        batch.draw(medium, Balloons.WIDTH / 2 - medium.getWidth() / 2, Balloons.HEIGHT / 2 - medium.getHeight() / 2);
        batch.draw(hard, Balloons.WIDTH / 2 - hard.getWidth() / 2, Balloons.HEIGHT / 2 - hard.getHeight() / 2 - 100);
        batch.end();
    }

    @Override
    public void dispose() {
        easy.dispose();
        medium.dispose();
        hard.dispose();
        bg.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.BACK) || (keycode == Input.Keys.ESCAPE)) {
            gsm.set(new MainMenuState(gsm, handler));
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
