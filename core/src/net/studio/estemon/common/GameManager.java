package net.studio.estemon.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import net.studio.estemon.ObstacleAvoiderGame;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();
    private static final String HIGH_SCORE_KEY = "highscore";
    private Preferences PREFS;
    private int highscore;

    // SINGLETON, not instantiable
    private GameManager() {
        PREFS = Gdx.app.getPreferences(ObstacleAvoiderGame.class.getSimpleName());
        highscore = PREFS.getInteger(HIGH_SCORE_KEY, 0);
    }

    public void updateHighscore(int score) {
        if (score < highscore) {
            return;
        }
        highscore = score;
        PREFS.putInteger(HIGH_SCORE_KEY, highscore);
        PREFS.flush();
    }

    public String getHighScoreString() {
        return String.valueOf(highscore);
    }


}
