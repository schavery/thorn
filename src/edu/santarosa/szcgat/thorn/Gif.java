package edu.santarosa.szcgat.thorn;

import java.io.File;
import java.util.List;

import android.content.Context;

public class Gif {

	public static final int CREATE = 0;
	public static final int DESTROY = 1;

	private long id;
	private String uri;
	private String path;
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

	public static Gif create(String filename) {
		String path = CameraFragment.GIF_PATH + filename;
		String uri = "file://" + path;
		return db.addGif(uri, path);
	}

	public static Gif find(long id) {
		return db.getGif(id);
	}

	public static void destroy(long id) {
		Gif gif = Gif.find(id);
		File gifFile = new File(gif.getPath());
		gifFile.delete();
		db.deleteGif(id);
	}

	// METHODS

	public Gif(long id, String uri, String path) {
		this.id = id;
		this.uri = uri;
		this.path = path;
	}

	public void destroy() {
		File gifFile = new File(path);
		gifFile.delete();
		db.deleteGif(id);
	}

	public long getId() {
		return id;
	}

	public String getUri() {
		return uri;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return uri;
	}

}
