package com.aia.hichef.listener;

public interface Uploader {
	public void choiceImageAndUploadToServer(ResponseListener listener);

	public void selectImageFoodCover(ResponseListener listener,
			DataTransistion data);

	public void selectImageFoodStep(ResponseListener listener,DataTransistion data);

}
