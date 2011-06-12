/**
 * 
 */
package com.innowhite.whiteboard.docconversion.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * @author prashanthj
 * 
 */
public class DocFilter implements FilenameFilter {

    /*
     * (non-Javadoc)
     * 
     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
     */
    public boolean accept(File dir, String name) {
	return Pattern.matches("\\.jpg", name);
    }

}
