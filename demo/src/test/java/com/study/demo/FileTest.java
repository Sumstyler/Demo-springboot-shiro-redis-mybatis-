package com.study.demo;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;

public class FileTest {

	public static void main(String[] args) {
		String file = "cc85a353-c5f2-4db4-a8f8-d04bc70f90ad.xlsx";
		File f = new File(file);
		String type = new MimetypesFileTypeMap().getContentType(f);
		System.out.println(type);

		double x1 = 161851 / 1024.f;
		System.out.println(String.format("%.2f", x1));
	}
}
