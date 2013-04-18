/**
 * @author Zachary Thompson
 * @author Steve Avery
 */

package edu.santarosa.szcgat.thorn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CameraFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_camera, container,
				false);
		return rootView;
	}

	public static void openCamera(Activity activity) {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10); // needs to be
																// dynamic
		activity.startActivityForResult(intent, 0);
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

}
