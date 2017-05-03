package com.kara4k.balloons.Sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SnowBall extends Balloon {
    public SnowBall(int frames, float cycleTime) {
        super(frames, cycleTime);
        isBalloon = false;
        ballTexture = new Texture("snow_sheet.png");
        animation = new BalloonAnimation(new TextureRegion(ballTexture), frames, cycleTime);
    }
}
