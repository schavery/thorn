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
	private String absolutePath;
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

	public static Gif find(long id) {
		return db.getGif(id);
	}

	public static void destroy(long id) {
		Gif gif = Gif.find(id);
		File gifFile = new File(gif.getAbsolutePath());
		gifFile.delete();
		db.deleteGif(id);
	}

	// METHODS

	public Gif(long id, String uri) {
		this.id = id;
		this.uri = uri;
		this.absolutePath = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
				+ "/thorn/tmp/" + Uri.parse(uri).getLastPathSegment();
	}

	public void destroy() {
		File gifFile = new File(absolutePath);
		gifFile.delete();
		db.deleteGif(id);
	}

	public long getId() {
		return id;
	}

	public String getUri() {
		return uri;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	@Override
	public String toString() {
		return uri;
	}

}
