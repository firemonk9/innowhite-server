package com.innowhite.whiteboard.docconversion.util;

import java.io.File;
import java.io.FileFilter;

public class ImageFileFilter implements FileFilter {

	private final String[] okFileExtensions = 
	    new String[] {"jpg", "png", "gif" , "swf"};

	
	public boolean accept(File file) {
		for (String extension : okFileExtensions)
	    {
	      if (file.getName().toLowerCase().endsWith(extension))
	      {
	        return true;
	      }
	    }
	    return false;
	}

}
