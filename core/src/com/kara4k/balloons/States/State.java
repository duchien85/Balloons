package com.kara4k.balloons.States;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.kara4k.balloons.AdMobHandler;
import com.kara4k.balloons.Balloons;

public abstract class State {

    protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected GameStateManager gsm;
    protected AdMobHandler handler;

    public State(GameStateManager gsm, AdMobHandler handler) {
        this.gsm = gsm;
        mouse = new Vector3();
        camera = new OrthographicCamera(Balloons.WIDTH/2, Balloons.HEIGHT/2);
        this.handler = handler;
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();



}
