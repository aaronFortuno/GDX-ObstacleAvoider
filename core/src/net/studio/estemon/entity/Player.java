package net.studio.estemon.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import net.studio.estemon.config.GameConfig;

public class Player extends GameObjectBase {

    public Player() {
        super(GameConfig.PLAYER_BOUNDS_RADIUS);
        setSize(GameConfig.PLAYER_SIZE, GameConfig.PLAYER_SIZE);
    }

    public void update() {
        float xSpeed = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { xSpeed = GameConfig.MAX_PLAYER_X_SPEED; }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { xSpeed = -GameConfig.MAX_PLAYER_X_SPEED; }

        setX(getX() + xSpeed);
        updateBounds(); // we have to update it to show the new position
    }
}
