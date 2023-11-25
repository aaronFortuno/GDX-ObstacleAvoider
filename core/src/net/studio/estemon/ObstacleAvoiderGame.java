package net.studio.estemon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

import net.studio.estemon.screen.game.GameScreen;
import net.studio.estemon.screen.loading.LoadingScreen;

public class ObstacleAvoiderGame extends Game {

	private AssetManager assetManager;
	public void create() {
		assetManager = new AssetManager();
		setScreen(new LoadingScreen(this));
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
