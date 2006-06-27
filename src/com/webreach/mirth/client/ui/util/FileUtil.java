package com.webreach.mirth.client.ui.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
	public static void write(File file, String data) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(data);
		writer.flush();
		writer.close();
	}

	public static String read(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder contents = new StringBuilder();
		String line = null;

		while ((line = reader.readLine()) != null) {
			contents.append(line + "\n");
		}
		
		reader.close();
		return contents.toString();
	}
}
