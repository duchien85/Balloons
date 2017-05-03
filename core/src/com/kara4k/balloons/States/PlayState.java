package com.kara4k.balloons.States;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.kara4k.balloons.AdMobHandler;
import com.kara4k.balloons.Balloons;
import com.kara4k.balloons.Sprites.Balloon;
import com.kara4k.balloons.Sprites.ClockBall;
import com.kara4k.balloons.Sprites.CloverBall;
import com.kara4k.balloons.Sprites.DynamiteBall;
import com.kara4k.balloons.Sprites.HoeBall;
import com.kara4k.balloons.Sprites.LifeBal;
import com.kara4k.balloons.Sprites.SnowBall;

import java.util.Random;

public class PlayState extends State implements InputProcessor {


    public static final float EASY = 1;
    public static final float MEDIUM = 2;
    public static final float HARD = 3;

    public static final int ROUND_COUNT = 15;
    public static final float ANIM_SPEED = 4f;
    public static final int SPAWN_SPEED = 500;

    public static final int RARE_SPAWN_CHANCE = 5;

    private float difficulty;
    private float rareSpawnChance;

    int level;
    float animSpeed;
    long spawnSpeed;
    int lives;

    BitmapFont font;
    Texture bg;
    Texture lifeT;
    Texture pauseT;
    Texture tap;
    Texture toMenu;
    Sound popSound;
    Sound goneSound;
    Sound dynamiteSound;
    Sound freezeSound;
    Sound clockSound;
    Sound cloverSound;
    Sound healthSound;
    Sound hoeSound;

    Array<Balloon> balloons;
    Vector3 touchPos;
    long lastSpawn;
    Random random;

    int popped;
    int lost;
    int totalRound;

    int clicks;

    boolean pause;
    boolean freeze;

    public PlayState(GameStateManager gsm, AdMobHandler handler, int lvl, int lives, float diff) {
        super(gsm, handler);

        difficulty = diff;
        rareSpawnChance = 20 - 5 * difficulty;
        animSpeed = ANIM_SPEED - difficulty;
        spawnSpeed = SPAWN_SPEED - 25 * (int) difficulty * (lvl / 5);

        pause = false;


        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        clicks = 0;
        this.lives = lives;
        level = lvl;


        popped = 0;
        lost = 0;
        totalRound = 0;
        font = new BitmapFont(Gdx.files.internal("snap.fnt"));
        font.setColor(Color.BROWN);

        bg = new Texture("playbg.jpg");
        lifeT = new Texture("health_bar.png");
        pauseT = new Texture("paused.png");
        tap = new Texture("tap.png");
        toMenu = new Texture("tomenu.png");

        popSound = Gdx.audio.newSound(Gdx.files.internal("pop.wav"));
        goneSound = Gdx.audio.newSound(Gdx.files.internal("gone.wav"));
        dynamiteSound = Gdx.audio.newSound(Gdx.files.internal("explode.wav"));
        freezeSound = Gdx.audio.newSound(Gdx.files.internal("freeze.wav"));
        clockSound = Gdx.audio.newSound(Gdx.files.internal("clock.wav"));
        cloverSound = Gdx.audio.newSound(Gdx.files.internal("clover.wav"));
        healthSound = Gdx.audio.newSound(Gdx.files.internal("health.wav"));
        hoeSound = Gdx.audio.newSound(Gdx.files.internal("hoe.wav"));

        random = new Random();
        balloons = new Array<Balloon>();


        camera.setToOrtho(false, Balloons.WIDTH, Balloons.HEIGHT);
    }

    private void spawnBall(int frames, float speed) {
        Balloon balloon = getBalloonType(frames, speed);
        balloons.add(balloon);
        lastSpawn = TimeUtils.millis();
        if (balloon.isBalloon()) {
            totalRound++;
        }
    }

    private Balloon getBalloonType(int frames, float speed) {
        Balloon balloon;
        int i = random.nextInt(99);
        if (i < rareSpawnChance) {
            balloon = getSpecificBalloon(frames, speed);
        } else {
            balloon = new Balloon(frames, speed);
        }
        return balloon;
    }

