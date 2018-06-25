package com.export;

import com.export.util.ExportBatchPicFileUtil;

public class Export implements Runnable{
	private String filename;
	private String filepath;
	private String savepath;
	private String month;
	
	@Override
	public void run() {
		try {
			new ExportBatchPicFileUtil().readFile(filename, filepath, savepath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getFilepath() {
		return filepath;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getSavepath() {
		return savepath;
	}
	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
}
