package org.quarkbit.controlpalabravida.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImageFilter extends FileFilter {
	final static String jpg = "jpg";
	final static String png = "png";
	final static String gif = "gif";

	// Accept all directories and all gif, jpg, or tiff files.
	public boolean accept(File f) {

		if (f.isDirectory()) {
			return true;
		}

		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			String extension = s.substring(i + 1).toLowerCase();
			if (jpg.equals(extension) || png.equals(extension)
					|| gif.equals(extension)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	// The description of this filter
	public String getDescription() {
		return "JPG & PNG & GIF Images";
	}
}
