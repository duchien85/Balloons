package com.kara4k.balloons.Sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HoeBall extends Balloon {
    public HoeBall(int frames, float cycleTime) {
        super(frames, cycleTime);
        isBalloon = false;
        ballTexture = new Texture("hoe_green_sheet.png");
        animation = new BalloonAnimation(new TextureRegion(ballTexture), frames, cycleTime);

    }
}
