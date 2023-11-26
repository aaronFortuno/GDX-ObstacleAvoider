package net.studio.estemon.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.studio.estemon.ObstacleAvoiderGame;
import net.studio.estemon.assets.AssetPaths;
import net.studio.estemon.config.GameConfig;
import net.studio.estemon.util.GdxUtils;

public abstract class MenuScreenBase extends ScreenAdapter {

    protected final ObstacleAvoiderGame game;
    protected final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;
    protected Skin skin;

    public MenuScreenBase(ObstacleAvoiderGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());
        skin = assetManager.get(AssetPaths.UI_SKIN);

        Gdx.input.setInputProcessor(stage);

        stage.addActor(createUi());
    }

    protected abstract Actor createUi();

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
