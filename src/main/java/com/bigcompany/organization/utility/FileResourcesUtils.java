package com.bigcompany.organization.utility;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class FileResourcesUtils {
	private static final String ERROR_FILE_NOT_FOUND = "ERROR: CSV File Not Found: %s";

	public File getFileFromResource(String fileName) throws URISyntaxException {

		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException(String.format(ERROR_FILE_NOT_FOUND, fileName));
		} else {
			return new File(resource.toURI());
		}
	}
}
