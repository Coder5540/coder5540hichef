package com.aia.hichef.assets;

import com.aia.hichef.enums.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontFactory {

	private BitmapFont	ttf_roboto_bold_13;
	private BitmapFont	ttf_roboto_bold_15;
	private BitmapFont	ttf_roboto_bold_20;

	private BitmapFont	ttf_roboto_regular_12;
	private BitmapFont	ttf_roboto_regular_15;
	private BitmapFont	ttf_roboto_regular_20;

	private BitmapFont	ttf_roboto_medium_15;
	private BitmapFont	ttf_roboto_medium_20;

	private BitmapFont	ttf_roboto_light_15;
	private BitmapFont	ttf_roboto_light_20;
	private BitmapFont	ttf_roboto_light_30;

	public FontFactory() {
	}

	public BitmapFont loadFont(String filePath, int size) {
		float SCALE = 1.0f * Gdx.graphics.getWidth() / Constants.WIDTH_SCREEN;
		if (SCALE < 1)
			SCALE = 1;
		boolean flip = false;
		FileHandle fontFile = Gdx.files.internal(filePath);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = (int) (size * SCALE);
		parameter.characters = "Ffaáàảãạăắẳằẵặâấẩầẫậbcdđeéẻèẽẹêếểềễệghiíỉìĩịjklmnoóòỏõọôốồổỗộơớờởỡợpqrstuúùủũụưứừửữựvxyýỳỹỷỵwz AÁÀẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬBCDĐEÉÈẺẼẸÊẾỀỂỄỆGHIÍÌỈĨỊJKLMNOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢPQRSTUÚÙỦŨỤƯỨỪỬỮỰVXYÝỲỶỸỴWZ1234567890\"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*";
		parameter.flip = flip;
		parameter.genMipMaps = true;
		generator.generateData(parameter);
		BitmapFont font = generator.generateFont(parameter);
		font.setScale((float) (1.0 / SCALE));
		font.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose();
		return font;
	}

	public BitmapFont getBold13() {
		if (ttf_roboto_bold_13 == null) {
			ttf_roboto_bold_13 = loadFont("font/Roboto-Bold.ttf", 13);
		}
		return ttf_roboto_bold_13;
	}

	public BitmapFont getBold15() {
		if (ttf_roboto_bold_15 == null) {
			ttf_roboto_bold_15 = loadFont("font/Roboto-Bold.ttf", 15);
		}
		return ttf_roboto_bold_15;
	}

	public BitmapFont getBold20() {
		if (ttf_roboto_bold_20 == null) {
			ttf_roboto_bold_20 = loadFont("font/Roboto-Bold.ttf", 20);
		}
		return ttf_roboto_bold_20;
	}

	public BitmapFont getRegular12() {
		if (ttf_roboto_regular_12 == null) {
			ttf_roboto_regular_12 = loadFont("font/Roboto-Regular.ttf", 12);
		}
		return ttf_roboto_regular_12;
	}

	public BitmapFont getRegular15() {
		if (ttf_roboto_regular_15 == null) {
			ttf_roboto_regular_15 = loadFont("font/Roboto-Regular.ttf", 15);
		}
		return ttf_roboto_regular_15;
	}

	public BitmapFont getRegular20() {
		if (ttf_roboto_regular_20 == null) {
			ttf_roboto_regular_20 = loadFont("font/Roboto-Regular.ttf", 20);
		}
		return ttf_roboto_regular_20;
	}

	public BitmapFont getMedium15() {
		if (ttf_roboto_medium_15 == null) {
			ttf_roboto_medium_15 = loadFont("font/Roboto-Medium.ttf", 15);
		}
		return ttf_roboto_medium_15;
	}

	public BitmapFont getMedium20() {
		if (ttf_roboto_medium_20 == null) {
			ttf_roboto_medium_20 = loadFont("font/Roboto-Medium.ttf", 20);
		}
		return ttf_roboto_medium_20;
	}

	public BitmapFont getLight15() {
		if (ttf_roboto_light_15 == null) {
			ttf_roboto_light_15 = loadFont("font/Roboto-Light.ttf", 15);
		}
		return ttf_roboto_light_15;
	}

	public BitmapFont getLight20() {
		if (ttf_roboto_light_20 == null) {
			ttf_roboto_light_20 = loadFont("font/Roboto-Light.ttf", 20);
		}
		return ttf_roboto_light_20;
	}

	public BitmapFont getLight30() {
		if (ttf_roboto_light_30 == null) {
			ttf_roboto_light_30 = loadFont("font/Roboto-Light.ttf", 30);
		}
		return ttf_roboto_light_30;
	}

}
