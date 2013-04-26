package edu.santarosa.szcgat.thorn;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

public class Gif {

	public static final int CREATE = 0;
	public static final int DESTROY = 1;

	private long id;
	private String uri;
	private static Context context;
	private static ThornDatabase db = null;

	// STATIC METHODS

	public static void setContext(Context curContext) {
		context = curContext;
		db = ThornDatabase.getInstance(context);
	}

	public static List<Gif> all() {
		return db.getAllGifs();
	}

	public static Gif create(String uri) {
		return db.addGif(uri);
	}

	public static void destroy(Gif gif) {
		String gifPath = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
				+ "/thorn/" + Uri.parse(gif.getUri()).getLastPathSegment();
		File gifFile = new File(gifPath);
		gifFile.delete();
		db.deleteGif(gif);
	}

	// METHODS

	public Gif(long id, String uri) {
		this.id = id;
		this.uri = uri;
	}

	public void destroy() {
		String gifPath = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
				+ "/thorn/tmp/" + Uri.parse(uri).getLastPathSegment();
		File gifFile = new File(gifPath);
		gifFile.delete();
		db.deleteGif(this);
	}

	public long getId() {
		return id;
	}

	public String getUri() {
		return uri;
	}

	@Override
	public String toString() {
		return uri;
	}

}
