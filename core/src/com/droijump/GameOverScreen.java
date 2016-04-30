package com.droijump;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.GL20;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.scenes.scene2d.Actor;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.scenes.scene2d.ui.Image;
        import com.badlogic.gdx.scenes.scene2d.ui.Skin;
        import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
        import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
        import com.badlogic.gdx.utils.viewport.FitViewport;
        import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameOverScreen extends BaseScreen {

    private Stage stage;
    //estilo
    private Skin skin;
    //imagen
    private Image gameover;
    //imagen con text boton
    private TextButton retry, menu;

    public GameOverScreen(final MainGame game) {
        super(game);

        stage = new Stage(new StretchViewport(800,480));
        //skin archivo
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        retry = new TextButton("Retry", skin);
        menu = new TextButton("Menu", skin);
        //crear la imagen game over
        gameover = new Image(game.getManager().get("gameover.png", Texture.class));

        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });

        menu.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);
            }
        });
        //posicion de la imagen game over
        gameover.setPosition(0,0);
        //tamanio de los botones

        retry.setSize(200, 60);
        menu.setSize(200,60);
        //posicion de los botones
        retry.setPosition(150, 50);
        menu.setPosition(450, 50);

        stage.addActor(gameover);
        stage.addActor(retry);

        stage.addActor(menu);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

        skin.dispose();
        stage.dispose();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
