/**
 * @author Zachary Thompson
 * @author Steve Avery
 */

package edu.santarosa.szcgat.thorn;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class ProfileFragment extends Fragment {
	VideoView mVideoView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.profile_video, container, false);
		mVideoView = (VideoView) view.findViewById(R.id.profile_video);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		int index = this.getArguments().getInt("gif_index");
		mVideoView.setVideoURI(Uri.parse(GalleryFragment.getGifs().get(index)
				.getUri()));
		mVideoView.setMediaController(new MediaController(getActivity()));
		mVideoView.requestFocus();
		mVideoView.start();
	}

	@Override
	public void onPause() {
		super.onPause();
		mVideoView.stopPlayback();
	}
}
