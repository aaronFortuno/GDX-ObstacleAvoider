package net.studio.estemon.util.debug;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class DebugCameraController {



    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();
    private float zoom = 1f;

    public DebugCameraController() {}

    public void setStartPosition(float x, float y) {
        startPosition.set(x, y);
        position.set(x, y);
    }

    public void applyTo(OrthographicCamera camera) {
        camera.position.set(position, 0);
        camera.zoom = zoom;
        camera.update();
    }

    public void handleDebugInput(float delta) {
        // check if we are not on desktop then not handle inputs
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }

        float moveSpeed = DEFAULT_MOVE_SPEED * delta;
        float zoomSpeed = DEFAULT_ZOOM_SPEED * delta;

        if (Gdx.input.isKeyPressed(DEFAULT_LEFT_KEY)) { moveLeft(moveSpeed); }
        if (Gdx.input.isKeyPressed(DEFAULT_RIGHT_KEY)) { moveRight(moveSpeed); }
        if (Gdx.input.isKeyPressed(DEFAULT_UP_KEY)) { moveUp(moveSpeed); }
        if (Gdx.input.isKeyPressed(DEFAULT_DOWN_KEY)) { moveDown(moveSpeed); }

        if (Gdx.input.isKeyPressed(DEFAULT_ZOOM_IN_KEY)) { zoomIn(zoomSpeed); }
        if (Gdx.input.isKeyPressed(DEFAULT_ZOOM_OUT_KEY)) { zoomOut(zoomSpeed); }

        if (Gdx.input.isKeyPressed(DEFAULT_RESET_KEY)) { reset(); }
    }

    private void setPosition(float x, float y) {
        position.set(x, y);
    }

    private void setZoom(float value) {
        // check if our values are inside valid min-max zoom levels
        zoom = MathUtils.clamp(value, DEFAULT_MAX_ZOOM_IN, DEFAULT_MAX_ZOOM_OUT);
    }

    private void moveCamera(float xSpeed, float ySpeed) {
        setPosition(position.x + xSpeed, position.y + ySpeed);
    }

    private void moveLeft(float moveSpeed) {
        moveCamera(-moveSpeed, 0);
    }

    private void moveRight(float moveSpeed) {
        moveCamera(moveSpeed, 0);
    }

    private void moveUp(float moveSpeed) {
        moveCamera(0, moveSpeed);
    }

    private void moveDown(float moveSpeed) {
        moveCamera(0, -moveSpeed);
    }

    private void zoomIn(float zoomSpeed) {
        setZoom(zoom - zoomSpeed);
    }

    private void zoomOut(float zoomSpeed) {
        setZoom(zoom + zoomSpeed);
    }

    private void reset() {
        position.set(startPosition);
        setZoom(1.0f);
    }

}
