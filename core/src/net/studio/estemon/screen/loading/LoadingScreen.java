package net.studio.estemon.screen.loading;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.studio.estemon.ObstacleAvoiderGame;
import net.studio.estemon.assets.AssetDescriptors;
import net.studio.estemon.config.GameConfig;
import net.studio.estemon.screen.game.GameScreen;
import net.studio.estemon.screen.menu.MenuScreen;
import net.studio.estemon.util.GdxUtils;

public class LoadingScreen extends ScreenAdapter {

    // constants
    private static final float PROGRESS_BAR_WIDTH = GameConfig.WIDTH / 2f; // world units
    private static final float PROGRESS_BAR_HEIGHT = 60f; // world units

    // attributes
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.25f;
    private boolean changeScreen;

    private final ObstacleAvoiderGame game;
    private final AssetManager assetManager;

    // constructors
    public LoadingScreen(ObstacleAvoiderGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }


    // public methods
    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.GAMEPLAY_ATLAS);
        assetManager.load(AssetDescriptors.UI_ATLAS);
        assetManager.load(AssetDescriptors.UI_SKIN);
    }

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();
        viewport.apply(); // don't forget to apply before setting projection matrix
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        // only when end drawing and rendering check if we can start game
        if (changeScreen) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }



    @Override
    public void hide() {
        // IMPORTANT: screens don't dispose automatically
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    // private methods
    private void waitMillis(long millis) {
        try {
            Thread.sleep(millis);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(float delta) {
        //waitMillis(400); // debug purposes

        // progress is between 0 and 1
        progress = assetManager.getProgress();

        // update returns true when all assets are loaded in memory
        if (assetManager.update()) {
            waitTime -= delta;

            if (waitTime <= 0) {
                changeScreen = true; // use this flag to avoid changing screen issues
            }
        }
    }

    private void draw() {
        float progressBarX = (GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f;
        float progressBarY = (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f;

        renderer.rect(progressBarX, progressBarY,
                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
    }
}
