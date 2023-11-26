package net.studio.estemon.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import net.studio.estemon.ObstacleAvoiderGame;
import net.studio.estemon.assets.AssetDescriptors;
import net.studio.estemon.assets.RegionNames;
import net.studio.estemon.screen.game.GameScreen;

public class MenuScreen extends MenuScreenBase {

    public MenuScreen(ObstacleAvoiderGame game) {
        super(game);
    }

    protected Actor createUi() {
        Table table = new Table();
        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI_ATLAS);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        TextureRegion panelRegion = uiAtlas.findRegion(RegionNames.PANEL);

        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // play button
        ImageTextButton playButton = new ImageTextButton("PLAY", skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });
        // highscore button
        ImageTextButton highscoreButton = new ImageTextButton("HIGHSCORE", skin);
        highscoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHighscore();
            }
        });
        // settings button
        ImageTextButton settingsButton = new ImageTextButton("SETTINGS", skin);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showSettings();
            }
        });
        // quit button
        ImageTextButton quitButton = new ImageTextButton("QUIT", skin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO
            }
        });

        // setup table
        Table buttonTable = new Table();
        buttonTable.defaults().pad(20);
        buttonTable.setBackground(new TextureRegionDrawable(panelRegion));

        buttonTable.add(playButton).row();
        buttonTable.add(highscoreButton).row();
        buttonTable.add(settingsButton).row();
        buttonTable.add(quitButton).row();
        buttonTable.center();

        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private void play() {
        game.setScreen(new GameScreen(game));
    }

    private void showHighscore() {
        game.setScreen(new HighScoreScreen(game));
    }

    private void showSettings() {
        game.setScreen(new SettingsScreen(game));
    }
}
