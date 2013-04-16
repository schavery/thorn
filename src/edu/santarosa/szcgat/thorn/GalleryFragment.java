package edu.santarosa.szcgat.thorn;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

public class GalleryFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		GridView gridView = new GridView(container.getContext());
		GalleryArrayAdapter adapter = new GalleryArrayAdapter(
				container.getContext(), R.layout.gallery_item, getImageIds());
		gridView.setAdapter(adapter);

		return gridView;
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

	public class GalleryArrayAdapter extends ArrayAdapter<Integer> {

		public GalleryArrayAdapter(Context context, int textViewResourceId,
				ArrayList<Integer> objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;

			if (v == null) {
				LayoutInflater inflater = (LayoutInflater) getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.gallery_item, null);
			}

			ImageButton ib = (ImageButton) v.findViewById(R.id.gallery_item);
			ib.setImageResource(getItem(position));

			return v;
		}
	}
}
