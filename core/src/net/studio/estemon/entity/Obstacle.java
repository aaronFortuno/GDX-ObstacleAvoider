package net.studio.estemon.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import net.studio.estemon.config.GameConfig;

public class Obstacle extends GameObjectBase {

    private static final float BOUNDS_RADIUS = 0.3f;
    public static final float SIZE = 2 * BOUNDS_RADIUS;
    // private static final float MAX_X_SPEED = 0.2f;

    private float ySpeed = GameConfig.MEDIUM_OBSTACLE_SPEED;
    private boolean hit;

    public Obstacle() {
        super(BOUNDS_RADIUS);
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
}
