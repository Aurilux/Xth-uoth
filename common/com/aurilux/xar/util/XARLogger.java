package com.aurilux.xar.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class XARLogger {
	private static String info;
	
	static {
		info = "";
	}
	
	public static void write(String s) {
		info += s;
	}
	
	public static boolean flush() {
		try {
			PrintWriter log_file_writer = new PrintWriter(new FileOutputStream("output.txt"), true);
			log_file_writer.write(info);
			log_file_writer.close();
			return true;
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			return false;
		}
	}
}