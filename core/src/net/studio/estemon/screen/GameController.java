package net.studio.estemon.screen;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import net.studio.estemon.config.DifficultyLevel;
import net.studio.estemon.config.GameConfig;
import net.studio.estemon.entity.Background;
import net.studio.estemon.entity.Obstacle;
import net.studio.estemon.entity.Player;

public class GameController {

    // constants

    // attributes
    private Player player;
    private Array<Obstacle> obstacles = new Array<Obstacle>();
    private Background background;

    private float obstacleTimer;
    private float scoreTimer;

    private int lives = GameConfig.LIVES_START;
    private int score;
    private int displayScore;

    private DifficultyLevel difficultyLevel = DifficultyLevel.EASY;
    private Pool<Obstacle> obstaclePool;


    // constructor
    public GameController() {
        init();
    }

    private void init() {
        // create player
        player = new Player();

        // calculate position
        float halfPlayerSize = GameConfig.PLAYER_SIZE / 2;
        float startPlayerX = GameConfig.WORLD_WIDTH / 2 - halfPlayerSize; // set starting x position at center
        float startPlayerY = 1 - halfPlayerSize; // set starting y at bottom of the screen

        // initial player position
        player.setPosition(startPlayerX, startPlayerY);

        // create obstacle pool
        obstaclePool = Pools.get(Obstacle.class, 40);

        // create background
        background = new Background();
        background.setPosition(0, 0);
        background.setSize(
                GameConfig.WORLD_WIDTH * GameConfig.WORLD_RATIO_ASPECT,
                GameConfig.WORLD_HEIGHT
        );
    }

    // public methods
    public void update(float delta) {
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

    public Player getPlayer() {
        return player;
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public Background getBackground() { return background; }

    public int getLives() {
        return lives;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    // private methods
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
        player.update();
        blockPlayerFromLeavingTheWorld();
    }

    private void blockPlayerFromLeavingTheWorld() {
        // 2 alternatives
        float playerX = MathUtils.clamp(player.getX(),
                0, // when only with ShapeRenderer: player.getWidth() / 2,
                GameConfig.WORLD_WIDTH - player.getWidth()
        );

        player.setPosition(playerX, player.getY());
    }
    private void updateObstacles(float delta) {
        for (Obstacle obstacle : obstacles) {
            obstacle.update();
        }

        createNewObstacle(delta);
        removePassedObstacles();
    }

    private void createNewObstacle(float delta) {
        obstacleTimer += delta;
        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME) {
            // if we want to create our obstacle completely inside world (not its center)
            // float min = Obstacle.SIZE / 2f;
            // float max = GameConfig.WORLD_WIDTH - Obstacle.SIZE / 2f;
            float min = 0f;
            float max = GameConfig.WORLD_WIDTH;
            float obstacleX = MathUtils.random(min, max);
            float obstacleY = GameConfig.WORLD_HEIGHT;

            Obstacle obstacle = obstaclePool.obtain();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacle.setPosition(obstacleX, obstacleY);

            obstacles.add(obstacle);
            obstacleTimer = 0f;
        }
    }

    private void removePassedObstacles() {
        if (obstacles.size > 0) {
            Obstacle first = obstacles.first();

            float minObstacleY = -GameConfig.OBSTACLE_SIZE;

            if (first.getY() < minObstacleY) {
                obstacles.removeValue(first, true);
                obstaclePool.free(first);
            }
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

}
