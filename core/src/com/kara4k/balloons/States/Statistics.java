package com.kara4k.balloons.States;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.kara4k.balloons.AdMobHandler;
import com.kara4k.balloons.Balloons;

public class Statistics extends State implements InputProcessor {

    BitmapFont font;
    Texture bg;
    Texture resetBtn;
    Rectangle resetRect;

    int topLevelEasy;
    long poppedEasy;
    long clicksEasy;

    int topLevelMedium;
    long poppedMedium;
    long clicksMedium;

    int topLevelHard;
    long poppedHard;
    long clicksHard;

    private final Preferences prefsEasy;
    private final Preferences prefsMedium;
    private final Preferences prefsHard;

    public Statistics(GameStateManager gsm,  AdMobHandler handler) {
        super(gsm, handler);

        camera.setToOrtho(false, Balloons.WIDTH, Balloons.HEIGHT);

        font = new BitmapFont(Gdx.files.internal("snap.fnt"));
        font.setColor(Color.BROWN);

        bg = new Texture("statsbg.jpg");
        resetBtn = new Texture("reset.png");

        prefsEasy = Gdx.app.getPreferences(Balloons.EASY);
        prefsMedium = Gdx.app.getPreferences(Balloons.MEDIUM);
        prefsHard = Gdx.app.getPreferences(Balloons.HARD);



        topLevelEasy = prefsEasy.getInteger(GameOver.TOP_LEVEL);
        poppedEasy = prefsEasy.getLong(LevelFinishedState.POPPED, 0);
        clicksEasy = prefsEasy.getLong(LevelFinishedState.CLICKED, 0);

        topLevelMedium = prefsMedium.getInteger(GameOver.TOP_LEVEL);
        poppedMedium = prefsMedium.getLong(LevelFinishedState.POPPED, 0);
        clicksMedium = prefsMedium.getLong(LevelFinishedState.CLICKED, 0);

        topLevelHard = prefsHard.getInteger(GameOver.TOP_LEVEL);
        poppedHard = prefsHard.getLong(LevelFinishedState.POPPED, 0);
        clicksHard = prefsHard.getLong(LevelFinishedState.CLICKED, 0);

        resetRect = new Rectangle();
        resetRect.set( 0, Balloons.HEIGHT/2 - resetBtn.getHeight() - 250, Balloons.WIDTH, resetBtn.getHeight() + 15);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }


    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouse);

            if (resetRect.contains(mouse.x, mouse.y)) {
                prefsEasy.putLong(LevelFinishedState.CLICKED, 0).flush();
                prefsEasy.putLong(LevelFinishedState.POPPED, 0).flush();
                prefsEasy.putInteger(GameOver.TOP_LEVEL, 0).flush();

                prefsMedium.putLong(LevelFinishedState.CLICKED, 0).flush();
                prefsMedium.putLong(LevelFinishedState.POPPED, 0).flush();
                prefsMedium.putInteger(GameOver.TOP_LEVEL, 0).flush();

                prefsHard.putLong(LevelFinishedState.CLICKED, 0).flush();
                prefsHard.putLong(LevelFinishedState.POPPED, 0).flush();
                prefsHard.putInteger(GameOver.TOP_LEVEL, 0).flush();

                gsm.set(new Statistics(gsm, handler));

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
        font.draw(batch, "Easy:" , Balloons.WIDTH/2 - 130, Balloons.HEIGHT - 50);
        font.draw(batch, "Top level   " + topLevelEasy, Balloons.WIDTH/2 - 100, Balloons.HEIGHT - 100);
        font.draw(batch, "Popped   " + poppedEasy,  Balloons.WIDTH/2 - 100, Balloons.HEIGHT - 130);
        font.draw(batch, "Clicks    " + clicksEasy,  Balloons.WIDTH/2 - 100, Balloons.HEIGHT - 160);

        font.draw(batch, "Medium:" , Balloons.WIDTH/2 - 130, Balloons.HEIGHT - 250);
        font.draw(batch, "Top level   " + topLevelMedium, Balloons.WIDTH/2 - 100, Balloons.HEIGHT - 300);
        font.draw(batch, "Popped   " + poppedMedium,  Balloons.WIDTH/2 - 100, Balloons.HEIGHT - 330);
        font.draw(batch, "Clicks    " + clicksMedium,  Balloons.WIDTH/2 - 100, Balloons.HEIGHT - 360);

        font.draw(batch, "Hard:" , Balloons.WIDTH/2 - 130, Balloons.HEIGHT - 450);
        font.draw(batch, "Top level   " + topLevelHard, Balloons.WIDTH/2 - 100, Balloons.HEIGHT - 500);
        font.draw(batch, "Popped   " + poppedHard,  Balloons.WIDTH/2 - 100, Balloons.HEIGHT - 530);
        font.draw(batch, "Clicks    " + clicksHard,  Balloons.WIDTH/2 - 100, Balloons.HEIGHT - 560);

        batch.draw(resetBtn, Balloons.WIDTH/2 - resetBtn.getWidth()/2, Balloons.HEIGHT/2 - resetBtn.getHeight() -250);
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        font.dispose();
        resetBtn.dispose();
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
