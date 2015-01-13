package utils.download;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.utils.Pool;

public class DownloadManager {
	Pool<ImgDownloader>				imgDownloadPool;
	HashMap<String, ImgDownloader>	downloads	= new HashMap<String, ImgDownloader>();
	private static DownloadManager	INSTANCE;
	private DownloadManager() {
		super();
		imgDownloadPool = new Pool<ImgDownloader>(5, 5) {
			@Override
			protected ImgDownloader newObject() {
				return new ImgDownloader();
			}
		};
	}

	public static DownloadManager getInstance() {
		if (INSTANCE == null)
			INSTANCE = new DownloadManager();
		return INSTANCE;
	}

	public void update() {
		ArrayList<ImgDownloader> list = new ArrayList<ImgDownloader>(
				downloads.values());
		for (ImgDownloader dl : list) {
			dl.update();
		}
	}

	public ImgDownloader getDownLoader(String className) {
		if (downloads.containsKey(className)) {
			return downloads.get(className);
		}
		ImgDownloader downloader = new ImgDownloader();
		downloads.put(className, downloader);
		return downloader;
	}

	public void removeDownLoader(String className) {
		if (downloads.containsKey(className)) {
			ImgDownloader downloader = downloads.get(className);
			imgDownloadPool.free(downloader);
			int size = downloads.size();
			downloads.remove(className);
			if (size == downloads.size())
				throw new NullPointerException();
		}
	}
}
