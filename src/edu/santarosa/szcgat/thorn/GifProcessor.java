/**
 * @author Zachary Thompson
 * @author Steve Avery
 */

package edu.santarosa.szcgat.thorn;

import java.io.File;
import java.io.IOException;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class GifProcessor extends IntentService {

	public GifProcessor() {
		super("GifProcessor");
	}

	/*
	 * The intention of the processor service is to handle the native side when
	 * processing the received videos and to issue the filename URI when a new
	 * video is being created.
	 * 
	 * and probably more stuff as we realize what's needed.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {

		String tempPath = intent.getData().getPath();
		String baseFilename = intent.getData().getLastPathSegment()
				.replaceFirst(".mp4", "");
		String gifPath = FileManager.THORN_PATH + File.separator + baseFilename
				+ ".gif";
		String thumbnailPath = FileManager.THUMBNAIL_PATH + File.separator
				+ baseFilename + ".jpg";
		// String pngPath = FileManager.TEMP_PATH + File.separator
		// + "output%05d.jpg";

		String rotateParam = getRotateParam(tempPath);

		String createGifCommand = FileManager.FFMPEG + " -i " + tempPath
				+ " -pix_fmt rgb24 -r 10 -s 320x240 " + rotateParam + gifPath;

		// String createGifCommand = FileManager.FFMPEG + " -i " + tempPath
		// + " -r 10 -s 320x240 " + rotateParam + pngPath;

		String createThumbnailCommand = FileManager.FFMPEG + " -i " + tempPath
				+ " -vcodec mjpeg -vframes 1 -an -f rawvideo -s 512x384 "
				+ rotateParam + thumbnailPath;

		try {

			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(createGifCommand);

			// BufferedReader reader = new BufferedReader(new InputStreamReader(
			// process.getErrorStream()));
			// int read;
			// char[] buffer = new char[4096];
			// StringBuffer output = new StringBuffer();
			// while ((read = reader.read(buffer)) > 0) {
			// output.append(buffer, 0, read);
			// }
			// reader.close();

			process.waitFor();
			// Log.d("thorn", output.toString());

			runtime.exec(createThumbnailCommand).waitFor();
		}
		catch (IOException e) {
			Log.e("thorn", "IOException", e);
		}
		catch (InterruptedException e) {
			Log.e("thorn", "InterruptedException", e);
		}

		Messenger messenger = (Messenger) intent.getExtras().get("MESSENGER");
		Message msg = Message.obtain();
		msg.obj = baseFilename;

		try {
			messenger.send(msg);
		}
		catch (RemoteException e) {
			Log.e("thorn", "Exception sending message", e);
		}

		File tempVideo = new File(tempPath);
		tempVideo.delete();
	}

	private String getRotateParam(String videoPath) {
		int orientation = getOrientation(videoPath);

		switch (orientation) {
		case 90:
			return " -strict -2 -vf transpose=1 ";
		case 180:
			return " -strict -2 -vf hflip,vflip ";
		case 270:
			return " -strict -2 -vf transpose=2 ";
		default:
			return "";
		}
	}

	private int getOrientation(String videoPath) {
		MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
		metaRetriever.setDataSource(videoPath);
		String rotation = metaRetriever
				.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
		return Integer.parseInt(rotation);
	}

}