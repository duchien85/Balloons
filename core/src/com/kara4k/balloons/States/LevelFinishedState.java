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

public class LevelFinishedState extends State {

    public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

    public static final String POPPED = "poppedEasy";
    public static final String CLICKED = "clicked";

    float difficulty;

    int level;
    int lives;

    Texture bg;
    BitmapFont font;

    private Texture contBtn;
    private Rectangle contRect;

    public LevelFinishedState(GameStateManager gsm, AdMobHandler handler, int lvl, int lives, int popped, int clicks, float diff) {
        super(gsm, handler);

//        handler.showAds(true);

        difficulty = diff;

        camera.setToOrtho(false, Balloons.WIDTH, Balloons.HEIGHT);

        bg = new Texture("statsbg.jpg");
        contBtn = new Texture("next.png");
        font = new BitmapFont(Gdx.files.internal("snap.fnt"));
        font.setColor(Color.BROWN);


        level = lvl;
        this.lives = lives;

        contRect = new Rectangle();
        contRect.x = Balloons.WIDTH / 2 - contBtn.getWidth() / 2;
        contRect.y = Balloons.HEIGHT / 2  - 250;
        contRect.width = contBtn.getWidth();
        contRect.height = contBtn.getHeight();


        writePrefs(popped, clicks, getPrefName());


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


    private void writePrefs(int popped, int clicks, String prefName) {
        Preferences prefs = Gdx.app.getPreferences(prefName);
        long poppedTotal = prefs.getLong(POPPED, 0);
        long clickedTotal = prefs.getLong(CLICKED, 0);
        poppedTotal += popped;
        clickedTotal += clicks;
        prefs.putLong(POPPED, poppedTotal).flush();
        prefs.putLong(CLICKED, clickedTotal).flush();

        int topLevel = prefs.getInteger(GameOver.TOP_LEVEL, 0);
        if (level > topLevel) {
            prefs.putInteger(GameOver.TOP_LEVEL, level).flush();
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouse);
            if (contRect.contains(mouse.x, mouse.y)) {
//                handler.showAds(false);
                gsm.set(new PlayState(gsm, handler, ++level, lives, difficulty ));

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
        batch.draw(bg, 0, 0, Balloons.WIDTH, Balloons.HEIGHT);
        font.draw(batch, "Level " + level + " completed!", Balloons.WIDTH / 2 - 130, Balloons.HEIGHT / 2 +50);
        font.draw(batch, "Excellent!", Balloons.WIDTH / 2 - 70, Balloons.HEIGHT / 2 + 80);
        batch.draw(contBtn, Balloons.WIDTH / 2 - contBtn.getWidth() / 2, Balloons.HEIGHT / 2 - 250);
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        font.dispose();
    }
}
