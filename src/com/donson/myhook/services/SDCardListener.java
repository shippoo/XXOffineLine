package com.donson.myhook.services;

import android.os.FileObserver;
import android.util.Log;

public class SDCardListener extends FileObserver {

	public SDCardListener(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEvent(int event, String path) {
		int e = event & FileObserver.ALL_EVENTS;
		switch (e) {
		case FileObserver.ACCESS:
			Log.i("FILELIS", "ACCESS: " + path);
			break;
		case FileObserver.ATTRIB:
			Log.i("FILELIS", "ATTRIB: " + path);
			break;
		case FileObserver.CLOSE_NOWRITE:
			Log.i("FILELIS", "CLOSE_NOWRITE: " + path);
			break;
		case FileObserver.CLOSE_WRITE:
			Log.i("FILELIS", "CLOSE_WRITE: " + path);
			break;
		case FileObserver.CREATE:
			Log.i("FILELIS", "CREATE: " + path);
			break;
		case FileObserver.DELETE:
			Log.i("FILELIS", "DELETE: " + path);
			break;
		case FileObserver.DELETE_SELF:
			Log.i("FILELIS","DELETE_SELF: " +
					 path);
			
			break;
		case FileObserver.MODIFY:
			Log.i("FILELIS", "MODIFY: " + path);
			break;
		case FileObserver.MOVE_SELF:
			Log.i("FILELIS", "MOVE_SELF: " + path);
			break;
		case FileObserver.MOVED_FROM:
			Log.i("FILELIS", "MOVED_FROM: " + path);
			break;
		case FileObserver.MOVED_TO:
			Log.i("FILELIS", "MOVED_TO: " + path);
			break;
		case FileObserver.OPEN:
			Log.i("FILELIS", "OPEN: " + path);
			break;
		default:
			Log.i("FILELIS", "DEFAULT(" + event + "): " + path);
			break;
		case FileObserver.ALL_EVENTS:
			Log.i("FILELIS", "path:" + path);
			break;

		}
	}

}
