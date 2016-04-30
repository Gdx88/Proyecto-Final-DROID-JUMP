package com.droijump.box2d;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class BodyDefFactory {

    public static BodyDef createPlayer() {
        BodyDef def = new BodyDef();
        def.position.set(0, 0.5f);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

    public static BodyDef createSpikes(float x) {

        BodyDef def = new BodyDef();
        def.position.set(x, 0.5f);
        return def;
    }

    public static BodyDef createFloor() {
        BodyDef def = new BodyDef();
        def.position.set(0, -1);
        return def;
    }
}
