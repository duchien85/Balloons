package com.kara4k.balloons.Sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainBalloon {

    protected Texture ballTexture;
    protected BalloonAnimation animation;

    public MainBalloon() {
        ballTexture = new Texture("main_sheet.png");
        animation = new BalloonAnimation(new TextureRegion(ballTexture), 25, 4f);
    }

    public void update(float dt) {
        animation.update(dt);
    }

    public TextureRegion getBaloon() {
        return animation.getFrame();
    }
}
