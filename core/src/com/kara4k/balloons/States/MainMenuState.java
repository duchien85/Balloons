package com.kara4k.balloons.States;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.kara4k.balloons.AdMobHandler;
import com.kara4k.balloons.Balloons;

public class MainMenuState extends State {

    private Texture background;
    private Texture playBtn;
    private Texture statsBtn;
    private Rectangle playRect;
    private Rectangle statsRect;
    private Texture exitBtn;
    private Rectangle exitRect;
    private Texture helpBtn;
    private Rectangle helpRect;
    private Texture mainBalloon;

    public MainMenuState(GameStateManager gsm, AdMobHandler handler) {
        super(gsm, handler);
        camera.setToOrtho(false, Balloons.WIDTH, Balloons.HEIGHT);

        background = new Texture("menubg.jpg");
        statsBtn = new Texture("records.png");
        exitBtn = new Texture("exit.png");
        helpBtn = new Texture("help.png");
        playBtn = new Texture("start.png");

        mainBalloon = new Texture("mainBall.png");

        playBtnRect();
        helpBtnRect();
        statsBtnRect();
        exitBtnRect();
    }

    private void exitBtnRect() {
        exitRect = new Rectangle();
        exitRect.x = 0;
        exitRect.y = 20;
        exitRect.width = Balloons.WIDTH;
        exitRect.height = exitBtn.getHeight() + 5;
    }

    private void statsBtnRect() {
        statsRect = new Rectangle();
        statsRect.x = 0;
        statsRect.y = 210;
        statsRect.width = Balloons.WIDTH;
        statsRect.height = statsBtn.getHeight() + 5;
    }

    private void helpBtnRect() {
        helpRect = new Rectangle();
        helpRect.x = 0;
        helpRect.y = 105;
        helpRect.width = Balloons.WIDTH;
        helpRect.height = helpBtn.getHeight() + 5;
    }

    private void playBtnRect() {
        playRect = new Rectangle();
        playRect.x = 0;
        playRect.y = 305;
        playRect.width = Balloons.WIDTH;
        playRect.height = playBtn.getHeight() + 5;
    }

    @Override
    protected void handleInput() {

        if (Gdx.input.justTouched()) {
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouse);
            if (playRect.contains(mouse.x, mouse.y)) {
                gsm.set(new DifficultyState(gsm, handler));
            }
            if (statsRect.contains(mouse.x, mouse.y)) {
                gsm.set(new Statistics(gsm, handler));
            }
            if (helpRect.contains(mouse.x, mouse.y)) {
                gsm.set(new HelpState(gsm,handler));
            }
            if (exitRect.contains(mouse.x, mouse.y)) {
                Gdx.app.exit();
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
        batch.draw(background, 0, 0, Balloons.WIDTH, Balloons.HEIGHT);
        batch.draw(playBtn, Balloons.WIDTH / 2 - playBtn.getWidth() / 2, 305);
        batch.draw(statsBtn, Balloons.WIDTH / 2 - statsBtn.getWidth() / 2, 210);
        batch.draw(helpBtn, Balloons.WIDTH / 2 - helpBtn.getWidth() / 2, 105);
        batch.draw(exitBtn, Balloons.WIDTH / 2 - exitBtn.getWidth() / 2, 20);
        batch.draw(mainBalloon, Balloons.WIDTH / 2 - mainBalloon.getWidth() / 2, Balloons.HEIGHT / 2 + 30);
        batch.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        exitBtn.dispose();
        statsBtn.dispose();
        mainBalloon.dispose();
        helpBtn.dispose();


    }
}
