package net.studio.estemon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;

import net.studio.estemon.screen.GameScreen;

public class ObstacleAvoiderGame extends Game {

	private AssetManager assetManager;
	public void create() {
		assetManager = new AssetManager();
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
