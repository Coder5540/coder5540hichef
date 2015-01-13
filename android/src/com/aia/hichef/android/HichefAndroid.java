package com.aia.hichef.android;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import uitls.input.GamePlatform;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.listener.DataTransistion;
import com.aia.hichef.listener.ResponseListener;
import com.aia.hichef.networks.Constant;
import com.aia.hichef.networks.FacebookConnector.OnLoginListener;
import com.aia.hichef.screenhelper.GameCore;
import com.aia.hichef.screens.FlashScreen;
import com.aia.hichef.utils.AppPreference;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.facebook.AppEventsLogger;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;

@SuppressLint("HandlerLeak")
public class HichefAndroid extends AndroidApplication {
	Map<String, String>			facebookInfo						= null;
	String						name								= "";
	UiLifecycleHelper			uiHelper;
	TelephonyManager			telephonyManager;

	private ResponseListener	uploadResponseListener;
	private DataTransistion		dataUpLoadCover, dataUploadStep;
	private int					ACTIVITY_ID_PICK_PHOTO_FOOD_COVER	= 42;
	private int					ACTIVITY_ID_PICK_PHOTO_FOOD_STEP	= 43;
	private int					maxWidth							= 200;
	private int					maxHeight							= 200;
	private int					minWidth							= 100;
	private int					minHeight							= 100;
	private HttpClient			mHttpClient;
	String						upLoadServerUri						= "http://125.212.192.94/hichef/upload";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGLSurfaceView20API18 = true;
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);
		getAppKeyHash();

		GameCore game = new GameCore() {
			@Override
			public void create() {
				super.create();
				setScreen(new FlashScreen(this));
			}
		};
		game.setPlatformResolver(new AndroidResolver(this));
		game.setFacebookConnector(new AndroidFBConnector(this));
		game.setUploader(new AndroidUploader(this));
		// initialize(game, config);

		AndroidTextInputHelper textInputHelper = new AndroidTextInputHelper(
				this);
		GamePlatform.helper = textInputHelper;

		View inputView = textInputHelper.getView();
		View gameView = initializeForView(game, config);

		FrameLayout layout = new FrameLayout(this);
		layout.addView(inputView);
		layout.addView(gameView);
		setContentView(layout);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
		// Logs 'install' and 'app activate' App Events.
		AppEventsLogger.activateApp(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		uiHelper.onPause();
		// Logs 'app deactivate' App Event.
		// AppEventsLogger.deactivateApp(this);
	}

	@Override
	protected void onDestroy() {
		android.os.Process.killProcess(android.os.Process.myPid());
		uiHelper.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	// check the return of the result
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// check th id of the result
		if (requestCode == ACTIVITY_ID_PICK_PHOTO_FOOD_COVER) {
			selectPhotoControl(data, ACTIVITY_ID_PICK_PHOTO_FOOD_COVER);
		} else if (requestCode == ACTIVITY_ID_PICK_PHOTO_FOOD_STEP) {
			selectPhotoControl(data, ACTIVITY_ID_PICK_PHOTO_FOOD_STEP);
		} else if (Session.getActiveSession() != null) {
			Session.getActiveSession().onActivityResult(this, requestCode,
					resultCode, data);
			uiHelper.onActivityResult(requestCode, resultCode, data,
					new FacebookDialog.Callback() {
						@Override
						public void onError(
								FacebookDialog.PendingCall pendingCall,
								Exception error, Bundle data) {
							Log.e("Activity", String.format("Error: %s",
									error.toString()));
						}

						@Override
						public void onComplete(
								FacebookDialog.PendingCall pendingCall,
								Bundle data) {
							Log.i("Activity", "Success!");
						}
					});
		}
	}

	void getAppKeyHash() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md;
				md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String something = new String(Base64.encode(md.digest(), 0));
				Log.e("Hash key", something);
			}
		} catch (NameNotFoundException e1) {
			Log.e("name not found", e1.toString());
		}

		catch (NoSuchAlgorithmException e) {
			Log.e("no such an algorithm", e.toString());
		} catch (Exception e) {
			Log.e("exception", e.toString());
		}
	}

	public void performFacebookLogin(final OnLoginListener listener) {
		Log.e("FACEBOOK", "performFacebookLogin");
		if (Session.getActiveSession() != null
				&& Session.getActiveSession().isOpened()) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Request.newMeRequest(Session.getActiveSession(),
							new GraphUserCallback() {
								@Override
								public void onCompleted(GraphUser user,
										Response response) {
									if (user != null) {
										String facebookID = user.getId();
										String firstName = user.getFirstName();
										String lastName = user.getLastName();
										String birthday = user.getBirthday();
										String email = "";
										try {
											email = user.getInnerJSONObject()
													.getString("email");
										} catch (JSONException e) {
											e.printStackTrace();
										}
										name = user.getName();
										if (facebookInfo == null)
											facebookInfo = new HashMap<String, String>();
										facebookInfo.put("id", facebookID);
										facebookInfo.put("first_name",
												firstName);
										facebookInfo.put("last_name", lastName);
										facebookInfo.put("name", name);
										facebookInfo
												.put("avatar",
														"https://graph.facebook.com/"
																+ facebookID
																+ "/picture?width=100&height=100");
										facebookInfo.put("access_token",
												Session.getActiveSession()
														.getAccessToken());
										facebookInfo.put(
												StringSystem._BIRTHDAY,
												birthday);
										facebookInfo.put(StringSystem._EMAIL,
												email);
										loginFBSuccessHandler
												.sendEmptyMessage(0);
										listener.onComplete(facebookInfo);
									} else {
										listener.onError();
									}
								}
							}).executeAsync();
				}
			});
		} else {
			loginFBHandler.sendEmptyMessage(0);
			boolean allowUI = false;
			Session session = new Builder(this).build();
			if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState())) {
				allowUI = false;
			} else {
				allowUI = true;
			}

			List<String> permissions = new ArrayList<String>();
			permissions.add("email");
			permissions.add("user_birthday");

			Session.openActiveSession(this, allowUI, permissions,
					new Session.StatusCallback() {
						@Override
						public void call(final Session session,
								SessionState state, Exception exception) {
							if (exception != null) {
								listener.onError();
							}
							if (session.isOpened()) {
								Request.newMeRequest(session,
										new GraphUserCallback() {
											@Override
											public void onCompleted(
													GraphUser user,
													Response response) {
												if (user != null) {
													String facebookID = user
															.getId();
													String firstName = user
															.getFirstName();
													String lastName = user
															.getLastName();
													String birthday = user
															.getBirthday();
													String email = "";
													try {
														email = user
																.getInnerJSONObject()
																.getString(
																		"email");
													} catch (JSONException e) {
														e.printStackTrace();
													}

													name = user.getName();
													if (facebookInfo == null)
														facebookInfo = new HashMap<String, String>();
													facebookInfo.put("id",
															facebookID);
													facebookInfo.put(
															"first_name",
															firstName);
													facebookInfo.put(
															"last_name",
															lastName);
													facebookInfo.put("name",
															name);
													facebookInfo
															.put("avatar",
																	"https://graph.facebook.com/"
																			+ facebookID
																			+ "/picture?width=100&height=100");
													facebookInfo
															.put("access_token",
																	session.getAccessToken());
													facebookInfo
															.put(StringSystem._BIRTHDAY,
																	birthday);
													facebookInfo
															.put(StringSystem._EMAIL,
																	email);

													Log.e("Facebook Info : ",
															"Email : "
																	+ email
																	+ "    Birthday : "
																	+ birthday);
													Log.e("Debug User : ",
															user.getInnerJSONObject()
																	.toString());
													Log.e("Debug Response : ",
															response.getRawResponse());

													loginFBSuccessHandler
															.sendEmptyMessage(0);
													listener.onComplete(facebookInfo);
												} else {
													listener.onError();
												}
											}
										}).executeAsync();
							}
						}
					});
		}
	}

	Handler	loginFBHandler			= new Handler() {

										@Override
										public void handleMessage(Message msg) {
											Toast.makeText(
													HichefAndroid.this,
													"Đăng nhập tài khoản facebook...",
													Toast.LENGTH_LONG).show();
										}

									};

	Handler	loginFBSuccessHandler	= new Handler() {

										@Override
										public void handleMessage(Message msg) {
											Toast.makeText(
													HichefAndroid.this,
													"Đăng nhập thành công, chào mừng "
															+ name + "!",
													Toast.LENGTH_SHORT).show();
										}

									};

	public String getDeviceId() {
		return telephonyManager.getDeviceId();
	}

	public void shareImage(FileHandle fileHandle) {
		Intent share = new Intent(Intent.ACTION_SEND);

		share.setType("image/*");

		File imageFileToShare = fileHandle.file();

		Uri uri = Uri.fromFile(imageFileToShare);
		share.putExtra(Intent.EXTRA_STREAM, uri);
		share.putExtra(
				"android.intent.extra.TEXT",
				"I have got a new archievement ! "
						+ "https://play.google.com/store/apps/details?id=com.bgate.escaptain");

		startActivity(Intent.createChooser(share, "Share!"));
	}

	public void share(final String url, final String n, final String des) {
		boolean allowUI = false;
		Session session = new Builder(this).build();
		if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState())) {
			allowUI = false;
		} else {
			allowUI = true;
		}
		Session.openActiveSession(this, allowUI, new Session.StatusCallback() {
			@Override
			public void call(final Session session, SessionState state,
					Exception exception) {
				if (exception != null) {
				}
				if (session.isOpened()) {
					String link = "https://play.google.com/store/apps/details?id=com.aia.hichef";

					// Define the other parameters
					String name = des;
					String caption = "Dậy nấu ăn";
					String description = n;
					String picture = url;
					if (FacebookDialog.canPresentShareDialog(
							HichefAndroid.this,
							FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
						// Create the Native Share dialog
						FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
								HichefAndroid.this).setLink(link).setName(name)
								.setCaption(caption).setPicture(picture)
								.setDescription(description).build();

						// Show the Native Share dialog
						uiHelper.trackPendingDialogCall(shareDialog.present());
					} else {
						// Prepare the web dialog parameters
						Bundle params = new Bundle();
						params.putString("link", link);
						params.putString("name", caption);
						params.putString("caption", caption);
						params.putString("description", description);
						params.putString("picture", picture);
						// Show FBDialog without a notification bar
						showDialogWithoutNotificationBar("feed", params);
					}
				}
			}
		});
	}

	WebDialog	dialog			= null;
	String		dialogAction	= null;
	Bundle		dialogParams	= null;

	private void showDialogWithoutNotificationBar(String action, Bundle params) {
		// Create the dialog
		dialog = new WebDialog.Builder(HichefAndroid.this,
				Session.getActiveSession(), action, params)
				.setOnCompleteListener(new WebDialog.OnCompleteListener() {

					@Override
					public void onComplete(Bundle values,
							FacebookException error) {
						if (error != null
								&& !(error instanceof FacebookOperationCanceledException)) {
						}
						dialog = null;
						dialogAction = null;
						dialogParams = null;
					}
				}).build();

		// Hide the notification bar and resize to full screen
		Window dialog_window = dialog.getWindow();
		dialog_window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Store the dialog information in attributes
		dialogAction = action;
		dialogParams = params;
		// Show the dialog
		dialog.show();
	}

	// Working data
	private void selectPhotoControl(Intent data, int activityID) {
		// check if any photo is selected
		if (data == null)
			return;
		// get the uri of the picture selected
		Uri photoUri = data.getData();
		if (photoUri != null) {
			// decode the Uri
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(photoUri,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String filePath = cursor.getString(columnIndex);
			System.out.println("File Patch : " + filePath);
			cursor.close();
			// get the image in the bitmap and resize the image
			try {
				Bitmap bp = resize(filePath);
				if (bp != null) {
					HttpResponse res = postImage(bp, filePath, activityID);
					if (res != null) {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(res.getEntity()
										.getContent(), "UTF-8"));
						String sResponse;
						StringBuilder s = new StringBuilder();
						while ((sResponse = reader.readLine()) != null) {
							s = s.append(sResponse);
						}
						System.out.println("server response:: " + s.toString());
						if (uploadResponseListener != null) {
							uploadResponseListener.response(s.toString());
							uploadResponseListener = null;
						}
					}
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Bitmap resize(String path) {
		// create the options
		BitmapFactory.Options opts = new BitmapFactory.Options();

		// just decode the file
		opts.inJustDecodeBounds = true;
		Bitmap bp = BitmapFactory.decodeFile(path, opts);

		// get the original size
		int orignalHeight = opts.outHeight;
		int orignalWidth = opts.outWidth;
		// initialization of the scale
		int resizeScale = 1;
		// get the good scale
		if (orignalWidth < minWidth || orignalHeight < minHeight) {
			Toast.makeText(this, "Ảnh phải có kích thước tối thiểu 100x100",
					Toast.LENGTH_LONG).show();
			return null;
		} else if (orignalWidth > maxWidth || orignalHeight > maxHeight) {
			final int heightRatio = Math.round((float) orignalHeight
					/ (float) maxHeight);
			final int widthRatio = Math.round((float) orignalWidth
					/ (float) maxWidth);
			resizeScale = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		// put the scale instruction (1 -> scale to (1/1); 8-> scale to 1/8)
		opts.inSampleSize = resizeScale;
		opts.inJustDecodeBounds = false;
		// get the futur size of the bitmap
		int bmSize = (orignalWidth / resizeScale)
				* (orignalHeight / resizeScale) * 4;
		// check if it's possible to store into the vm java the picture
		if (Runtime.getRuntime().freeMemory() > bmSize) {
			// decode the file
			bp = BitmapFactory.decodeFile(path, opts);
		} else {
			Toast.makeText(this, "Không đủ bộ nhớ", Toast.LENGTH_LONG).show();
			return null;
		}
		return bp;
	}

	public HttpResponse postImage(Bitmap bp, String uristr, int activityID)
			throws ClientProtocolException, IOException {
		if (mHttpClient == null)
			mHttpClient = new DefaultHttpClient();
		// initialization of the post request
		HttpPost httpPost = new HttpPost(upLoadServerUri);
		if (bp != null) {
			if (activityID == ACTIVITY_ID_PICK_PHOTO_FOOD_COVER) {
				MultipartEntity entity = new MultipartEntity();
				entity.addPart(
						"uploadAction",
						new StringBody(Constant.ACTION_UPLOAD_FOOD_IMG, Charset
								.forName("UTF-8")));
				entity.addPart("idUser",
						new StringBody("" + AppPreference.instance._userID,
								Charset.forName("UTF-8")));
				entity.addPart(
						"foodName",
						new StringBody(dataUpLoadCover == null ? "Chưa đặt tên"
								: dataUpLoadCover.getFoodName(), Charset
								.forName("UTF-8")));

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				bp.compress(CompressFormat.PNG, 100, bos);
				String filename = uristr.substring(uristr.lastIndexOf("/") + 1,
						uristr.length());
				FormBodyPart fbp = new FormBodyPart("fileUpload",
						new ByteArrayBody(bos.toByteArray(), "image/png",
								filename));
				entity.addPart(fbp);
				httpPost.setEntity(entity);
			}
			if (activityID == ACTIVITY_ID_PICK_PHOTO_FOOD_STEP) {
				MultipartEntity entity = new MultipartEntity();
				entity.addPart(
						"uploadAction",
						new StringBody(Constant.ACTION_UPLOAD_FOOD_IMG, Charset
								.forName("UTF-8")));
				entity.addPart("idUser",
						new StringBody("" + AppPreference.instance._userID,
								Charset.forName("UTF-8")));
				entity.addPart(
						"foodName",
						new StringBody(dataUpLoadCover == null ? "Chưa đặt tên"
								: dataUpLoadCover.getFoodName(), Charset
								.forName("UTF-8")));

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				bp.compress(CompressFormat.PNG, 100, bos);
				String filename = uristr.substring(uristr.lastIndexOf("/") + 1,
						uristr.length());
				FormBodyPart fbp = new FormBodyPart("fileUpload",
						new ByteArrayBody(bos.toByteArray(), "image/png",
								filename));
				entity.addPart(fbp);

				entity.addPart(
						"foodDirection",
						new StringBody(dataUploadStep == null ? "1"
								: dataUploadStep.getFoodDirection(), Charset
								.forName("UTF-8")));
				httpPost.setEntity(entity);
			}
		}
		return exec(httpPost);
	}

	public void selectImageAndUploadToServer(ResponseListener listener) {
		// Call the activity for select photo into the gallery
		uploadResponseListener = listener;
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent,
				ACTIVITY_ID_PICK_PHOTO_FOOD_COVER);
	}

	public void selectImageFoodCover(ResponseListener listener,
			DataTransistion data) {
		dataUpLoadCover = data;
		uploadResponseListener = listener;
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent,
				ACTIVITY_ID_PICK_PHOTO_FOOD_COVER);
	}

	public void selectImageFoodStep(ResponseListener listener,
			DataTransistion data) {
		dataUploadStep = data;
		uploadResponseListener = listener;
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent,
				ACTIVITY_ID_PICK_PHOTO_FOOD_STEP);
	}

	protected synchronized HttpResponse exec(HttpRequestBase base)
			throws ClientProtocolException, IOException {
		if (base != null) {
			Log.w("Respone Upload : ",
					"====================== Return Null =============");
			// Execute the request
			return mHttpClient.execute(base);
		} else {
			Log.w("Respone Upload : ",
					"====================== Return Null =============");
			return null;
		}

	}

}
