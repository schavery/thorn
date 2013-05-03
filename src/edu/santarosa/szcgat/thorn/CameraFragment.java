/**
 * @author Zachary Thompson
 * @author Steve Avery
 */

package edu.santarosa.szcgat.thorn;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CameraFragment extends Fragment {
	public static final int NEW_VIDEO = 0;
	public static final String VIDEO_PATH = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
			+ "/thorn/tmp/";
	public static final String GIF_PATH = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
			+ "/thorn/";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_camera, container,
				false);
		return rootView;
	}

	public static void openCamera(Activity activity) {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);// needs to be
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputMediaFileUri());// dynamic
		activity.startActivityForResult(intent, NEW_VIDEO);
	}

	public static class CameraListener implements
			ViewPager.OnPageChangeListener {

		private Activity activity;

		public CameraListener(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void onPageSelected(int pos) {
			if (pos == 0) {
				openCamera(activity);
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

	}

	/** Create a file Uri */
	private static Uri getOutputMediaFileUri() {
		return Uri.fromFile(getOutputMediaFile());
	}

	/** Create a File */
	private static File getOutputMediaFile() {

		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Log.d("thorn", "file system is not mounted");
			return null; // not exception safe
		}
		else {
			File thornDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
					"thorn");
			File thornTmpDir = new File(thornDir.getPath(), "tmp");
			File nomedia = new File(thornDir.getPath() + File.separator
					+ ".nomedia");

			// Create the storage directory if it does not exist
			if (!thornDir.exists()) {
				if (!thornDir.mkdirs()) {
					Log.d("thorn", "failed to create directory");
					return null; // not exception safe
				}

				// Create internal files since they won't exist at this point
				try {
					nomedia.createNewFile();
					thornTmpDir.mkdir();
				}
				catch (IOException e) {
					Log.d("thorn", "failed to create .nomedia or tmp/");
				}
			}

			// Create a media file name
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US)
					.format(new Date());
			File mediaFile = new File(thornTmpDir.getPath() + File.separator
					+ timeStamp + ".mp4");
			return mediaFile;
		}
	}
}
