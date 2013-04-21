/**
 * @author Zachary Thompson
 * @author Steve Avery
 */

package edu.santarosa.szcgat.thorn;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Processor extends Service {
	
	/*
	 * The intention of the processor service is to handle the native side when
	 * processing the received videos and to issue the filename URI when a new
	 * video is being created.
	 * 
	 * and probably more stuff as we realize what's needed.
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
