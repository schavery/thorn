package edu.santarosa.szcgat.thorn;

import java.io.File;
import android.content.Context;
import android.widget.TextView;

/*this part communicates with native code through jni (java native interface)*/
public class TestJni {
	public static TextView getView(String path, Context ctx) {
		File file = new File(path);
		String string = showVideoInfo(file);
		TextView tv = new TextView(ctx);
		tv.setText(string);
		return tv;
	}
	
	//load the native library
	static {
		//System.loadLibrary("ffmpeg");
		//System.loadLibrary("ffmpeg-jni");
	}

	//declare the jni functions
	private static native void naInit(String _videoFileName);
//	private static native int[] naGetVideoResolution();
//	private static native String naGetVideoCodecName();
//	private static native String naGetVideoFormatName();
	private static native void naClose();

	private static String showVideoInfo(final File _file) {
		String videoFilename = _file.getAbsolutePath();
		naInit(videoFilename);
//		int[] prVideoRes = naGetVideoResolution();
//		String prVideoCodecName = naGetVideoCodecName();
//		String prVideoFormatName = naGetVideoFormatName();
		naClose();

		String displayText = "Video: " + videoFilename + "\n";
//		displayText += "Video Resolution: " + prVideoRes[0] + "x" + prVideoRes[1] + "\n";
//		displayText += "Video Codec: " + prVideoCodecName + "\n";
//		displayText += "Video Format: " + prVideoFormatName + "\n";
		return displayText;
	}
}

