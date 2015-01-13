package com.aia.hichef.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetUIP {

	public TextureRegion bgActionBar;
	public TextureRegion bgMainMenu;
	public TextureRegion iconMenu;
	public TextureRegion iconBack;
	public TextureRegion icon;
	public TextureRegion iconSearch;
	public TextureRegion iconSmallSearch;
	public TextureRegion iconSubMenu;
	public TextureRegion ninepatch_gray;
	public TextureRegion ninepatch_white;
	public TextureRegion transparent;
	public TextureRegion transparent2;
	public TextureRegion textfield;
	public TextureRegion rgDel;
	public TextureRegion rgDelDown;
	public TextureRegion radioNoChoice;
	public TextureRegion radioChoice;
	public TextureRegion checkboxChecked;
	public TextureRegion checkboxUnChecked;

	public TextureRegion tapFace;
	public TextureRegion icono_actualidad;
	public TextureRegion icono_info;
	public TextureRegion icono_recetas;
	public TextureRegion icono_settings;

	public AssetUIP(TextureAtlas textureAtlas) {
		bgActionBar = textureAtlas.findRegion("UIP/bg_actionbar");
		bgMainMenu = textureAtlas.findRegion("UIP/bg_mainmenu");
		iconMenu = textureAtlas.findRegion("UIP/icon_menu");
		iconBack = textureAtlas.findRegion("UIP/icon_back");
		icon = textureAtlas.findRegion("UIP/icon");
		iconSearch = textureAtlas.findRegion("UIP/icon-search");
		iconSmallSearch = textureAtlas.findRegion("UIP/icon_smallsearch");
		iconSubMenu = textureAtlas.findRegion("UIP/icon_submenu");
		ninepatch_gray = textureAtlas.findRegion("UIP/bg_gray");
		ninepatch_white = textureAtlas.findRegion("UIP/bg_white");
		transparent = textureAtlas.findRegion("UIP/transparent");
		transparent2 = textureAtlas.findRegion("UIP/transparent2");
		textfield = textureAtlas.findRegion("UIP/textfield");
		rgDel = textureAtlas.findRegion("UIP/icon_cancel");
		rgDelDown = textureAtlas.findRegion("UIP/icon_cancel_down");
		radioChoice = textureAtlas.findRegion("UIP/radio_choice");
		radioNoChoice = textureAtlas.findRegion("UIP/radio_nochoice");
		checkboxChecked = textureAtlas.findRegion("UIP/checked_checkbox");
		checkboxUnChecked = textureAtlas.findRegion("UIP/unchecked_checkbox");

		tapFace = textureAtlas.findRegion("UIP/tapface");
		icono_actualidad = textureAtlas.findRegion("UIP/icono_actualidad");
		icono_info = textureAtlas.findRegion("UIP/icono_info");
		icono_recetas = textureAtlas.findRegion("UIP/icono_recetas");
		icono_settings = textureAtlas.findRegion("UIP/icono_settings");
	}
}
