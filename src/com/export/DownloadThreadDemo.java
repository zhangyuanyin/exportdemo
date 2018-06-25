package com.export;

import java.io.File;

public class DownloadThreadDemo {
//	private static String[] months = {"08", "09", "10", "11", "12"};
	private static String[] months = {"11"};
	private static String[] paths = {"D:/pic/download/sources/", "D:/pic/download/pictures/11bt"};
	
	public static void main(String[] args) throws Exception {
		// 创建文件夹
		for (String path : paths) {
			File file = new File(path);
			if(!file.exists()) {
				file.mkdirs();
			}
		}
		
		for (int i = 1; i <= 1; i++) {
			Export export = new Export();
			export.setMonth(months[0]);
			export.setFilepath(paths[0]);
			export.setFilename(months[0]+"月Bt_data"+i+".txt");
			export.setSavepath(paths[1]+months[0]+"/");
			Thread exportThread = new Thread(export);
			exportThread.start();
		}
	}
}
