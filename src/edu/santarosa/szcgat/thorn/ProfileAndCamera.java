/**
 * @author Zachary Thompson
 * @author Steve Avery
 */

package edu.santarosa.szcgat.thorn;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class ProfileAndCamera extends FragmentActivity {

	ProfilePagerAdapter mPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_and_camera);

		// Create the adapter that will return a fragment the
		// primary sections of the app.
		mPagerAdapter = new ProfilePagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.profile_pager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new CameraFragment.CameraListener(
				this));

		mViewPager.setCurrentItem(getIntent().getExtras().getInt("image_pos"));
	}

	@Override
	protected void onResume() {
		super.onResume();
		mViewPager.setCurrentItem(getIntent().getExtras().getInt("image_pos"));
	}

	@Override
	protected void onPause() {
		super.onPause();
		int curPos = mViewPager.getCurrentItem();
		int resumePos = curPos != 0 ? curPos : 1;
		getIntent().putExtra("image_pos", resumePos);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class ProfilePagerAdapter extends FragmentPagerAdapter {

		public ProfilePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment;
			if (position == 0) {
				fragment = new CameraFragment();
			}
			else {
				fragment = new ProfileFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("pos", position - 1);
				fragment.setArguments(bundle);
			}

			return fragment;
		}

		@Override
		public int getCount() {
			return GalleryFragment.getItemCount() + 1;
		}

	}

}
