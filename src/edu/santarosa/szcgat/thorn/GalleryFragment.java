/**
 * @author Zachary Thompson
 * @author Steve Avery
 */

package edu.santarosa.szcgat.thorn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GalleryFragment extends Fragment {

	private static List<Integer> itemIds = null;
	private static int itemCount = 0;

	public GalleryFragment() {
		refreshIdsAndCount();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		GridView gridView = (GridView) inflater.inflate(R.layout.gallery_grid,
				container, false);
		GalleryArrayAdapter adapter = new GalleryArrayAdapter(
				container.getContext(), R.layout.gallery_item, itemIds);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),
						ProfileAndCamera.class);
				Log.d("thorn", "onItemClick pos =" + position);
				intent.putExtra("image_pos", position + 1);
				getActivity().startActivity(intent);
			}
		});

		return gridView;
	}

	public static List<Integer> getItemIds() {
		if (itemIds == null) {
			refreshIdsAndCount();
		}

		return itemIds;
	}

	public static int getItemCount() {
		if (itemIds == null) {
			refreshIdsAndCount();
		}

		return itemCount;
	}

	public class GalleryArrayAdapter extends ArrayAdapter<Integer> {

		public GalleryArrayAdapter(Context context, int textViewResourceId,
				List<Integer> objects) {
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

			ImageView imageView = (ImageView) v.findViewById(R.id.gallery_item);
			imageView.setImageResource(getItem(position));

			return v;
		}
	}

	private static void refreshIdsAndCount() {
		itemIds = getImageIds();
		itemCount = itemIds.size();
	}

	private static List<Integer> getImageIds() {
		List<Integer> idArray = new ArrayList<Integer>();

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
