package com.droijump;

        import com.badlogic.gdx.Game;
        import com.badlogic.gdx.assets.AssetManager;
        import com.badlogic.gdx.audio.Music;
        import com.badlogic.gdx.audio.Sound;
        import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {

    private AssetManager manager;

    public BaseScreen loadingScreen, menuScreen, gameScreen, gameOverScreen, creditsScreen;

    @Override
    public void create() {
        //instanciar el manager
        manager = new AssetManager();
        //le pasamos los archivos
        manager.load("floor.png", Texture.class);
        manager.load("gameover.png", Texture.class);
        manager.load("overfloor.png", Texture.class);
        manager.load("logo.png", Texture.class);
        manager.load("spike.png", Texture.class);
        manager.load("player.png", Texture.class);
        //sonidos
        manager.load("audio/die.ogg", Sound.class);
        manager.load("audio/jump.ogg", Sound.class);
        manager.load("audio/song.ogg", Music.class);
        manager.load("audio/go.ogg", Sound.class);
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }

    public void finishLoading() {
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        gameOverScreen = new GameOverScreen(this);
        creditsScreen = new CreditsScreen(this);
        setScreen(menuScreen);
    }

    public AssetManager getManager() {
        return manager;
    }

}