    private Balloon getSpecificBalloon(int frames, float speed) {
        Balloon balloon;
        int i = random.nextInt(6);
        System.out.println(i);
        switch (i) {
            case 0:
                balloon = new LifeBal(frames, speed);
                break;
            case 1:
                balloon = new ClockBall(frames, speed);
                break;
            case 2:
                balloon = new DynamiteBall(frames, speed);
                break;
            case 3:
                balloon = new SnowBall(frames, speed);
                break;
            case 4:
                balloon = new HoeBall(frames, speed);
                break;
            case 5:
                balloon = new CloverBall(frames, speed);
                break;
            default:
                balloon = new CloverBall(frames, speed);
                break;
        }
        return balloon;
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            clicks++;
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouse);
            for (int i = 0; i < balloons.size; i++) {
                if (balloons.get(i).getBounds().contains(mouse.x, mouse.y)) {
                    if (balloons.get(i) instanceof LifeBal) {
                        lives++;
                        healthSound.play();
                        balloons.removeIndex(i);
                    } else if (balloons.get(i) instanceof ClockBall) {
                        if (animSpeed == 5f) {
                            animSpeed = 0.5f;
                        } else {
                            animSpeed = 5f;
                        }
                        clockSound.play();
                        balloons.removeIndex(i);
                    } else if (balloons.get(i) instanceof DynamiteBall) {
                        dynamiteSound.play();
                        int balls = 0;
                        for (int j = 0; j < balloons.size; j++) {
                            if (balloons.get(j).isBalloon()) balls++;
                        }
                        popped += balls;
                        balloons.clear();
                    } else if (balloons.get(i) instanceof SnowBall) {
                        freeze = true;
                        freezeSound.play();
                        balloons.removeIndex(i);
                    } else if (balloons.get(i) instanceof HoeBall) {
                        hoeSound.play();
                        balloons.removeIndex(i);
                        popped = ROUND_COUNT - lost;
                        totalRound = ROUND_COUNT;
                        balloons.clear();
                    } else if (balloons.get(i) instanceof CloverBall) {
                        level++;
                        cloverSound.play();
                        balloons.removeIndex(i);
                    } else {
                        popSound.play();
                        popped++;
                        balloons.removeIndex(i);
                    }

                }
            }
        }
    }

    @Override
    public void update(float dt) {
        if (pause) {
            if (Gdx.input.justTouched()) pause = false;
        }

        if (!pause) {
            handleInput();
            if (!freeze) {
                for (int i = 0; i < balloons.size; i++) {
                    balloons.get(i).update(dt);
                }

                for (int i = 0; i < balloons.size; i++) {
                    if (!balloons.get(i).isExist()) {
                        if (balloons.size != 0 && balloons.get(i).isBalloon()) {
                            goneSound.play();
                            --lives;
                            lost++;
                        }
                        balloons.removeIndex(i);
                    }
                }

                if (lives <= 0) {
                    gsm.set(new GameOver(gsm, handler, level, difficulty));
                } else {

                    if (totalRound < ROUND_COUNT) {
                        if (TimeUtils.millis() > lastSpawn + spawnSpeed) spawnBall(25, animSpeed);
                    } else {
                        if (((lost + popped >= ROUND_COUNT) && (TimeUtils.millis() > lastSpawn + 3000))) {
                            gsm.set(new LevelFinishedState(gsm, handler, level, lives, popped, clicks, difficulty));
                        }
                    }
                }
            } else {
                if (balloons.size == 0) freeze = false;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bg, 0, 0, Balloons.WIDTH, Balloons.HEIGHT);

        drawLifesBar(batch);
        font.draw(batch, "Level " + level, 5, Balloons.HEIGHT - 10);

        if (balloons.size != 0) {
            for (int i = 0; i < balloons.size; i++) {
                batch.draw(balloons.get(i).getBaloon(), balloons.get(i).getPosition().x, balloons.get(i).getPosition().y);
            }
        }


        if (pause) {
            drawPause(batch);
        }

        batch.end();
    }

    private void drawPause(SpriteBatch batch) {
        batch.draw(pauseT, Balloons.WIDTH / 2 - pauseT.getWidth() / 2, Balloons.HEIGHT / 2 - pauseT.getHeight() / 2);
        batch.draw(tap, Balloons.WIDTH / 2 - tap.getWidth() / 2, Balloons.HEIGHT / 2 - tap.getHeight() / 2 - 60);
        batch.draw(toMenu, Balloons.WIDTH / 2 - toMenu.getWidth() / 2, Balloons.HEIGHT / 2 - toMenu.getHeight() / 2 - 100);
    }

    private void drawLifesBar(SpriteBatch batch) {
        int lifesCount = getDrawLifesCount();
        for (int i = 1; i <= lifesCount; i++) {
            batch.draw(lifeT, Balloons.WIDTH - lifeT.getWidth() * i, Balloons.HEIGHT - lifeT.getHeight() - 5);
        }
    }

    private int getDrawLifesCount() {
        int lifesCount;
        if (lives > 10) {
            lifesCount = 10;
        } else {
            lifesCount = lives;
        }
        return lifesCount;
    }

    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.BACK) || (keycode == Input.Keys.ESCAPE)) {
            if (!pause) {
                pause = true;
            } else {
                gsm.set(new MainMenuState(gsm, handler));
            }
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

    @Override
    public void dispose() {
        for (int i = 0; i < balloons.size; i++) {
            balloons.get(i).dispose();
        }
        bg.dispose();
        lifeT.dispose();
        popSound.dispose();
        goneSound.dispose();
        dynamiteSound.dispose();
        freezeSound.dispose();
        clockSound.dispose();
        cloverSound.dispose();
        healthSound.dispose();
        hoeSound.dispose();
        pauseT.dispose();
        toMenu.dispose();
        tap.dispose();

    }
}
