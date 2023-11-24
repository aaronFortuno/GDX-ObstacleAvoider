package net.studio.estemon.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;

import net.studio.estemon.config.GameConfig;

public class Obstacle extends GameObjectBase implements Pool.Poolable {

    private float ySpeed = GameConfig.MEDIUM_OBSTACLE_SPEED;
    private boolean hit;

    public Obstacle() {
        super(GameConfig.OBSTACLE_BOUNDS_RADIUS);
        setSize(GameConfig.OBSTACLE_SIZE, GameConfig.OBSTACLE_SIZE);
    }

    public void update() {
        setY(getY() - ySpeed);
    }

    public boolean isPlayerColliding(Player player) {
        Circle playerBounds = player.getBounds();
        // check if playerBounds overlaps obstacle bounds
        boolean overlaps = Intersector.overlaps(playerBounds, getBounds());
        hit = overlaps;
        return overlaps;
    }

    public boolean isNotHit() { return !hit; }

    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    @Override
    public void drawDebug(ShapeRenderer renderer) {
        super.drawDebug(renderer);
        renderer.x(getBounds().x, getBounds().y, 0.1f);
    }

    @Override
    public void reset() {
        // we have to reset obstacles hit state in order to reuse them with pool
        hit = false;
    }
}
