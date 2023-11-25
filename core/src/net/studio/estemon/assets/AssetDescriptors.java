package net.studio.estemon.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {
    private AssetDescriptors() {} // not instantiable

    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);
    public static final AssetDescriptor<TextureAtlas> GAMEPLAY_ATLAS =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAMEPLAY_ATLAS, TextureAtlas.class);

    /*public static final AssetDescriptor<Texture> BACKGROUND =
            new AssetDescriptor<Texture>(AssetPaths.BACKGROUND, Texture.class);
    public static final AssetDescriptor<Texture> OBSTACLE_LARGE =
            new AssetDescriptor<Texture>(AssetPaths.OBSTACLE_LARGE, Texture.class);
    public static final AssetDescriptor<Texture> PLAYER_SHIP_A =
            new AssetDescriptor<Texture>(AssetPaths.SHIP_A, Texture.class);*/
}
