package com.aia.hichef.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetUIM {
	public TextureRegion	ninepatch;

	public TextureRegion	bg_listmenu_actionbar;
	public TextureRegion	icono_actualidad;
	public TextureRegion	icono_info;
	public TextureRegion	icono_recetas;
	public TextureRegion	icono_settings;
	public TextureRegion	bg_actionbar;
	public TextureRegion	bg_icon_caterogy;
	public TextureRegion	bg_icon_caterogy2;
	public TextureRegion	bg_ninepatch;
	public TextureRegion	icon_heart_03;
	public TextureRegion	icon_heart_06;


	public AssetUIM(TextureAtlas atlas) {
		ninepatch = atlas.findRegion("UIM/ninepatch");
		bg_listmenu_actionbar = atlas.findRegion("UIM/bg_listmenu_actionbar");
		icono_actualidad = atlas.findRegion("UIM/icono_actualidad");
		icono_info = atlas.findRegion("UIM/icono_info");
		icono_recetas = atlas.findRegion("UIM/icono_recetas");
		icono_settings = atlas.findRegion("UIM/icono_settings");
		bg_actionbar = atlas.findRegion("UIM/bg_actionbar");

		bg_icon_caterogy = atlas.findRegion("UIM/bg_icon_caterogy");
		bg_icon_caterogy2 = atlas.findRegion("UIM/bg_icon_caterogy2");
		bg_ninepatch = atlas.findRegion("UIM/bg_ninepatch");
		icon_heart_03 = atlas.findRegion("UIM/ic_heart3");
		icon_heart_06 = atlas.findRegion("UIM/ic_heart6");
	}
}
