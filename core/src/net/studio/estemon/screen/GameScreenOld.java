package net.studio.estemon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.studio.estemon.assets.AssetPaths;
import net.studio.estemon.config.DifficultyLevel;
import net.studio.estemon.config.GameConfig;
import net.studio.estemon.entity.Obstacle;
import net.studio.estemon.entity.Player;
import net.studio.estemon.util.GdxUtils;
import net.studio.estemon.util.ViewportUtils;
import net.studio.estemon.util.debug.DebugCameraController;

/**
 * Old code with logic and render screen
 */
@Deprecated
public class GameScreenOld implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;
    private SpriteBatch batch;
    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();
    private DebugCameraController debugCameraController;

    private Player player;
    private Array<Obstacle> obstacles = new Array<Obstacle>();

    private float obstacleTimer;
    private float scoreTimer;

    private int lives = GameConfig.LIVES_START;
    private int score;
    private int displayScore;

    private DifficultyLevel difficultyLevel = DifficultyLevel.HARD;

    @Override
    public void show () {
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

        // create player
        player = new Player();

        // calculate position
        float startPlayerX = GameConfig.WORLD_WIDTH / 2; // set starting x position at center
        float startPlayerY = 1; // set starting y at bottom of the screen

        // position player
        player.setPosition(startPlayerX, startPlayerY);
   }

    @Override
    public void render (float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        // update world
        update(delta);
        GdxUtils.clearScreen();

        // render hud
        renderUi();

        // render debug graphics
        renderDebug();
    }

    private void update(float delta) {
        // not wrapping inside alive because we want to be
        // able to inspect the world and control camera even if the game is over
        if (isGameOver()) {
            return;
        }

        updatePlayer();
        updateObstacles(delta);
        updateScore(delta);
        updateDisplayScore(delta);
        if (isPlayerCollidingWithObstacle()) {
            lives--;
        }
    }

    private boolean isGameOver() {
        return lives <= 0;
    }

    private boolean isPlayerCollidingWithObstacle() {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isNotHit() && obstacle.isPlayerColliding(player)) {
                return true;
            }
        }
        return false;
    }

    private void updatePlayer() {
        //player.update();
        blockPlayerFromLeavingTheWorld();
    }

    private void blockPlayerFromLeavingTheWorld() {
        // 2 alternatives
        float playerX = MathUtils.clamp(player.getX(),
                player.getWidth() / 2,
                GameConfig.WORLD_WIDTH - player.getWidth() / 2
        );

        // next code does same as previous but nicely with MathUtils.clamp
        /*
        if (playerX < player.getWidth() / 2f) {
            playerX = player.getWidth() / 2f;
        }
        else if (playerX > GameConfig.WORLD_WIDTH - player.getWidth() / 2) {
            playerX = GameConfig.WORLD_WIDTH - player.getWidth() / 2;
        }
        */
        player.setPosition(playerX, player.getY());
    }
    private void updateObstacles(float delta) {
        for (Obstacle obstacle : obstacles) {
            obstacle.update();
        }

        createNewObstacle(delta);
    }

    private void createNewObstacle(float delta) {
        obstacleTimer += delta;
        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME) {
            float min = 0f;
            float max = GameConfig.WORLD_WIDTH;
            float obstacleX = MathUtils.random(min, max);
            float obstacleY = GameConfig.WORLD_HEIGHT;

            Obstacle obstacle = new Obstacle();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacle.setPosition(obstacleX, obstacleY);

            obstacles.add(obstacle);
            obstacleTimer = 0f;
        }
    }



    private void updateScore(float delta) {
        scoreTimer += delta;
        if (scoreTimer >= GameConfig.SCORE_MAX_TIME) {
            score += MathUtils.random(1, 5);
            scoreTimer = 0f;
        }
    }

    private void updateDisplayScore(float delta) {
        if (displayScore < score) {
            displayScore = Math.min(score,
                    displayScore + (int) (60 * delta)
            );
        }
    }

    private void renderUi() {
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesText = "LIVES: " + lives;
        layout.setText(font, livesText);
        font.draw(batch,
                livesText,
                20,
                GameConfig.HUD_HEIGHT - layout.height
        );

        String scoreText = "SCORE: " + displayScore;
        layout.setText(font, scoreText);
        font.draw(batch,
                scoreText,
                GameConfig.HUD_WIDTH - layout.width - 20,
                GameConfig.HUD_HEIGHT - layout.height
        );

        batch.end();
    }

    private void renderDebug() {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();
        ViewportUtils.drawGrid(viewport, renderer);

    }

    private void drawDebug() {
        player.drawDebug(renderer);
        for (Obstacle obstacle : obstacles) {
            obstacle.drawDebug(renderer);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose(); // in general, we have to dispose resources when leaving a screen
    }



    @Override
    public void dispose () {
        batch.dispose();
        font.dispose();
        renderer.dispose();
    }

}
