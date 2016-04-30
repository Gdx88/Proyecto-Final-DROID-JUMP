package com.droijump.entities;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Batch;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.physics.box2d.Body;
        import com.badlogic.gdx.physics.box2d.BodyDef;
        import com.badlogic.gdx.physics.box2d.Fixture;
        import com.badlogic.gdx.physics.box2d.PolygonShape;
        import com.badlogic.gdx.physics.box2d.World;
        import com.badlogic.gdx.scenes.scene2d.Actor;

        import com.droijump.Constants;

public class PlayerEntity extends Actor {
    //dibujar
    private Texture texture;
    //su mundo
    private World world;

    private Body body;
    //su fixture
    private Fixture fixture;
    //cuando esta vivo
    private boolean alive = true;
    //cuando esta saltando
    private boolean jumping = false;

    private boolean mustJump = false;

    public PlayerEntity(World world, Texture texture, Vector2 position) {
        //guardar todo
        this.world = world;
        this.texture = texture;

        BodyDef def = new BodyDef();
        //posicion
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        //crear body
        body = world.createBody(def);

        //fixture
        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
        fixture = body.createFixture(box, 3);
        fixture.setUserData("player");
        box.dispose();
        //conversion
        setSize(Constants.PIXELS_IN_METER, Constants.PIXELS_IN_METER);
    }

    @Override//dibujar por pantalla
    public void draw(Batch batch, float parentAlpha) {
        //posicion para dibujar//Box2D
        setPosition((body.getPosition().x - 0.5f) * Constants.PIXELS_IN_METER,
                    (body.getPosition().y - 0.5f) * Constants.PIXELS_IN_METER);
        //dibujar
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        //salto
        if (Gdx.input.justTouched()) {
            jump();
        }

        if (mustJump) {
            mustJump = false;
            jump();
        }

        if (alive) {
            float speedY = body.getLinearVelocity().y;
            body.setLinearVelocity(Constants.PLAYER_SPEED, speedY);
        }

        if (jumping) {
            body.applyForceToCenter(0, -Constants.IMPULSE_JUMP * 1.15f, true);
        }
    }

    public void jump() {

        //para que deje de saltar cuando te chocas
        if (!jumping && alive) {
            jumping = true;
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, Constants.IMPULSE_JUMP, position.x, position.y, true);
        }
    }
    //metodo para eliminar el fixture y el body
    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setMustJump(boolean mustJump) {
        this.mustJump = mustJump;
    }
}
