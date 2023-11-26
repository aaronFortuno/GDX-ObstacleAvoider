package net.studio.estemon.screen.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.studio.estemon.ObstacleAvoiderGame;
import net.studio.estemon.assets.AssetDescriptors;
import net.studio.estemon.assets.AssetPaths;
import net.studio.estemon.assets.RegionNames;
import net.studio.estemon.common.GameManager;
import net.studio.estemon.config.DifficultyLevel;
import net.studio.estemon.config.GameConfig;
import net.studio.estemon.util.GdxUtils;

public class SettingsScreen extends ScreenAdapter {

    private final ObstacleAvoiderGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;
    private Skin skin;
    private Image checkmark;


    public SettingsScreen(ObstacleAvoiderGame game) {
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
        table.defaults().pad(20);

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI_ATLAS);
        BitmapFont font = assetManager.get(AssetDescriptors.FONT);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        TextureRegion panelRegion = uiAtlas.findRegion(RegionNames.PANEL);

        Image background = new Image(backgroundRegion);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label label = new Label("DIFFICULTY", labelStyle);
        label.setPosition(GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2 + 180, Align.center);

        final ImageTextButton easyButton = new ImageTextButton("EASY", skin);
        easyButton.setPosition(GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2 + 90, Align.center);
        easyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkmark.setY(easyButton.getY() + 25);
                GameManager.INSTANCE.updateDifficulty(DifficultyLevel.EASY);
            }
        });

        final ImageTextButton mediumButton = new ImageTextButton("MEDIUM", skin);
        mediumButton.setPosition(GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2, Align.center);
        mediumButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkmark.setY(mediumButton.getY() + 25);
                GameManager.INSTANCE.updateDifficulty(DifficultyLevel.MEDIUM);
            }
        });

        final ImageTextButton hardButton = new ImageTextButton("HARD", skin);
        hardButton.setPosition(GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2 - 90, Align.center);
        hardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkmark.setY(hardButton.getY() + 25);
                GameManager.INSTANCE.updateDifficulty(DifficultyLevel.HARD);
            }
        });

        TextureRegion checkmarkRegion = uiAtlas.findRegion(RegionNames.CHECKMARK);
        checkmark = new Image(new TextureRegionDrawable(checkmarkRegion));
        checkmark.setPosition(mediumButton.getX() + 50, mediumButton.getY() + 40, Align.center);
        checkmark.setColor(Color.DARK_GRAY);
        DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
        if (difficultyLevel.isEasy()) {
            checkmark.setY(easyButton.getY() + 25);
        } else if (difficultyLevel.isHard()) {
            checkmark.setY(hardButton.getY() + 25);
        }

        ImageTextButton backButton = new ImageTextButton("BACK", skin);
        backButton.setPosition(GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2 - 180, Align.center);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        stage.addActor(background);
        stage.addActor(label);
        stage.addActor(easyButton);
        stage.addActor(mediumButton);
        stage.addActor(hardButton);
        stage.addActor(checkmark);
        stage.addActor(backButton);


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
