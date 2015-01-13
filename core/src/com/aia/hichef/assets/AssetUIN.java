package com.aia.hichef.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetUIN {
	public TextureRegion	loading;
	public TextureRegion	line;
	public TextureRegion	share;

	public AssetUIN(TextureAtlas atlas) {
		loading = atlas.findRegion("UIN/abs__spinner_48_inner_holo");
		line = atlas.findRegion("UIN/line");
		share = atlas.findRegion("UIN/share");
		// share = new TextureRegion(new Texture("Img/nhat/share.png"));
	}
}
