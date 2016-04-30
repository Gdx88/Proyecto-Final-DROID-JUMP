package com.droijump.box2d;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.GL20;
        import com.badlogic.gdx.graphics.OrthographicCamera;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.physics.box2d.Body;
        import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
        import com.badlogic.gdx.physics.box2d.Contact;
        import com.badlogic.gdx.physics.box2d.ContactImpulse;
        import com.badlogic.gdx.physics.box2d.ContactListener;
        import com.badlogic.gdx.physics.box2d.Fixture;
        import com.badlogic.gdx.physics.box2d.Manifold;
        import com.badlogic.gdx.physics.box2d.World;

        import com.droijump.BaseScreen;
        import com.droijump.MainGame;

public class Box2DScreen extends BaseScreen {

    public Box2DScreen(MainGame game) {
        super(game);
    }

    private World world;
    private Box2DDebugRenderer renderer;

    private OrthographicCamera camera;

    private Body minijoeBody, floorBody, spikeBody;

    private Fixture minijoeFixture, floorFixture, spikeFixture;

    private boolean mustJump, isJumping, isAlive = true;

    @Override
    public void show() {

        world = new World(new Vector2(0, -10), true);

        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(16, 9);
        camera.translate(0, 1);

        minijoeBody = world.createBody(BodyDefFactory.createPlayer());
        floorBody = world.createBody(BodyDefFactory.createFloor());
        spikeBody = world.createBody(BodyDefFactory.createSpikes(6f));

        minijoeFixture = FixtureFactory.createPlayerFixture(minijoeBody);
        floorFixture = FixtureFactory.createFloorFixture(floorBody);
        spikeFixture = FixtureFactory.createSpikeFixture(spikeBody);

        minijoeFixture.setUserData("player");
        floorFixture.setUserData("floor");
        spikeFixture.setUserData("spike");

        world.setContactListener(new Box2DScreenContactListener());
    }

    @Override
    public void dispose() {

        floorBody.destroyFixture(floorFixture);
        minijoeBody.destroyFixture(minijoeFixture);
        spikeBody.destroyFixture(spikeFixture);

        world.destroyBody(minijoeBody);
        world.destroyBody(floorBody);
        world.destroyBody(spikeBody);

        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched() && !isJumping) {
            mustJump = true;
        }

        if (mustJump) {
            mustJump = false;
            makePlayerJump();
        }

        if (isAlive) {
            float velocidadY = minijoeBody.getLinearVelocity().y;
            minijoeBody.setLinearVelocity(8, velocidadY);
        }


        world.step(delta, 6, 2);

        camera.update();
        renderer.render(world, camera.combined);
    }

    private void makePlayerJump() {
        Vector2 position = minijoeBody.getPosition();
        minijoeBody.applyLinearImpulse(0, 20, position.x, position.y, true);
    }

    private class Box2DScreenContactListener implements ContactListener {
        @Override
        public void beginContact(Contact contact) {

            Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();

            if (fixtureA.getUserData() == null || fixtureB.getUserData() == null) {
                return;
            }
            if ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("floor")) ||
                    (fixtureA.getUserData().equals("floor") && fixtureB.getUserData().equals("player"))) {
                // So player and floor have collided.

                if (Gdx.input.isTouched()) {
                    // If the screen is still touched, jump again.
                    mustJump = true;
                }

                // Stop jumping anyway.
                isJumping = false;
            }

            if ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("spike")) ||
                    (fixtureA.getUserData().equals("spike") && fixtureB.getUserData().equals("player"))) {
                isAlive = false;
            }
        }

        @Override
        public void endContact(Contact contact) {
            Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();

            if (fixtureA == minijoeFixture && fixtureB == floorFixture) {
                isJumping = true;
            }

            if (fixtureA == floorFixture && fixtureB == minijoeFixture) {
                isJumping = true;
            }
        }
        @Override public void preSolve(Contact contact, Manifold oldManifold) { }
        @Override public void postSolve(Contact contact, ContactImpulse impulse) { }
    }
}
