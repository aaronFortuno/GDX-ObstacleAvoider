package net.studio.estemon.screen.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import net.studio.estemon.ObstacleAvoiderGame;
import net.studio.estemon.assets.AssetDescriptors;
import net.studio.estemon.assets.RegionNames;
import net.studio.estemon.common.GameManager;
import net.studio.estemon.config.DifficultyLevel;
import net.studio.estemon.config.GameConfig;

public class SettingsScreen extends MenuScreenBase {

    private Image checkmark;


    public SettingsScreen(ObstacleAvoiderGame game) {
        super(game);
    }

    @Override
    protected Actor createUi() {
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

        table.addActor(background);
        table.addActor(label);
        table.addActor(easyButton);
        table.addActor(mediumButton);
        table.addActor(hardButton);
        table.addActor(checkmark);
        table.addActor(backButton);

        return table;
    }

    private void back() {
        game.setScreen(new MenuScreen(game));
    }
}
