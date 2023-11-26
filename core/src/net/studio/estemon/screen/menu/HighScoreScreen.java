package net.studio.estemon.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
import net.studio.estemon.util.GdxUtils;

public class HighScoreScreen extends ScreenAdapter {

    private final ObstacleAvoiderGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;
    private Skin skin;

    public HighScoreScreen(ObstacleAvoiderGame game) {
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
        BitmapFont font = assetManager.get(AssetDescriptors.FONT);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        TextureRegion panelRegion = uiAtlas.findRegion(RegionNames.PANEL);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.RED);

        // background
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // highscore text
        Label highScoreText = new Label("HIGHSCORE", labelStyle);

        // highscore label
        Label highScoreLabel = new Label("100", labelStyle);

        // back button
        ImageTextButton backButton = new ImageTextButton("BACK", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        // setup tables
        Table contentTable = new Table();
        contentTable.defaults().pad(20);
        contentTable.setBackground(new TextureRegionDrawable(panelRegion));

        contentTable.add(highScoreText).row();
        contentTable.add(highScoreLabel).row();
        contentTable.add(backButton);

        contentTable.center();

        table.add(contentTable);
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

    private void back() {
        game.setScreen(new MenuScreen(game));
    }

}
