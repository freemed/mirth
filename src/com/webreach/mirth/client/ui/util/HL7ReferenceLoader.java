package com.webreach.mirth.client.ui.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import com.Ostermiller.util.CSVParser;

public class HL7ReferenceLoader {
	public String[][] getReferenceTable() throws IOException {
		File referenceFile = new File("reference.csv");
		BufferedReader reader = new BufferedReader(new FileReader(referenceFile));
		StringBuilder contents = new StringBuilder();
		String line = null;

		while ((line = reader.readLine()) != null) {
			contents.append(line + "\n");
		}

		reader.close();
		return CSVParser.parse(new StringReader(contents.toString()));
	}
}
