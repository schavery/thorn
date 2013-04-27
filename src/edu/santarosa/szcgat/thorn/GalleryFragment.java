/**
 * @author Zachary Thompson
 * @author Steve Avery
 */

package edu.santarosa.szcgat.thorn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GalleryFragment extends Fragment {

	private static List<Gif> gifs = null;
	private static int gifCount = 0;

	private GalleryArrayAdapter adapter;
	private Map<Long, View> selectedGifs;

	// STATIC METHODS

	public static List<Gif> getGifs() {
		if (gifs == null) {
			updateGifsAndCount();
		}
		return gifs;
	}

	public static int getGifCount() {
		if (gifs == null) {
			updateGifsAndCount();
		}
		return gifCount;
	}

	// OBJECT METHODS

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		gifs = Gif.all();
		gifCount = gifs.size();
		adapter = new GalleryArrayAdapter(container.getContext(),
				R.layout.gallery_text, gifs);
		selectedGifs = new HashMap<Long, View>();

		ListView view = (ListView) inflater.inflate(R.layout.gallery_list,
				container, false);

		view.setAdapter(adapter);

		view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),
						ProfileAndCamera.class);
				intent.putExtra("gif_index", position);
				getActivity().startActivity(intent);
			}
		});

		view.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				long gifId = gifs.get(position).getId();

				if (selectedGifs.containsKey(gifId)) {
					selectedGifs.remove(gifId).setBackgroundColor(Color.WHITE);
				}
				else {
					selectedGifs.put(gifId, view);
					view.setBackgroundColor(Color.MAGENTA);
				}
				getActivity().invalidateOptionsMenu();
				return true;
			}
		});

		this.setHasOptionsMenu(true);

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.gallery, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (selectedGifs.isEmpty()) {
			menu.setGroupEnabled(R.id.gallery_menu, false);
			menu.setGroupVisible(R.id.gallery_menu, false);
		}

		else {
			menu.setGroupEnabled(R.id.gallery_menu, true);
			menu.setGroupVisible(R.id.gallery_menu, true);
			menu.findItem(R.id.total_selected).setTitle(
					selectedGifs.size() + " selected");
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case (R.id.delete):
			for (long gifId : selectedGifs.keySet()) {
				Gif.destroy(gifId);
			}
			update();
			resetDelete();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (gifCount == 0) {
			Toast.makeText(getActivity(), "Swipe left to create a video",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		resetDelete();
	}

	public void update() {
		updateGifsAndCount();
		adapter.clear();
		adapter.addAll(gifs);
	}

	// PRIVATE METHODS

	private void resetDelete() {
		for (View view : selectedGifs.values()) {
			view.setBackgroundColor(Color.WHITE);
		}
		selectedGifs = new HashMap<Long, View>();
		getActivity().invalidateOptionsMenu();
	}

	private static void updateGifsAndCount() {
		gifs = Gif.all();
		gifCount = gifs.size();
	}

	// HELPER CLASSES

	public class GalleryArrayAdapter extends ArrayAdapter<Gif> {

		public GalleryArrayAdapter(Context context, int textViewResourceId,
				List<Gif> objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;

			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.gallery_text, null);
			}

			TextView child = (TextView) view.findViewById(R.id.gallery_text);
			child.setText(getItem(position).getUri());

			return view;
		}
	}
}
