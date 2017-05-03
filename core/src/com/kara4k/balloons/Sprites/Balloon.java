package com.kara4k.balloons.Sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;
import com.kara4k.balloons.Balloons;

import java.util.Random;

public class Balloon {


    private Random random;
    protected Texture ballTexture;
    private Vector3 position;
    protected BalloonAnimation animation;
    protected Circle bounds;
    private int x;
    private int y;
    protected boolean isBalloon;

    public Balloon(int frames, float cycleTime) {
        isBalloon = true;
        random = new Random();
        ballTexture = new Texture("red_sprite.png");
        animation = new BalloonAnimation(new TextureRegion(ballTexture), frames, cycleTime);
        position = new Vector3();


        x = random.nextInt(Balloons.WIDTH - ballTexture.getWidth() / frames);
        y = random.nextInt(Balloons.HEIGHT - ballTexture.getHeight());

        position.set(x, y, 0);

        bounds = new Circle(x + ballTexture.getWidth() / frames / 2, y + ballTexture.getHeight() / 2, 30);

    }

    public Vector3 getPosition() {
        return position;
    }

    public Circle getBounds() {
        return bounds;
    }

    public void update(float dt) {
        animation.update(dt);
    }

    public TextureRegion getBaloon() {
        return animation.getFrame();
    }

    public boolean isExist() {
        return animation.isExist();
    }



    public void dispose() {
        ballTexture.dispose();
    }

    public boolean isBalloon() {
        return isBalloon;
    }


}
