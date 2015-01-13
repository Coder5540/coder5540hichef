package com.aia.hichef.ui;
//package com.aia.hichef.UI;
//
//import com.badlogic.gdx.assets.AssetManager;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//
//public class Asset {
//	private static Asset	INSTANCE;
//
////	public BitmapFont		font_10;
////	public BitmapFont		font_12;
////	public BitmapFont		font_13;
////	public BitmapFont		font_15;
////	public BitmapFont		font_20;
////	public BitmapFont		font_30;
//	public Texture			ninepatch;
//
//	public Texture			tapface;
//	public Texture			bg_listmenu_actionbar;
//	public Texture			icono_actualidad;
//	public Texture			icono_info;
//	public Texture			icono_recetas;
//	public Texture			icono_settings;
//	public Texture			bg_actionbar;
//	public Texture			bg_icon_caterogy;
//	public Texture			bg_icon_caterogy2;
//	public Texture			bg_ninepatch;
//
//	public Asset() {
//	}
//
//	public void load(AssetManager assetManager) {
//		assetManager.load("font/ARIALBD-10.fnt", BitmapFont.class);
//		assetManager.load("font/ARIALBD-12.fnt", BitmapFont.class);
//		assetManager.load("font/ARIALBD-13.fnt", BitmapFont.class);
//		assetManager.load("font/ARIALBD-15.fnt", BitmapFont.class);
//		assetManager.load("font/ARIALBD-20.fnt", BitmapFont.class);
//		assetManager.load("font/ARIALBD-30.fnt", BitmapFont.class);
//		assetManager.load("Image_biz/ninepatch.png", Texture.class);
//		assetManager.load("Image_biz/bg_ninepatch.png", Texture.class);
//
//		assetManager.load("Image_biz/tapface.jpg", Texture.class);
//		assetManager.load("Image_biz/bg_listmenu_actionbar.png", Texture.class);
//		assetManager.load("Image_biz/icono_actualidad.png", Texture.class);
//		assetManager.load("Image_biz/icono_info.png", Texture.class);
//		assetManager.load("Image_biz/icono_recetas.png", Texture.class);
//		assetManager.load("Image_biz/icono_settings.png", Texture.class);
//		assetManager.load("Image_biz/bg_actionbar.png", Texture.class);
//		assetManager.load("Image_biz/bg_icon_caterogy.png", Texture.class);
//		assetManager.load("Image_biz/bg_icon_caterogy2.png", Texture.class);
//
//		assetManager.finishLoading();
//		font_10 = assetManager.get("newfont/ARIALBD-10.fnt");
//		font_12 = assetManager.get("newfont/ARIALBD-12.fnt");
//		font_13 = assetManager.get("newfont/ARIALBD-13.fnt");
//		font_15 = assetManager.get("newfont/ARIALBD-15.fnt");
//		font_20 = assetManager.get("newfont/ARIALBD-20.fnt");
//		font_30 = assetManager.get("newfont/ARIALBD-30.fnt");
//		
//		
//		ninepatch = assetManager.get("Image_biz/ninepatch.png");
//		tapface = assetManager.get("Image_biz/tapface.jpg");
//		bg_listmenu_actionbar = assetManager.get("Image_biz/bg_listmenu_actionbar.png");
//		icono_actualidad = assetManager.get("Image_biz/icono_actualidad.png");
//		icono_info = assetManager.get("Image_biz/icono_info.png");
//		icono_recetas = assetManager.get("Image_biz/icono_recetas.png");
//		icono_settings = assetManager.get("Image_biz/icono_settings.png");
//		bg_actionbar = assetManager.get("Image_biz/bg_actionbar.png");
//
//		bg_icon_caterogy = assetManager.get("Image_biz/bg_icon_caterogy.png");
//		bg_icon_caterogy2 = assetManager.get("Image_biz/bg_icon_caterogy2.png");
//		bg_ninepatch = assetManager.get("Image_biz/bg_ninepatch.png");
//
//	}
//
//	public static Asset getInstance() {
//		if (INSTANCE == null) {
//			INSTANCE = new Asset();
//		}
//		return INSTANCE;
//	}
//}
