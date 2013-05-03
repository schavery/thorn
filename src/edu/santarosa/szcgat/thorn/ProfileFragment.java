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
import android.widget.ImageView;

public class ProfileFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.profile_image, container, false);
		ImageView imageView = (ImageView) view.findViewById(R.id.profile_image);

		int index = this.getArguments().getInt("gif_index");
		imageView.setImageURI(Uri.parse(GalleryFragment.getGifs().get(index)
				.getUri()));

		return view;
	}

}
