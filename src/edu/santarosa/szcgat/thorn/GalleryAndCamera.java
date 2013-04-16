package edu.santarosa.szcgat.thorn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;

public class GalleryAndCamera extends FragmentActivity implements
		ViewPager.OnPageChangeListener {

	GalleryPagerAdapter mPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery_and_camera);

		// Create the adapter that will return a fragment for the
		// primary sections of the app.
		mPagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.gallery_pager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);

		mViewPager.setCurrentItem(1);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mViewPager.setCurrentItem(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

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

	public void openProfile(View v) {
		GalleryAndCamera.this.startActivity(new Intent(GalleryAndCamera.this,
				ProfileAndCamera.class));
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int pos) {
		if (pos == 0) {
			CameraFragment.openCamera(this);
		}
	}

}
