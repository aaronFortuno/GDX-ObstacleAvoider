package net.studio.estemon.screen.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

import net.studio.estemon.ObstacleAvoiderGame;
import net.studio.estemon.assets.AssetDescriptors;

public class GameScreen implements Screen {

    private final ObstacleAvoiderGame game;
    private final AssetManager assetManager;
    private GameController controller;
    private GameRenderer renderer;

    public GameScreen(ObstacleAvoiderGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        controller = new GameController();
        renderer = new GameRenderer(assetManager, controller);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
