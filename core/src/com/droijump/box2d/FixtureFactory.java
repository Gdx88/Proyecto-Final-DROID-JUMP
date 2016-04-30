package com.droijump.box2d;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.physics.box2d.Body;
        import com.badlogic.gdx.physics.box2d.Fixture;
        import com.badlogic.gdx.physics.box2d.PolygonShape;


public class FixtureFactory {
    public static Fixture createPlayerFixture(Body playerBody) {
        PolygonShape minijoeShape = new PolygonShape();
        minijoeShape.setAsBox(0.5f, 0.5f);
        Fixture fixture = playerBody.createFixture(minijoeShape, 3);
        minijoeShape.dispose();
        return fixture;
    }

    public static Fixture createFloorFixture(Body floorBody) {
        PolygonShape box = new PolygonShape();
        box.setAsBox(500, 1);
        Fixture fixture = floorBody.createFixture(box, 1);
        box.dispose();
        return fixture;
    }

    public static Fixture createSpikeFixture(Body pinchoBody) {
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.5f, -0.5f);
        vertices[1] = new Vector2(0.5f, -0.5f);
        vertices[2] = new Vector2(0, 0.5f);

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        Fixture fixture = pinchoBody.createFixture(shape, 1);
        shape.dispose();
        return fixture;
    }
}
