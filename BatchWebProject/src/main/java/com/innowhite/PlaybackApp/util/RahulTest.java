package com.innowhite.PlaybackApp.util;

public class RahulTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String line = "width           : 1080 ";
		String[] temp = line.split(":");// substring(line.indexOf(":") + 2);
		System.out.println(temp[1].trim());
	}

}
