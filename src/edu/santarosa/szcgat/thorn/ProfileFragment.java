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

//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore.Video;
//import android.util.Log;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.MediaController;
//import android.widget.VideoView;
//
//public class ProfileFragment extends Fragment implements
//		MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
//		MediaPlayer.OnErrorListener {
//
//	private Video mVideo;
//	private VideoView mVideoView;
//	// The video position
//	private int mPosition;
//	private Uri videoUri;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//
//		int position = this.getArguments().getInt("pos");
//		videoUri = Uri.parse(GalleryFragment.getGifs().get(position).getUri());
//
//		mVideoView = new VideoView(getActivity());
//
//		return mVideoView;
//	}
//
//	@Override
//	public void onPause() {
//		super.onPause();
//
//		// Pause the video if it is playing
//		if (mVideoView.isPlaying()) {
//			mVideoView.pause();
//		}
//
//		// Save the current video position
//		mPosition = mVideoView.getCurrentPosition();
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//
//		mVideoView.setOnCompletionListener(this);
//		mVideoView.setOnPreparedListener(this);
//		mVideoView.setOnErrorListener(this);
//		mVideoView.setKeepScreenOn(true);
//
//		// Initialize the media controller
//		MediaController mediaController = new MediaController(getActivity());
//		mediaController.setMediaPlayer(mVideoView);
//		// Set-up the video view
//		mVideoView.setMediaController(mediaController);
//		mVideoView.requestFocus();
//		mVideoView.setVideoURI(videoUri);
//
//		if (mVideoView != null) {
//			// Restore the video position
//			mVideoView.seekTo(mPosition);
//			mVideoView.requestFocus();
//		}
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//
//		// Clean-up
//		if (mVideoView != null) {
//			mVideoView.stopPlayback();
//			mVideoView = null;
//		}
//	}
//
//	@Override
//	public void onCompletion(MediaPlayer mediaPlayer) {
//		Log.e("VIDEO PLAY", "end video play");
//	}
//
//	@Override
//	public void onPrepared(MediaPlayer mediaPlayer) {
//		// Start the video view
//		mediaPlayer.start();
//		Log.e("VIDEO PLAY", "video ready for playback");
//	}
//
//	@Override
//	public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//		Log.e("VIDEO PLAY", "error: " + i);
//		return true;
//	}
//
//}
// import android.net.Uri;
// import android.os.Bundle;
// import android.support.v4.app.Fragment;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.VideoView;
//
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
