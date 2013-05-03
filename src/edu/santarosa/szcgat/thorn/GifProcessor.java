/**
 * @author Zachary Thompson
 * @author Steve Avery
 */

package edu.santarosa.szcgat.thorn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

		String ffmpegPath = getFilesDir().getAbsolutePath() + "/ffmpeg";
		File ffmpeg = new File(ffmpegPath);

		if (!ffmpeg.exists()) {
			copyFfmpeg();
		}

		if (!ffmpeg.canExecute()) {
			ffmpeg.setExecutable(true);
		}

		String tempPath = intent.getData().getPath();
		String gifFilename = intent.getData().getLastPathSegment()
				.replaceFirst(".mp4", ".gif");
		String gifPath = CameraFragment.GIF_PATH + gifFilename;

		String ffmpegCommand = ffmpegPath + " -ss 00:00:00.000 -i " + tempPath
				+ " -pix_fmt rgb24 -r 10 -s 320x240 -t 00:00:10.000 " + gifPath;

		try {
			Runtime.getRuntime().exec(ffmpegCommand).waitFor();
		}
		catch (IOException e) {
			Log.e("thorn", "IOException", e);
		}
		catch (InterruptedException e) {
			Log.e("thorn", "InterruptedException", e);
		}

		Messenger messenger = (Messenger) intent.getExtras().get("MESSENGER");
		Message msg = Message.obtain();
		msg.obj = gifFilename;

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
					+ "/ffmpeg");
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
// Process process = Runtime
// .getRuntime()
// .exec("/data/data/edu.santarosa.szcgat.thorn/files/ffmpeg -i /storage/emulated/0/DCIM/thorn/tmp/20130429152358.mp4 -pix_fmt rgb24 /storage/emulated/0/DCIM/thorn/test.gif");
// // Reads stdout.
// process.waitFor();
// }
// catch (IOException e) {
// Log.e("thorn", "IOException", e);
// }
// catch (InterruptedException e) {
// Log.e("thorn", "InterruptedException", e);
// }