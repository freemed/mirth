package com.webreach.mirth.client.ui.util;

import java.io.File;
import java.io.IOException;

import com.Ostermiller.util.CSVParser;

public class HL7ReferenceLoader {
	public String[][] getReferenceTable() throws IOException {
		return CSVParser.parse(FileUtil.read(new File("reference.csv")));
	}
}
