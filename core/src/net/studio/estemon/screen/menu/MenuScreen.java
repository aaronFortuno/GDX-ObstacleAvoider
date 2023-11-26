package net.studio.estemon.screen.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.studio.estemon.ObstacleAvoiderGame;
import net.studio.estemon.assets.AssetDescriptors;
import net.studio.estemon.assets.AssetPaths;
import net.studio.estemon.assets.RegionNames;
import net.studio.estemon.config.GameConfig;
import net.studio.estemon.screen.game.GameScreen;
import net.studio.estemon.util.GdxUtils;

public class MenuScreen extends ScreenAdapter {

    private final ObstacleAvoiderGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;
    private Skin skin;

    public MenuScreen(ObstacleAvoiderGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());
        skin = assetManager.get(AssetPaths.UI_SKIN);

        Gdx.input.setInputProcessor(stage);

        initUi();
    }

    private void initUi() {
        Table table = new Table();
        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI_ATLAS);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        TextureRegion panelRegion = uiAtlas.findRegion(RegionNames.PANEL);

        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // play button
        ImageTextButton playButton = createButton("PLAY");
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });
        // highscore button
        ImageTextButton highscoreButton = createButton("HIGHSCORE");
        highscoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHighscore();
            }
        });
        // settings button
        ImageTextButton settingsButton = createButton("SETTINGS");
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showSettings();
            }
        });
        // quit button
        ImageTextButton quitButton = createButton("QUIT");
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

        stage.addActor(table);
    }



    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void play() {
        game.setScreen(new GameScreen(game));
    }

    private void showHighscore() {
        game.setScreen(new HighScoreScreen(game));
    }

    private void showSettings() {

    }

    private ImageTextButton createButton(String buttonText) {
        ImageTextButton button = new ImageTextButton(buttonText, skin);
        button.setSize(100, 50);
        return button;
    }
}
