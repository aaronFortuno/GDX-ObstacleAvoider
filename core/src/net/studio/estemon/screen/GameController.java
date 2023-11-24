package net.studio.estemon.screen;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import net.studio.estemon.config.DifficultyLevel;
import net.studio.estemon.config.GameConfig;
import net.studio.estemon.entity.Obstacle;
import net.studio.estemon.entity.Player;

public class GameController {

    // constants

    // attributes
    private Player player;
    private Array<Obstacle> obstacles = new Array<Obstacle>();

    private float obstacleTimer;
    private float scoreTimer;

    private int lives = GameConfig.LIVES_START;
    private int score;
    private int displayScore;

    private DifficultyLevel difficultyLevel = DifficultyLevel.HARD;


    // constructor
    public GameController() {
        init();
    }

    private void init() {
        // create player
        player = new Player();

        // calculate position
        float startPlayerX = GameConfig.WORLD_WIDTH / 2; // set starting x position at center
        float startPlayerY = 1; // set starting y at bottom of the screen

        // position player
        player.setPosition(startPlayerX, startPlayerY);
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
                player.getWidth() / 2,
                GameConfig.WORLD_WIDTH - player.getWidth() / 2
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

            Obstacle obstacle = new Obstacle();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacle.setPosition(obstacleX, obstacleY);

            obstacles.add(obstacle);
            obstacleTimer = 0f;
        }
    }

    private void removePassedObstacles() {
        if (obstacles.size > 0) {
            Obstacle first = obstacles.first();

            float minObstacleY = -Obstacle.SIZE;

            if (first.getY() < minObstacleY) {
                obstacles.removeValue(first, true);
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
