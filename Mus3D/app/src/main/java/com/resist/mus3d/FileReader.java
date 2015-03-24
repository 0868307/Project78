package com.resist.mus3d;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class FileReader {
	private Context ctx;
	private int resource;
	private String data;

	public FileReader(Context ctx, int resource) {
		this.ctx = ctx;
		this.resource = resource;
	}

	public String getData() {
		if(data == null) {
			try {
				readAll();
			} catch(IOException e) {
				Log.e(this.getClass().getCanonicalName(), "Failed to read data.", e);
			}
		}
		return data;
	}

	private void readAll() throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStream in = ctx.getResources().openRawResource(resource);
		int read;
		while((read = in.read()) != -1) {
			sb.appendCodePoint(read);
		}
		in.close();
		data = sb.toString();
	}
}
