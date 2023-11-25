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

    public static final AssetDescriptor<TextureAtlas> UI_ATLAS =
            new AssetDescriptor<TextureAtlas>(AssetPaths.UI_ATLAS, TextureAtlas.class);
}
