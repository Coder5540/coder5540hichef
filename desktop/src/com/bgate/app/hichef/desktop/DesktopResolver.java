package com.bgate.app.hichef.desktop;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.aia.hichef.PlatformResolver;
import com.badlogic.gdx.graphics.Pixmap;

public class DesktopResolver implements PlatformResolver{

	@Override
	public Pixmap formatBitmap(InputStream in) {
		BufferedImage bufferedImage;
		Pixmap pixmap = null;
		try {
			bufferedImage = ImageIO.read(in);
			BufferedImage newBufferedImage = new BufferedImage(
					bufferedImage.getWidth(), bufferedImage.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0,
					new Color(0, 0, 0, 0), null);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			ImageIO.write(newBufferedImage, "png", outStream);
			pixmap = new Pixmap(outStream.toByteArray(), 0,
					outStream.toByteArray().length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pixmap;
	}

	@Override
	public String getDeviceName() {
		return "Desktop";
	}

	@Override
	public String getDeviceID() {
		return "Null";
	}

	
}
