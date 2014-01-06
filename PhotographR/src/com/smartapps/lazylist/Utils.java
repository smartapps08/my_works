package com.smartapps.lazylist;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;

		byte[] bytes = new byte[buffer_size];
		for (;;) {
			int count;
			try {
				count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
