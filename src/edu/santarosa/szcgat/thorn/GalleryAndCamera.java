/**
 * @author Zachary Thompson
 * @author Steve Avery
 */

package edu.santarosa.szcgat.thorn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class GalleryAndCamera extends FragmentActivity {

	private GalleryPagerAdapter mPagerAdapter;
	private ViewPager mViewPager;

	// OBJECT METHODS

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_gallery_and_camera);
		Gif.setContext(this);

		mPagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.gallery_pager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new CameraFragment.CameraListener(
				this));

		mViewPager.setCurrentItem(1);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mViewPager.setCurrentItem(1);
	}

	@Override
	protected void onActivityResult(int reqCode, int resCode, Intent intent) {
		super.onActivityResult(reqCode, resCode, intent);

		if (reqCode == CameraFragment.NEW_VIDEO) {
			if (resCode == RESULT_OK) {
				Gif.create(intent.getDataString());
				getGalleryFragment().update();
			}

		}
	}

	public GalleryFragment getGalleryFragment() {
		GalleryFragment gallery = (GalleryFragment) getSupportFragmentManager()
				.findFragmentByTag(getFragmentTag(1));
		return gallery;
	}

	// PRIVATE METHODS

	private String getFragmentTag(int pos) {
		return "android:switcher:" + R.id.gallery_pager + ":" + pos;
	}

	// HELPER CLASSES

	public class GalleryPagerAdapter extends FragmentPagerAdapter {

		public GalleryPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment;
			if (position == 0) {
				fragment = new CameraFragment();
			}
			else {
				fragment = new GalleryFragment();
			}

			return fragment;
		}

		@Override
		public int getCount() {
			return 2;
		}

	}
}
