package net.studio.estemon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import net.studio.estemon.screen.GameScreen;

public class ObstacleAvoiderGame extends Game {

	public void create() {
		setScreen(new GameScreen());
	}
}
