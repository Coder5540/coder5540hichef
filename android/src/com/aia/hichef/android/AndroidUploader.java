package com.aia.hichef.android;

import com.aia.hichef.listener.DataTransistion;
import com.aia.hichef.listener.ResponseListener;
import com.aia.hichef.listener.Uploader;


public class AndroidUploader implements Uploader{
	private HichefAndroid main;
	
	public AndroidUploader(HichefAndroid main) {
		this.main = main;
	}
	
	@Override
	public void choiceImageAndUploadToServer(ResponseListener listener) {
		main.selectImageAndUploadToServer(listener);
	}

	@Override
	public void selectImageFoodCover(ResponseListener listener,
			DataTransistion data) {
		main.selectImageFoodCover(listener,data);		
	}

	@Override
	public void selectImageFoodStep(ResponseListener listener,
			DataTransistion data) {
		main.selectImageFoodStep(listener,data);
	}
	
}
