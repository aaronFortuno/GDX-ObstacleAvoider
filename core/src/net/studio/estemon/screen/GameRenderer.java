package net.studio.estemon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.studio.estemon.assets.AssetPaths;
import net.studio.estemon.config.GameConfig;
import net.studio.estemon.entity.Background;
import net.studio.estemon.entity.Obstacle;
import net.studio.estemon.entity.Player;
import net.studio.estemon.util.GdxUtils;
import net.studio.estemon.util.ViewportUtils;
import net.studio.estemon.util.debug.DebugCameraController;

public class GameRenderer implements Disposable {

    // attributes
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;
    private SpriteBatch batch;
    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();
    private DebugCameraController debugCameraController;
    private final GameController controller;

    private Texture backgroundTexture;
    private Texture playerTexture;
    private Texture obstacleTexture;


    // constructor
    public GameRenderer(GameController controller) {
        this.controller = controller;
        init();
    }

    public void init() {
        // game view
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        // hud view
        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal(AssetPaths.UI_FONT));

        // create debug camera controller
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        backgroundTexture = new Texture(Gdx.files.internal("gameplay/background.png"));
        playerTexture = new Texture(Gdx.files.internal("gameplay/ship_a.png"));
        obstacleTexture = new Texture(Gdx.files.internal("gameplay/obstacle_large.png"));
    }

    // public methods
    public void render(float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        GdxUtils.clearScreen();

        // render gameplay (before ui)
        renderGamePlay();

        // render hud
        renderUi();

        // render debug graphics
        renderDebug();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        renderer.dispose();

        backgroundTexture.dispose();
        playerTexture.dispose();
        obstacleTexture.dispose();
    }

    /*
    Whenever we use more than one viewport we need to call .apply() method of the viewport
    before any drawing. Since we are using different screen sizes for each viewport we have
    to apply viewport to avoid layout problems, buttons don't working, etc.

    Is similar as we have to tell viewport that we resized our screen or tell the
    projectionMatrix which camera are we using.
    */

    // private methods
    private void renderGamePlay() {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // draw background
        Background background = controller.getBackground();
        batch.draw(backgroundTexture,
                background.getX(), background.getY(),
                background.getWidth(), background.getHeight());

        // draw player
        Player player = controller.getPlayer();
        batch.draw(playerTexture,
                player.getX(), player.getY(),
                player.getWidth(), player.getHeight()
        );

        // draw obstacle
        for (Obstacle obstacle : controller.getObstacles()) {
            batch.draw(obstacleTexture,
                    obstacle.getX(), obstacle.getY(),
                    obstacle.getWidth(), obstacle.getHeight());
        }

        batch.end();
    }

    private void renderUi() {
        hudViewport.apply();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesText = "LIVES: " + controller.getLives();
        layout.setText(font, livesText);
        font.draw(batch,
                livesText,
                20,
                GameConfig.HUD_HEIGHT - layout.height
        );

        String scoreText = "SCORE: " + controller.getDisplayScore();
        layout.setText(font, scoreText);
        font.draw(batch,
                scoreText,
                GameConfig.HUD_WIDTH - layout.width - 20,
                GameConfig.HUD_HEIGHT - layout.height
        );

        batch.end();
    }

    private void renderDebug() {
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();
        ViewportUtils.drawGrid(viewport, renderer);

    }

    private void drawDebug() {
        controller.getPlayer().drawDebug(renderer);
        for (Obstacle obstacle : controller.getObstacles()) {
            obstacle.drawDebug(renderer);
        }
    }
}
