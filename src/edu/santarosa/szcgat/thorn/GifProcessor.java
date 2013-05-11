/**
 * @author Zachary Thompson
 * @author Steve Avery
 */

package edu.santarosa.szcgat.thorn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.app.IntentService;
import android.content.Intent;
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

		String ffmpegPath = getFilesDir().getAbsolutePath() + File.separator
				+ "ffmpeg";
		File ffmpeg = new File(ffmpegPath);

		if (!ffmpeg.exists()) {
			copyFfmpeg();
		}

		if (!ffmpeg.canExecute()) {
			ffmpeg.setExecutable(true);
		}

		String tempPath = intent.getData().getPath();
		String baseFilename = intent.getData().getLastPathSegment()
				.replaceFirst(".mp4", "");
		String gifPath = Camera.THORN_PATH + File.separator + baseFilename
				+ ".gif";
		String thumbnailPath = Camera.THUMBNAIL_PATH + File.separator
				+ baseFilename + ".jpg";

		String createGifCommand = ffmpegPath + " -ss 00:00:00.000 -i "
				+ tempPath
				+ " -pix_fmt rgb24 -r 10 -s 320x240 -t 00:00:10.000 " + gifPath;

		String createThumbnailCommand = ffmpegPath + " -i " + tempPath
				+ " -vcodec mjpeg -vframes 1 -an -f rawvideo -s 512x384 "
				+ thumbnailPath;

		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(createGifCommand);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			int read;
			char[] buffer = new char[4096];
			StringBuffer output = new StringBuffer();
			while ((read = reader.read(buffer)) > 0) {
				output.append(buffer, 0, read);
			}
			reader.close();

			process.waitFor();
			Log.d("thorn", output.toString());

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

	private void copyFfmpeg() {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = getAssets().open("ffmpeg");
			out = new FileOutputStream(getFilesDir().getAbsolutePath()
					+ File.separator + "ffmpeg");
			copyFile(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		}
		catch (IOException e) {
			Log.e("thorn", "Failed to copy ffmpeg", e);
		}
	}

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}
}