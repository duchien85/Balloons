package com.kara4k.balloons.States;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.kara4k.balloons.AdMobHandler;
import com.kara4k.balloons.Balloons;

public class GameOver extends State {

    public static final String TOP_LEVEL = "top_level";

    Texture bg;
    Texture retry;
    Texture menu;
    Texture gameOver;
    Rectangle retryRect;
    Rectangle menuRect;
    BitmapFont font;
    float difficulty;
    int level;

    public GameOver(GameStateManager gsm, AdMobHandler handler, int level, float diff) {
        super(gsm, handler);
        difficulty = diff;
        this.level = level;
        camera.setToOrtho(false, Balloons.WIDTH, Balloons.HEIGHT);

        bg = new Texture("statsbg.jpg");
        retry = new Texture("retry2.png");
        menu = new Texture("home2.png");
        gameOver = new Texture("game_over.png");

        retryRect = new Rectangle();
        retryRect.set(Balloons.WIDTH / 2 - retry.getWidth() / 2, Balloons.HEIGHT / 2 - 220, retry.getWidth(), retry.getHeight());

        menuRect = new Rectangle();
        menuRect.set(Balloons.WIDTH / 2 - menu.getWidth() / 2, Balloons.HEIGHT / 2 - 300, menu.getWidth(), menu.getHeight());


        font = new BitmapFont(Gdx.files.internal("snap.fnt"));
        font.setColor(Color.BROWN);

        Preferences prefs = Gdx.app.getPreferences(getPrefName());
        int topLevel = prefs.getInteger(TOP_LEVEL, 0);
        if (level > topLevel) {
            prefs.putInteger(TOP_LEVEL, level).flush();
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouse);
            if (retryRect.contains(mouse.x, mouse.y)) {
                gsm.set(new PlayState(gsm, handler, 1, 5, difficulty));
            } else if (menuRect.contains(mouse.x, mouse.y)) {
                gsm.set(new MainMenuState(gsm, handler));
            }
        }
    }

    private String getPrefName() {
        String prefsName = "";
        if (difficulty == PlayState.EASY) {
            prefsName = Balloons.EASY;
        }
        if (difficulty == PlayState.MEDIUM) {
            prefsName = Balloons.MEDIUM;
        }
        if (difficulty == PlayState.HARD) {
            prefsName = Balloons.HARD;
        }
        return prefsName;
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bg, 0, 0, Balloons.WIDTH, Balloons.HEIGHT);
        font.draw(batch, "Level " + level, Balloons.WIDTH / 2 - 55, Balloons.HEIGHT / 2);
        batch.draw(gameOver, Balloons.WIDTH/2 - gameOver.getWidth()/2, Balloons.HEIGHT/2  + 150);
        batch.draw(retry, Balloons.WIDTH / 2 - retry.getWidth() / 2, Balloons.HEIGHT / 2 - 220);
        batch.draw(menu, Balloons.WIDTH / 2 - menu.getWidth() / 2, Balloons.HEIGHT / 2 - 300);
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        font.dispose();
        retry.dispose();
        menu.dispose();
        gameOver.dispose();
    }
}
