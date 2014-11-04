package com.vincestyling.recyclerview_practice;

import android.content.Context;
import android.widget.ImageView;
import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.RequestQueue;
import com.duowan.mobile.netroid.cache.BitmapImageCache;
import com.duowan.mobile.netroid.cache.DiskCache;
import com.duowan.mobile.netroid.image.NetworkImageView;
import com.duowan.mobile.netroid.stack.HurlStack;
import com.duowan.mobile.netroid.toolbox.BasicNetwork;
import com.duowan.mobile.netroid.toolbox.ImageLoader;
import com.vincestyling.recyclerview_practice.entity.Data;
import com.vincestyling.recyclerview_practice.entity.Game;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.util.List;

public class Netroid {
	// http parameters
	public static final int HTTP_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 5MB
	public static final int HTTP_MEMORY_CACHE_SIZE = 6 * 1024 * 1024; // 6MB
	public static final String HTTP_DISK_CACHE_DIR_NAME = "recyclerview_practice";

	/** The queue. :-) */
	private static RequestQueue mRequestQueue;

	/** The image loader. :-) */
	private static ImageLoader mImageLoader;

	/** Nothing to see here. */
	private Netroid() {}

	public static void init(Context ctx) {
		if (mRequestQueue == null) {
			mRequestQueue = new RequestQueue(
					new BasicNetwork(new HurlStack(HTTP_DISK_CACHE_DIR_NAME, null), HTTP.UTF_8), 2, new DiskCache(
					new File(ctx.getCacheDir(), HTTP_DISK_CACHE_DIR_NAME), HTTP_DISK_CACHE_SIZE));

			mImageLoader = new SelfImageLoader(mRequestQueue, new BitmapImageCache(HTTP_MEMORY_CACHE_SIZE));

			mRequestQueue.start();
		} else {
			throw new IllegalStateException("initialized");
		}
	}

	public static RequestQueue get() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}

	private static ImageLoader getImageLoader() {
		if (mImageLoader != null) {
			return mImageLoader;
		} else {
			throw new IllegalStateException("ImageLoader not initialized");
		}
	}

    public static void getGameList(Listener<List<Data>> listener) {
        get().add(new GameListRequest("http://vince-styling.github.io/recyclerview-practice/game_list.json", listener));
    }

	public static void displayImage(String url, NetworkImageView imageView) {
		displayImage(url, imageView, 0, 0);
	}

	public static void displayImage(String url, NetworkImageView imageView, int defaultImageResId, int errorImageResId) {
		imageView.setDefaultImageResId(defaultImageResId);
		imageView.setErrorImageResId(errorImageResId);
		imageView.setImageUrl(url, getImageLoader());
	}

	public static void displayImage(String url, ImageView imageView) {
		displayImage(url, imageView, 0, 0);
	}

	public static void displayImage(String url, ImageView imageView, int defaultImageResId, int errorImageResId) {
		ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, defaultImageResId, errorImageResId);
		getImageLoader().get(url, listener, 0, 0);
	}

}
