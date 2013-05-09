/**
 * @author Zachary Thompson
 * @author Steve Avery
 */

package edu.santarosa.szcgat.thorn;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class Profile extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.profile_gif, container, false);
		WebView webView = (WebView) view.findViewById(R.id.profile_gif);

		int index = this.getArguments().getInt("gif_index");

		webView.loadUrl("file://"
				+ Gallery.getGifs().get(index).getPath());

		return view;
	}

}
