package net.studio.estemon.util.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class DebugCameraConfig {

    private static final String MAX_ZOOM_IN = "maxZoomIn";
    private static final String MAX_ZOOM_OUT = "maxZoomOut";
    private static final String MOVE_SPEED = "moveSpeed";
    private static final String ZOOM_SPEED = "zoomSpeed";

    private static final String LEFT_KEY = "leftKey";
    private static final String RIGHT_KEY = "rightKey";
    private static final String UP_KEY = "upKey";
    private static final String DOWN_KEY = "downKey";

    private static final String ZOOM_IN_KEY = "zoomInKey";
    private static final String ZOOM_OUT_KEY = "zommOutKey";
    private static final String RESET_KEY = "resetKey";


    private static final int DEFAULT_LEFT_KEY = Input.Keys.A;
    private static final int DEFAULT_RIGHT_KEY = Input.Keys.D;
    private static final int DEFAULT_UP_KEY = Input.Keys.W;
    private static final int DEFAULT_DOWN_KEY = Input.Keys.S;
    private static final int DEFAULT_ZOOM_IN_KEY = Input.Keys.R;
    private static final int DEFAULT_ZOOM_OUT_KEY = Input.Keys.F;
    private static final int DEFAULT_RESET_KEY = Input.Keys.BACKSPACE;

    private static final float DEFAULT_MOVE_SPEED = 20f;
    private static final float DEFAULT_ZOOM_SPEED = 2f;
    private static final float DEFAULT_MAX_ZOOM_IN = 0.2f;
    private static final float DEFAULT_MAX_ZOOM_OUT = 30f;

    private static final String FILE_PATH = "debug/debugCameraConfig.json";

    private float maxZoomIn;
    private float maxZoomOut;
    private float moveSpeed;
    private float zoomSpeed;

    private int leftKey;
    private int rightKey;
    private int upKey;
    private int downKey;

    private int zoomInKey;
    private int zoomOutKey;

    private int resetKey;

    private FileHandle fileHandle;

    public DebugCameraConfig() {
        init();
    }

    private void init() {
        fileHandle = Gdx.files.internal(FILE_PATH);

        if (fileHandle.exists()) {
            load();
        } else {
            setupDefaults();
        }
    }

    private void load() {
        try {
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(fileHandle);

            maxZoomIn = root.getFloat(MAX_ZOOM_IN);
            maxZoomOut = root.getFloat(MAX_ZOOM_OUT);
            moveSpeed = root.getFloat(MOVE_SPEED);
            zoomSpeed = root.getFloat(ZOOM_SPEED);

            leftKey = root.getInt(LEFT_KEY);
            rightKey = root.getInt(RIGHT_KEY);
            upKey = root.getInt(UP_KEY);
            downKey = root.getInt(DOWN_KEY);

            zoomInKey = root.getInt(ZOOM_IN_KEY);
            zoomOutKey = root.getInt(ZOOM_OUT_KEY);

            resetKey = root.getInt(RESET_KEY);
        } catch (Exception e) {
            setupDefaults();
        }
    }

    private void setupDefaults() {
        maxZoomIn = DEFAULT_MAX_ZOOM_IN;
        maxZoomOut = DEFAULT_MAX_ZOOM_OUT;
        moveSpeed = DEFAULT_MOVE_SPEED;
        zoomSpeed = DEFAULT_ZOOM_SPEED;

        leftKey = DEFAULT_LEFT_KEY;
        rightKey = DEFAULT_RIGHT_KEY;
        upKey = DEFAULT_UP_KEY;
        downKey = DEFAULT_DOWN_KEY;

        zoomInKey = DEFAULT_ZOOM_IN_KEY;
        zoomOutKey = DEFAULT_ZOOM_OUT_KEY;

        resetKey = DEFAULT_RESET_KEY;
    }
}
