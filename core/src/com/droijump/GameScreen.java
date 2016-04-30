package com.droijump;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.audio.Music;
        import com.badlogic.gdx.audio.Sound;
        import com.badlogic.gdx.graphics.GL20;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.math.Vector3;
        import com.badlogic.gdx.physics.box2d.Contact;
        import com.badlogic.gdx.physics.box2d.ContactImpulse;
        import com.badlogic.gdx.physics.box2d.ContactListener;
        import com.badlogic.gdx.physics.box2d.Manifold;
        import com.badlogic.gdx.physics.box2d.World;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.scenes.scene2d.actions.Actions;
        import com.badlogic.gdx.utils.viewport.FitViewport;

        import com.droijump.entities.FloorEntity;
        import com.droijump.entities.EntityFactory;
        import com.droijump.entities.PlayerEntity;
        import com.droijump.entities.SpikeEntity;

        import java.util.ArrayList;
        import java.util.List;


public class GameScreen extends BaseScreen {

    //para la parte de scene 2D
    private Stage stage;
    //para la parte de box2D
    private World world;

    private PlayerEntity player;
    //representar suelos
    private List<FloorEntity> floorList = new ArrayList<FloorEntity>();
    //representar pinchos
    private List<SpikeEntity> spikeList = new ArrayList<SpikeEntity>();

    private Sound jumpSound;

    private Sound dieSound;
    private Sound goSound;

    private Music backgroundMusic;

    private Vector3 position;

    public GameScreen(MainGame game) {
        super(game);
        //defino mi stage

        stage = new Stage(new FitViewport(800, 480));
        position = new Vector3(stage.getCamera().position);
        //defino mi mundo
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new GameContactListener());
        goSound = game.getManager().get("audio/go.ogg");
        jumpSound = game.getManager().get("audio/jump.ogg");
        dieSound = game.getManager().get("audio/die.ogg");
        backgroundMusic = game.getManager().get("audio/song.ogg");
    }

    @Override
    public void show() {
        EntityFactory factory = new EntityFactory(game.getManager());

        player = factory.createPlayer(world, new Vector2(1.5f, 1.5f));
    //principal
        floorList.add(factory.createFloor(world, 0, 1000, 1));
        //pisos en el aire
        floorList.add(factory.createFloor(world, 15, 10, 2));
        floorList.add(factory.createFloor(world, 30, 8, 2));
    //pinchos
        spikeList.add(factory.createSpikes(world, 8, 1));
        spikeList.add(factory.createSpikes(world, 23, 2));
        spikeList.add(factory.createSpikes(world, 35, 2));
        spikeList.add(factory.createSpikes(world, 50, 1));
        //pisos en el aire
        floorList.add(factory.createFloor(world, 65, 10, 2));
        floorList.add(factory.createFloor(world, 80, 8, 2));
        //pinchos
        spikeList.add(factory.createSpikes(world, 58, 1));
        spikeList.add(factory.createSpikes(world, 73, 2));
        spikeList.add(factory.createSpikes(world, 85, 2));
        spikeList.add(factory.createSpikes(world, 100, 1));
        //pisos en el aire
        floorList.add(factory.createFloor(world, 115, 10, 2));
        floorList.add(factory.createFloor(world, 130, 8, 2));
        //pinchos
        spikeList.add(factory.createSpikes(world, 108, 1));
        spikeList.add(factory.createSpikes(world, 123, 2));
        spikeList.add(factory.createSpikes(world, 135, 2));
        spikeList.add(factory.createSpikes(world, 150, 1));
        //pisos en el aire
        floorList.add(factory.createFloor(world, 165, 10, 2));
        floorList.add(factory.createFloor(world, 180, 8, 2));
        //pinchos
        spikeList.add(factory.createSpikes(world, 158, 1));
        spikeList.add(factory.createSpikes(world, 173, 2));
        spikeList.add(factory.createSpikes(world, 185, 2));
        spikeList.add(factory.createSpikes(world, 200, 1));
        for (FloorEntity floor : floorList)
            stage.addActor(floor);
        for (SpikeEntity spike : spikeList)
            stage.addActor(spike);
        //agregar al stage
        stage.addActor(player);

        stage.getCamera().position.set(position);
        stage.getCamera().update();
    //musica de fondo
        backgroundMusic.setVolume(0.75f);
        backgroundMusic.play();
    }

    @Override
    public void hide() {

        stage.clear();

        player.detach();
        for (FloorEntity floor : floorList)
            floor.detach();
        for (SpikeEntity spike : spikeList)
            spike.detach();

        floorList.clear();
        spikeList.clear();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //introducir las fuerzas
        stage.act();
        //aplicar y actualizar al mundo
        world.step(delta, 6, 2);
        //para que la camara siga al jugador
        if (player.getX() > 150 && player.isAlive()) {
            float speed = Constants.PLAYER_SPEED * delta * Constants.PIXELS_IN_METER;
            stage.getCamera().translate(speed, 0, 0);
        }
        //para dibujar
        stage.draw();
    }
//para eliminar memoria XD
    @Override
    public void dispose() {
        stage.dispose();

        world.dispose();
    }

    private class GameContactListener implements ContactListener {
        //colisiones
        private boolean areCollided(Contact contact, Object userA, Object userB) {
            Object userDataA = contact.getFixtureA().getUserData();
            Object userDataB = contact.getFixtureB().getUserData();

            if (userDataA == null || userDataB == null) {
                return false;
            }

            return (userDataA.equals(userA) && userDataB.equals(userB)) ||
                    (userDataA.equals(userB) && userDataB.equals(userA));
        }

        @Override
        public void beginContact(Contact contact) {

            if (areCollided(contact, "player", "floor")) {
                player.setJumping(false);
                //si el dedo sigue en la pantalla
                if (Gdx.input.isTouched()) {
                    //cuando tocas la pantalla el sonido play se reproduce
                    jumpSound.play();

                    player.setMustJump(true);
                }
            }

            if (areCollided(contact, "player", "spike")) {

                if (player.isAlive()) {
                    player.setAlive(false);

                    backgroundMusic.stop();
                    dieSound.play();
                    goSound.play();
                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(1.5f),
                                    Actions.run(new Runnable() {

                                        @Override
                                        public void run() {
                                            game.setScreen(game.gameOverScreen);
                                        }
                                    })
                            )
                    );
                }
            }
        }

        @Override
        public void endContact(Contact contact) {
            if (areCollided(contact, "player", "floor")) {
                if (player.isAlive()) {
                    jumpSound.play();
                }
            }
        }

        @Override public void preSolve(Contact contact, Manifold oldManifold) { }
        @Override public void postSolve(Contact contact, ContactImpulse impulse) { }
    }
}

