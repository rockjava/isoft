package com.isoftframework.common.io.file;

import java.io.File;
import java.io.FileFilter;

public class ExtensionFileFilter implements FileFilter {
	
	public final static String img_regex="jpg|png|gif|bmp";
	
	private String regex=".*";

	public ExtensionFileFilter(String regex) {
		this.regex = regex;
	}

	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}
		String name = file.getName();// find the last
		int index = name.lastIndexOf(".");
		if (index == -1) {
			return false;
		} else if (index == name.length() - 1) {
			return false;
		} else {
			return name.substring(index + 1).toLowerCase().matches(regex);
		}
	}
	
	public static void main(String[] args){
		String regex=".*";
		System.out.println("PNG".toLowerCase().matches(regex));
	}
}