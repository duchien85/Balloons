package com.kara4k.balloons.Sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LifeBal extends Balloon {
    public LifeBal(int frames, float cycleTime) {
        super(frames, cycleTime);
        isBalloon = false;
        ballTexture = new Texture("life_sprite.png");
        animation = new BalloonAnimation(new TextureRegion(ballTexture), frames, cycleTime);





    }

}
