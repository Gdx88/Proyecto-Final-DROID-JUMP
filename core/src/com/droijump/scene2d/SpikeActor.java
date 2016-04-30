package com.droijump.scene2d;
        import com.badlogic.gdx.graphics.g2d.Batch;
        import com.badlogic.gdx.graphics.g2d.TextureRegion;
        import com.badlogic.gdx.scenes.scene2d.Actor;


public class SpikeActor extends Actor {

    private TextureRegion spikes;

    private float speed;

    public SpikeActor(TextureRegion spikes, float x, float y, float speed) {
        this.spikes = spikes;
        this.speed = speed;
        setPosition(x, y);
        setSize(spikes.getRegionWidth(), spikes.getRegionHeight());
    }

    @Override
    public void act(float delta) {
     setX(getX() - speed * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(spikes, getX(), getY());
    }
}
