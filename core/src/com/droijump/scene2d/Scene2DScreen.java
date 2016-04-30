package com.droijump.scene2d;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.GL20;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.TextureRegion;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.utils.viewport.FitViewport;

        import com.droijump.BaseScreen;
        import com.droijump.MainGame;

public class Scene2DScreen extends BaseScreen {

    private Stage stage;

    private PlayerActor player;

    private SpikeActor spikes;

    private Texture playerTexture, spikeTexture;

    private TextureRegion spikeRegion;

    public Scene2DScreen(MainGame game) {
        super(game);

        playerTexture = new Texture("player.png");
        spikeTexture = new Texture("spike.png");
        spikeRegion = new TextureRegion(spikeTexture, 0, 64, 128, 64);
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(640, 400));

        player = new PlayerActor(playerTexture);
        spikes = new SpikeActor(spikeRegion, 2100, 100, 500);
        player.setPosition(20, 100);

        stage.addActor(player);
        stage.addActor(spikes);
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        checkCollisions();
        stage.draw();
    }

    private void checkCollisions() {
        if (player.isAlive() &&  (player.getX() + player.getWidth()) > spikes.getX()) {
            System.out.println("A collision has happened.");
            player.setAlive(false);
        }
    }

    @Override
    public void dispose() {
        playerTexture.dispose();
        spikeTexture.dispose();
        stage.dispose();
    }
}
