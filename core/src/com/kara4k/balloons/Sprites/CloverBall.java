package com.kara4k.balloons.Sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CloverBall extends Balloon {
    public CloverBall(int frames, float cycleTime) {
        super(frames, cycleTime);
        isBalloon = false;
        ballTexture = new Texture("clover_sheet.png");
        animation = new BalloonAnimation(new TextureRegion(ballTexture), frames, cycleTime);

    }
}
