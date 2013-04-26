package edu.santarosa.szcgat.thorn;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ThornDatabase extends SQLiteOpenHelper {

	private static ThornDatabase thornDB = null;

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "thorn.db";
	private static final String TABLE_GIF = "uris";

	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_GIF_URI = "gif_uri";

	private ThornDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static ThornDatabase getInstance(Context context) {
		if (thornDB == null) {
			thornDB = new ThornDatabase(context);
		}
		return thornDB;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String DATABASE_CREATE = "create table " + TABLE_GIF + "(" + COLUMN_ID
				+ " integer primary key autoincrement, " + COLUMN_GIF_URI
				+ " text not null);";
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("thorn", "Upgrading DB from " + oldVersion + " to " + newVersion);
		db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_GIF);
		onCreate(db);
	}

	public Gif addGif(String uri) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues newGif = new ContentValues();
		newGif.put(ThornDatabase.COLUMN_GIF_URI, uri);

		long id = db.insert(TABLE_GIF, null, newGif);
		db.close();

		return new Gif(id, uri);
	}

	public void deleteGif(Gif gif) {
		SQLiteDatabase db = this.getWritableDatabase();
		long id = gif.getId();
		db.delete(TABLE_GIF, COLUMN_ID + " = " + id, null);

	}

	public List<Gif> getAllGifs() {
		String[] allColumns = { COLUMN_ID, COLUMN_GIF_URI };

		SQLiteDatabase db = this.getWritableDatabase();

		List<Gif> gifUris = new ArrayList<Gif>();
		Cursor cursor = db.query(TABLE_GIF, allColumns, null, null, null, null,
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			gifUris.add(cursorToGif(cursor));
			cursor.moveToNext();
		}

		cursor.close();
		db.close();
		return gifUris;
	}

	private Gif cursorToGif(Cursor cursor) {
		Gif gifUri = new Gif(cursor.getLong(0), cursor.getString(1));
		return gifUri;
	}

}