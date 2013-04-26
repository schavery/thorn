/**
 * @author Zachary Thompson
 * @author Steve Avery
 */

package edu.santarosa.szcgat.thorn;

import java.util.ArrayList;
import java.util.List;

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

public class GalleryFragment extends Fragment {

	private GalleryArrayAdapter adapter;
	private static List<Gif> gifs = null;
	private static int gifCount = 0;
	private List<View> highlightedViews;
	private List<Gif> selectedForDelete;

	public GalleryFragment() {
		updateGifsAndCount();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ListView view = (ListView) inflater.inflate(R.layout.gallery_list,
				container, false);

		adapter = new GalleryArrayAdapter(container.getContext(),
				R.layout.gallery_text, gifs);
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
				if (highlightedViews.contains(view)) {
					highlightedViews.remove(view);
					selectedForDelete.remove(gifs.get(position));
					view.setBackgroundColor(Color.WHITE);
				}
				else {
					highlightedViews.add(view);
					selectedForDelete.add(gifs.get(position));
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
		if (highlightedViews.isEmpty()) {
			menu.setGroupEnabled(R.id.gallery_menu, false);
			menu.setGroupVisible(R.id.gallery_menu, false);
		}

		else {
			menu.setGroupEnabled(R.id.gallery_menu, true);
			menu.setGroupVisible(R.id.gallery_menu, true);
			menu.findItem(R.id.total_selected).setTitle(
					highlightedViews.size() + " selected");
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case (R.id.delete):
			for (Gif gif : selectedForDelete) {
				gif.destroy();
				update(gif, Gif.DESTROY);
			}
			resetDelete();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void resetDelete() {
		highlightedViews = new ArrayList<View>();
		selectedForDelete = new ArrayList<Gif>();
		getActivity().invalidateOptionsMenu();
	}

	@Override
	public void onResume() {
		super.onResume();
		resetDelete();
	}

	@Override
	public void onPause() {
		super.onPause();

		for (View view : highlightedViews) {
			view.setBackgroundColor(Color.WHITE);
		}
	}

	public void update(Gif gif, int action) {
		updateGifsAndCount();
		switch (action) {
		case Gif.CREATE:
			adapter.add(gif);
			break;
		case Gif.DESTROY:
			adapter.remove(gif);
			break;
		}
		adapter.notifyDataSetChanged();
	}

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

	private static void updateGifsAndCount() {
		gifs = Gif.all();
		gifCount = gifs.size();
	}

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
