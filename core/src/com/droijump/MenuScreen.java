package com.droijump;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.audio.Sound;
        import com.badlogic.gdx.graphics.GL20;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.scenes.scene2d.Actor;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.scenes.scene2d.ui.Image;
        import com.badlogic.gdx.scenes.scene2d.ui.Skin;
        import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
        import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
        import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen extends BaseScreen {

    private Stage stage;

    private Skin skin;

    private Image logo;

    private TextButton play, credits;

    public MenuScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(800, 480));

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        play = new TextButton("PLAY", skin);
        credits = new TextButton("CREDITOS", skin);

            logo = new Image(game.getManager().get("logo.png", Texture.class));

        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Take me to the game screen!
                game.setScreen(game.gameScreen);
            }
        });

        credits.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.creditsScreen);
            }
        });
        logo.setPosition(0, 0);


        play.setSize(200, 50);
        credits.setSize(200, 50);
        play.setPosition(40, 140);
        credits.setPosition(40, 40);
        stage.addActor(logo);
        stage.addActor(play);

        stage.addActor(credits);
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
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
