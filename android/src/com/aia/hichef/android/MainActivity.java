//package com.aia.hichef.android;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.security.NoSuchAlgorithmException;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpRequestBase;
//import org.apache.http.entity.mime.FormBodyPart;
//import org.apache.http.entity.mime.HttpMultipartMode;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.ByteArrayBody;
//import org.apache.http.entity.mime.content.StringBody;
//import org.apache.http.impl.client.DefaultHttpClient;
//
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.app.PendingIntent;
//import android.app.ProgressDialog;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.CompressFormat;
//import android.graphics.BitmapFactory;
//import android.net.ConnectivityManager;
//import android.net.Uri;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.provider.MediaStore;
//import android.provider.Settings;
//import android.telephony.PhoneStateListener;
//import android.telephony.SignalStrength;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.backends.android.AndroidApplication;
//import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
//import com.facebook.FacebookException;
//import com.facebook.Request;
//import com.facebook.Request.GraphUserCallback;
//import com.facebook.Response;
//import com.facebook.Session;
//import com.facebook.Session.Builder;
//import com.facebook.SessionState;
//import com.facebook.UiLifecycleHelper;
//import com.facebook.model.GraphUser;
//import com.facebook.widget.FacebookDialog;
//import com.facebook.widget.WebDialog;
//import com.facebook.widget.WebDialog.OnCompleteListener;
//import com.gdx.hd.casino.core.Casino;
//import com.gdx.hd.casino.core.Config;
//import com.gdx.hd.casino.core.Constants;
//import com.gdx.hd.casino.core.Info;
//import com.gdx.hd.connect.FacebookConnector.OnActionListener;
//import com.gdx.hd.connect.FacebookConnector.OnLoginListener;
//import com.gdx.hd.impl.Uploader;
//import com.gdx.hd.impl.Uploader.ResponseListener;
//import com.gdx.hd.utils.MD5Good;
//
//@SuppressLint("NewApi")
//public class MainActivity extends AndroidApplication {
//	Casino game;
//	WifiManager wifiManager;
//	TelephonyManager telephonyManager;
//	// SmsManager smsManager;
//	PendingIntent sentPI;
//	PendingIntent deliveredPI;
//	AndroidClient client;
//	boolean is3G, isWifi;
//	volatile boolean isFetching = false;
//	String name = "";
//
//	FileDialog fileDialog;
//	int serverResponseCode = 0;
//	ProgressDialog progressDialog = null;
//	ShareExternalServer appUtil;
//	String upLoadServerUri = "http://" + Config.UPLOAD_HOST + "/"
//			+ Config.UPLOAD_PATH;
//	Map<String, String> facebookInfo = null;
//	UiLifecycleHelper uiHelper;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
////		// To track statistics around application
////        ParseAnalytics.trackAppOpened(getIntent());
//		
//		appUtil = new ShareExternalServer();
//
//		uiHelper = new UiLifecycleHelper(this, null);
//		uiHelper.onCreate(savedInstanceState);
//
//		Casino.platform = Casino.Platform.Android;
//
//		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//		is3G = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
//				.isConnectedOrConnecting();
//		isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
//				.isConnectedOrConnecting();
//
//		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//
//		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//
//		if (isWifi) {
//			Casino.network = Casino.Network.Wireless;
//			registerReceiver(mWifiStateChangedReceiver, new IntentFilter(
//					WifiManager.RSSI_CHANGED_ACTION));
//		} else if (is3G) {
//			Casino.network = Casino.Network.MobiData;
//			TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//			telManager.listen(mPhoneStateListener,
//					PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
//		} else {
//			showNoConnectionDialog(this);
//		}
//
//		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
//		cfg.useWakelock = true;
//
//		game = new Casino();
//		client = new AndroidClient(game);
//		game.setUploader(new AndroidUploader(this));
//		game.setPlatformResolver(new AndroidResolver(this));
//		game.setConnector(client);
//		game.setTelephone(new AndroidPhone(this));
//		game.setFacebookConnector(new AndroidFBConnector(this));
//		initialize(game, cfg);
//
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		uiHelper.onPause();
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		uiHelper.onResume();
//	}
//
//	@Override
//	protected void onDestroy() {
//		if (isWifi)
//			unregisterReceiver(mWifiStateChangedReceiver);
//		android.os.Process.killProcess(android.os.Process.myPid());
//		uiHelper.onDestroy();
//	}
//
//	public int getNetworkSignalStrength() {
//		if (isWifi) {
//			return wifiManager.getConnectionInfo().getRssi();
//		}
//
//		return 0;
//	}
//
//	public void showNoConnectionDialog(Context ctx1) {
//		final Context ctx = ctx1;
//		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
//		builder.setCancelable(true);
//		builder.setMessage("Không có kết nối mạng!");
//		builder.setPositiveButton("Cài đặt",
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						ctx.startActivity(new Intent(
//								Settings.ACTION_WIRELESS_SETTINGS));
//					}
//				});
//
//		builder.setNegativeButton("Đóng",
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						Gdx.app.exit();
//						return;
//					}
//				});
//
//		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//			public void onCancel(DialogInterface dialog) {
//				Gdx.app.exit();
//				return;
//			}
//		});
//
//		builder.show();
//	}
//
//	public void showFileDialog() {
//		fileDialog.showDialog();
//	}
//
//	private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
//		public int singalStenths = 0;
//		public int quality = 0;
//
//		@Override
//		public void onSignalStrengthsChanged(SignalStrength signalStrength) {
//			super.onSignalStrengthsChanged(signalStrength);
//			singalStenths = signalStrength.getGsmSignalStrength();
//
//			if (singalStenths > 30) {
//				quality = Constants.GOOD;
//				client.checkInternetConnection(false);
//			} else if (singalStenths > 20 && singalStenths <= 30) {
//				quality = Constants.FAIR;
//				client.checkInternetConnection(true);
//			} else if (singalStenths <= 20) {
//				quality = Constants.SLOW;
//				client.checkInternetConnection(true);
//			}
//
//			game.notifyChangedNetworkQuality(quality);
//
//		}
//
//	};
//
//	private BroadcastReceiver mWifiStateChangedReceiver = new BroadcastReceiver() {
//		int quality = 0;
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			// TODO Auto-generated method stub
//			WifiInfo info = wifiManager.getConnectionInfo();
//			int dBm = info.getRssi();
//
//			// dBm to Quality:
//			if (dBm <= -100)
//				quality = 0;
//			else if (dBm >= -50)
//				quality = 100;
//			else
//				quality = 2 * (dBm + 100);
//
//			// Low quality:: quality <= 10(%)
//			if (quality <= 10) {
//				quality = Constants.SLOW;
//				client.checkInternetConnection(true);
//			} else if (quality > 10 && quality <= 60) {
//				quality = Constants.FAIR;
//				client.checkInternetConnection(true);
//			} else if (quality > 60) {
//				quality = Constants.GOOD;
//				client.checkInternetConnection(false);
//			}
//
//			game.notifyChangedNetworkQuality(quality);
//		}
//	};
//
//	public void sendSms(String phoneNumber, String content) {
//		// smsManager.sendTextMessage(phoneNumber, null, content, sentPI,
//		// deliveredPI);
//		Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",
//				phoneNumber, null));
//		sendIntent.putExtra("sms_body", content);
//		startActivity(sendIntent);
//	}
//
//	public void callNumber(String phoneNumber) {
//		// String url = "tel:" + phoneNumber;
//		// Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
//		// startActivity(intent);
//	}
//
//	public void openUrl(String url) {
//		Intent i = new Intent(Intent.ACTION_VIEW);
//		i.setData(Uri.parse(url));
//		startActivity(i);
//	}
//
//	private final static String SECRET_KEY = "a8131d9f5ce4fdb783920837210e4e08";
//	private int ACTIVITY_ID_PICK_PHOTO = 42;
//	private int maxWidth = 512;
//	private int maxHeight = 512;
//	private int minWidth = 100;
//	private int minHeight = 100;
//	private HttpClient mHttpClient;
//	private Uploader.ResponseListener uploadResponseListener;
//
//	// Call the activity for select photo into the gallery
//	public void selectImageAndUploadToServer(ResponseListener listener) {
//		uploadResponseListener = listener;
//
//		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//		photoPickerIntent.setType("image/*");
//		startActivityForResult(photoPickerIntent, ACTIVITY_ID_PICK_PHOTO);
//	}
//
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		uiHelper.onSaveInstanceState(outState);
//		SharedPreferences prefs = this.getSharedPreferences(
//				"com.gdx.hd.casino", Context.MODE_PRIVATE);
//		prefs.edit().putString("username", Info.USER_NAME).commit();
//	}
//
//	// check the return of the result
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		// check th id of the result
//		if (requestCode == ACTIVITY_ID_PICK_PHOTO) {
//			selectPhotoControl(data);
//		} else if (Session.getActiveSession() != null) {
//			Session.getActiveSession().onActivityResult(this, requestCode,
//					resultCode, data);
//
//			uiHelper.onActivityResult(requestCode, resultCode, data,
//					new FacebookDialog.Callback() {
//						@Override
//						public void onError(
//								FacebookDialog.PendingCall pendingCall,
//								Exception error, Bundle data) {
//							Log.e("Activity", String.format("Error: %s",
//									error.toString()));
//						}
//
//						@Override
//						public void onComplete(
//								FacebookDialog.PendingCall pendingCall,
//								Bundle data) {
//							Log.i("Activity", "Success!");
//						}
//					});
//		}
//	}
//
//	Handler loginFBHandler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			Toast.makeText(MainActivity.this,
//					"Đăng nhập tài khoản facebook...", Toast.LENGTH_LONG)
//					.show();
//		}
//
//	};
//
//	Handler loginFBSuccessHandler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			Toast.makeText(MainActivity.this,
//					"Đăng nhập thành công, chào mừng " + name + "!",
//					Toast.LENGTH_SHORT).show();
//		}
//
//	};
//
//	public void likeFacebook(String link, OnActionListener listener) {
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.setData(Uri.parse(link));
//		startActivity(intent);
//	}
//
//	public void downloadApp(String link, OnActionListener listener) {
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.setData(Uri.parse(link));
//		startActivity(intent);
//	}
//	
//	String shareName = "Bài lá Ella";
//	String[] shareDescriptions = new String[] {"Bài hay, tiền ngay", "Miễn phí, tặng xu", "Chơi ngay không cần mất phí", 
//											   "Chơi tiến lên miền Nam nào!", "Tá lả cực hay", "Tá lả bắt gà", 
//											   "Mậu Binh thả ga,  Poker cực đã"};
//	String picture = "https://lh6.ggpht.com/0cxnZpMvI0PstCD05leMVMrsIHkZ_dZHskfO-LjWPDeISiVEMWKvPxvh5Aiq0P1XoKA=h900-rw";
//
//	public void shareFacebook(final String link, OnActionListener listener) {
//		if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
//				FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
//			// Publish the post using the Share Dialog
//			Random random = new Random();
//			int index = random.nextInt(shareDescriptions.length);
//			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
//					this).setLink(link)
//					.setDescription(shareDescriptions[index])
//					.setPicture(picture)
//					.setName(shareName)
//					.build();
//			uiHelper.trackPendingDialogCall(shareDialog.present());
//
//		} else {
//			// Fallback. For example, publish the post using the Feed Dialog
//			publishFeedDialog(link);
//		}
//	}
//
//	private void publishFeedDialog(String link) {
//		Random random = new Random();
//		int index = random.nextInt(shareDescriptions.length);
//		Bundle params = new Bundle();
//		params.putString("name", shareName);
//		params.putString("description", shareDescriptions[index]);
//		params.putString("link", link);
//		params.putString("picture", picture);
//
//		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(this,
//				Session.getActiveSession(), params)).setOnCompleteListener(
//				new OnCompleteListener() {
//
//					@Override
//					public void onComplete(Bundle values,
//							FacebookException error) {
//						if (error != null)
//							error.printStackTrace();
//					}
//
//				}).build();
//		feedDialog.show();
//	}
//
//	public void rateApp(String link, OnActionListener listener) {
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.setData(Uri.parse(link));
//		startActivity(intent);
//	}
//
//	public void performFacebookLogin(final OnLoginListener listener) {
//		Log.d("FACEBOOK", "performFacebookLogin");
//		if (Session.getActiveSession() != null && Session.getActiveSession().isOpened()) {
//			runOnUiThread(new Runnable() {
//				
//				@Override
//				public void run() {
//					Request.newMeRequest(Session.getActiveSession(),
//							new GraphUserCallback() {
//								@Override
//								public void onCompleted(GraphUser user,
//										Response response) {
//									if (user != null) {
//										String facebookID = user.getId();
//										String firstName = user.getFirstName();
//										String lastName = user.getLastName();
//										name = user.getName();
//										if (facebookInfo == null)
//											facebookInfo = new HashMap<String, String>();
//										facebookInfo.put("id", facebookID);
//										facebookInfo.put("first_name",
//												firstName);
//										facebookInfo.put("last_name", lastName);
//										facebookInfo.put("name", name);
//										facebookInfo
//												.put("avatar",
//														"https://graph.facebook.com/"
//																+ facebookID
//																+ "/picture?width=100&height=100");
//										facebookInfo.put("access_token", Session.getActiveSession().getAccessToken());
//
//										loginFBSuccessHandler
//												.sendEmptyMessage(0);
//
//										listener.onComplete(facebookInfo);
//									} else {
//										listener.onError();
//									}
//								}
//							}).executeAsync();
//				}
//			});
//		} else {
//			loginFBHandler.sendEmptyMessage(0);
//			boolean allowUI = false;
//			Session session = new Builder(this).build();
//			if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState())) {
//				allowUI = false;
//			} else {
//				allowUI = true;
//			}
//			Session.openActiveSession(this, allowUI, new Session.StatusCallback() {
//				@Override
//				public void call(final Session session, SessionState state,
//						Exception exception) {
//					if (exception != null) {
//						listener.onError();
//					}
//					if (session.isOpened()) {
//						Request.newMeRequest(session,
//								new GraphUserCallback() {
//									@Override
//									public void onCompleted(GraphUser user,
//											Response response) {
//										if (user != null) {
//											String facebookID = user.getId();
//											String firstName = user.getFirstName();
//											String lastName = user.getLastName();
//											name = user.getName();
//											if (facebookInfo == null)
//												facebookInfo = new HashMap<String, String>();
//											facebookInfo.put("id", facebookID);
//											facebookInfo.put("first_name",
//													firstName);
//											facebookInfo.put("last_name", lastName);
//											facebookInfo.put("name", name);
//											facebookInfo
//													.put("avatar",
//															"https://graph.facebook.com/"
//																	+ facebookID
//																	+ "/picture?width=100&height=100");
//											facebookInfo.put("access_token",
//													session.getAccessToken());
//
//											loginFBSuccessHandler
//													.sendEmptyMessage(0);
//
//											listener.onComplete(facebookInfo);
//										} else {
//											listener.onError();
//										}
//									}
//								}).executeAsync();
//					}
//				}
//			});
//		}
//	}
//
//	private boolean isSubsetOf(Collection<String> subset,
//			Collection<String> superset) {
//		for (String string : subset) {
//			if (!superset.contains(string)) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	// Working data
//	private void selectPhotoControl(Intent data) {
//		// check if any photo is selected
//		if (data == null)
//			return;
//		// get the uri of the picture selected
//		Uri photoUri = data.getData();
//		if (photoUri != null) {
//			// decode the Uri
//			String[] filePathColumn = { MediaStore.Images.Media.DATA };
//			Cursor cursor = getContentResolver().query(photoUri,
//					filePathColumn, null, null, null);
//			cursor.moveToFirst();
//			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//			// get the uri of the image
//			String filePath = cursor.getString(columnIndex);
//			cursor.close();
//			// get the image in the bitmap and resize the image
//			try {
//				Bitmap bp = resize(filePath);
//				if (bp != null) {
//					HttpResponse res = postImage(bp, filePath);
//					if (res != null) {
//						BufferedReader reader = new BufferedReader(
//								new InputStreamReader(res.getEntity()
//										.getContent(), "UTF-8"));
//						String sResponse;
//						StringBuilder s = new StringBuilder();
//						while ((sResponse = reader.readLine()) != null) {
//							s = s.append(sResponse);
//						}
//						System.out.println("server response:: " + s.toString());
//						if (uploadResponseListener != null) {
//							uploadResponseListener.response(s.toString());
//							uploadResponseListener = null;
//						}
//					}
//				}
//			} catch (ClientProtocolException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public HttpResponse postImage(Bitmap bp, String uristr)
//			throws ClientProtocolException, IOException {
//		if (mHttpClient == null)
//			mHttpClient = new DefaultHttpClient();
//		SharedPreferences prefs = this.getSharedPreferences(
//				"com.gdx.hd.casino", Context.MODE_PRIVATE);
//		String username = prefs.getString("username", "");
//		String params = "game=%game%&username=%username%";
//		params = params.replace("%game%", "wiicards");
//		params = params.replace("%username%", username);
//		String accessKeyServer = "";
//		try {
//			accessKeyServer = MD5Good.hash(SECRET_KEY + params);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		// initialization of the postrequest
//		HttpPost httpPost = new HttpPost(upLoadServerUri);
//
//		if (bp != null) {
//			MultipartEntity entity = new MultipartEntity(
//					HttpMultipartMode.BROWSER_COMPATIBLE,
//					"------CustomBoundary", null);
//			entity.addPart("game", new StringBody("wiicards"));
//			entity.addPart("username", new StringBody(username));
//			entity.addPart("access_key", new StringBody(accessKeyServer));
//			// create the bytes array for send the image
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			// if you want to compress the image -> write the result into bos
//			bp.compress(CompressFormat.PNG, 100, bos);
//			// get the filename of the image
//			String filename = uristr.substring(uristr.lastIndexOf("/") + 1,
//					uristr.length());
//			// put the picture into the body of this part
//			FormBodyPart fbp = new FormBodyPart("file", new ByteArrayBody(
//					bos.toByteArray(), "image/png", filename));
//			// add the part to the entity
//			entity.addPart(fbp);
//			httpPost.setEntity(entity);
//		}
//		// set the entity into the request
//		// execute the request
//		return exec(httpPost);
//	}
//
//	protected synchronized HttpResponse exec(HttpRequestBase base)
//			throws ClientProtocolException, IOException {
//		if (base != null)
//			// Execute the request
//			return mHttpClient.execute(base);
//		else
//			return null;
//	}
//
//	private Bitmap resize(String path) {
//		// create the options
//		BitmapFactory.Options opts = new BitmapFactory.Options();
//
//		// just decode the file
//		opts.inJustDecodeBounds = true;
//		Bitmap bp = BitmapFactory.decodeFile(path, opts);
//
//		// get the original size
//		int orignalHeight = opts.outHeight;
//		int orignalWidth = opts.outWidth;
//		// initialization of the scale
//		int resizeScale = 1;
//		// get the good scale
//		if (orignalWidth < minWidth || orignalHeight < minHeight) {
//			Toast.makeText(this, "Ảnh phải có kích thước tối thiểu 100x100",
//					Toast.LENGTH_LONG).show();
//			return null;
//		} else if (orignalWidth > maxWidth || orignalHeight > maxHeight) {
//			final int heightRatio = Math.round((float) orignalHeight
//					/ (float) maxHeight);
//			final int widthRatio = Math.round((float) orignalWidth
//					/ (float) maxWidth);
//			resizeScale = heightRatio < widthRatio ? heightRatio : widthRatio;
//		}
//		// put the scale instruction (1 -> scale to (1/1); 8-> scale to 1/8)
//		opts.inSampleSize = resizeScale;
//		opts.inJustDecodeBounds = false;
//		// get the futur size of the bitmap
//		int bmSize = (orignalWidth / resizeScale)
//				* (orignalHeight / resizeScale) * 4;
//		// check if it's possible to store into the vm java the picture
//		if (Runtime.getRuntime().freeMemory() > bmSize) {
//			// decode the file
//			bp = BitmapFactory.decodeFile(path, opts);
//		} else {
//			Toast.makeText(this, "Không đủ bộ nhớ", Toast.LENGTH_LONG).show();
//			return null;
//		}
//		return bp;
//	}
//
//	public String getDeviceId() {
//		 return telephonyManager.getDeviceId();
//	 }
//}