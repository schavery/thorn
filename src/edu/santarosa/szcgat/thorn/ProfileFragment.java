package edu.santarosa.szcgat.thorn;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ProfileFragment extends Fragment {

	private ArrayList<Integer> imageIds;

	public ProfileFragment() {
		imageIds = getImageIds();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		int position = this.getArguments().getInt("pos");

		ImageView imageView = new ImageView(container.getContext());
		imageView.setImageResource(imageIds.get(position));

		return imageView;
	}

	private ArrayList<Integer> getImageIds() {
		ArrayList<Integer> idArray = new ArrayList<Integer>();

		Field[] fields = R.raw.class.getFields();
		for (Field field : fields) {
			try {
				idArray.add(field.getInt(field));
			}
			catch (IllegalArgumentException e) {
				Log.e("thorn", "IllegalArgumentException");
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				Log.e("thorn", "IllegalAccessException");
				e.printStackTrace();
			}
		}
		return idArray;
	}
}
