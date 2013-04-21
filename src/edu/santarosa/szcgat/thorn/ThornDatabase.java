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

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "thorn.db";
	private static final String DATABASE_TABLE = "uris";

	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_GIF_URI = "gif_uri";

	public ThornDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String DATABASE_CREATE = "create table " + DATABASE_TABLE + "("
				+ COLUMN_ID + " integer primary key autoincrement, "
				+ COLUMN_GIF_URI + " text not null);";
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("thorn", "Upgrading DB from " + oldVersion + " to " + newVersion);
		db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
		onCreate(db);
	}

	public void addGifUri(String uri) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues newGif = new ContentValues();
		newGif.put(ThornDatabase.COLUMN_GIF_URI, uri);

		db.insert(DATABASE_TABLE, null, newGif);
		db.close();
	}

	public List<GifUri> getAllGifUris() {
		String[] allColumns = { COLUMN_ID, COLUMN_GIF_URI };

		SQLiteDatabase db = this.getWritableDatabase();

		List<GifUri> gifUris = new ArrayList<GifUri>();
		Cursor cursor = db.query(DATABASE_TABLE, allColumns, null, null, null,
				null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			gifUris.add(cursorToGifUri(cursor));
			cursor.moveToNext();
		}

		cursor.close();
		db.close();
		return gifUris;
	}

	private GifUri cursorToGifUri(Cursor cursor) {
		GifUri gifUri = new GifUri(cursor.getLong(0), cursor.getString(1));
		return gifUri;
	}

}