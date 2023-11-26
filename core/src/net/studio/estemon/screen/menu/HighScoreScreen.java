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
import net.studio.estemon.common.GameManager;
import net.studio.estemon.config.GameConfig;
import net.studio.estemon.util.GdxUtils;

public class HighScoreScreen extends MenuScreenBase {

    public HighScoreScreen(ObstacleAvoiderGame game) {
        super(game);
    }

    @Override
    protected Actor createUi() {
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
        String highscoreString = GameManager.INSTANCE.getHighScoreString();
        Label highScoreLabel = new Label(highscoreString, labelStyle);

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

        return table;
    }

    private void back() {
        game.setScreen(new MenuScreen(game));
    }

}
