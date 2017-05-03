package com.kara4k.balloons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kara4k.balloons.States.GameStateManager;
import com.kara4k.balloons.States.MainMenuState;

public class Balloons extends ApplicationAdapter {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;
    public static final String TITLE = "Balloons";

    public static final String EASY = "easy";
    public static final String MEDIUM = "medium";
    public static final String HARD = "hard";

    SpriteBatch batch;
    GameStateManager gsm;
    AdMobHandler handler;

    public Balloons(AdMobHandler handler) {
        this.handler = handler;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gsm.push(new MainMenuState(gsm, handler));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }


    @Override
    public void dispose() {
        batch.dispose();
    }


}
