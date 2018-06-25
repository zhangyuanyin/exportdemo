package com.export.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件批量下载工具类
 * @author yyzhang 2017年12月19日18:00:17
 */
public class ExportBatchPicFileUtil {
	
	/**
	 * 读文件
	 * @param savePath 
	 * @throws Exception 
	 */
	public void readFile(String[] fileNames, String filepath, String savepath) throws Exception {
		for (String name : fileNames) {
			File file = new File(filepath + name);
			if(!file.exists()) continue;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				
				if(values.length < 5) continue;
				
				String filePath = savepath;
				filePath += values[0]+"_"+values[1] + "/";
				File checkFile = new File(filePath);
				if(checkFile.exists() && checkFile.listFiles().length > 1){
					continue;
				}
				
//				new HttpConntionHelper().download(values, savepath);
				
				if(values.length == 8) {
					values[4] = values[6];
					values[5] = values[7];
//					new HttpConntionHelper().download(values, savepath);
				}
			}
			if(br != null) {
				br.close();
			}
		}
	}
	
	/**
	 * 读文件
	 * @param filename
	 * @param filepath
	 * @param savepath
	 * @throws Exception
	 */
	public void readFile(String filename, String filepath, String savepath) throws Exception {
		List<String> errorMsg = new ArrayList<String>();
		File file = new File(filepath + filename);
		if(!file.exists()) return;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] values = line.split(",");
			
			if(values.length < 5) continue;
			
			String filePath = savepath;
			filePath += values[0]+"_"+values[1] + "/";
			File checkFile = new File(filePath);
			if(checkFile.exists()){
				continue;
			}
			new HttpConntionHelper().download(values, savepath, errorMsg, line);
			
			if(values.length == 8) {
				values[4] = values[6];
				values[5] = values[7];
				new HttpConntionHelper().download(values, savepath, errorMsg, line);
			}
		}
		if(br != null) {
			br.close();
		}
		
		// 写异常文件信息
		if(errorMsg != null && errorMsg.size() > 0)
			this.writeErrorMsg(errorMsg, filename);
	}

	/**
	 * 
	 * @param errorMsg
	 * @param filename 
	 * @throws IOException 
	 */
	private void writeErrorMsg(List<String> errorMsg, String filename) {
		BufferedWriter bw = null;
		try {
			File file = new File("D:/pic/download/errors/"+filename.replaceAll("data", "error"));
			bw = new BufferedWriter(new FileWriter(file));
			if(!errorMsg.isEmpty()){
				for(String error : errorMsg){
					bw.write(error + "\n");
				}
				bw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
