package com.export.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 根据文件地址，下载图片
 * @author yyzhang 2017年12月19日17:51:37
 *
 */
public class HttpConntionHelper {
	
	private static final String FILE_NAME = "file_name"; // 文件名称
	
    public void download(String[] path, String filePath, List<String> errorMsg, String line) {
    	InputStream in = null;
    	FileOutputStream out = null;
    	String url = path[4];
    	
		System.out.println("【影像下载地址】" + url);
        
		if("".equals(StringUtils.trimToEmpty(url))) {
			System.out.println("地址为空了 ############## appNo = "+path[2] +" length: "+path.length);
			return;
		}
		
        try {
        	if(path[2].startsWith("MJ") && url.contains("ffce")) {
        		url = url.replaceAll("ffce", "rmps").replaceAll("fastdfs-gateway", "fastdfs-gateway-rmps");
        	}
        	
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			HttpResponse response = httpClient.execute(get);
			
			// 获取并验证执行结果
			int code = response.getStatusLine().getStatusCode();
			if(code == HttpStatus.SC_OK){
				String fileName = "";
				in = response.getEntity().getContent();
				Header[] headers = response.getAllHeaders();
				for (Header header : headers) {
					if(FILE_NAME.equals(header.getName())){
						fileName = URLDecoder.decode(header.getValue(), "utf-8");
						break;
					}
				}
				
				if(path[0].contains("?")) {
					path[0] = "用户名乱码@@@";
				}
				// 判断文件路径是否存在，不存在创建对应路径
				filePath += path[0]+"_"+path[1] + "/";
				File file = new File(filePath);
				if(file.exists() && file.listFiles().length > 1){
					return;
				}
				if(!file.isDirectory()){
					file.mkdirs();
				}
				
				// 判断文件是否存在，不存在创建新文件
				if("".equals(fileName)) 
					fileName = path[5];
				file = new File(filePath + fileName);
				if(!file.exists()){
					file.createNewFile();
				}
				
				// 将输入流写入到新文件中
				out = new FileOutputStream(file);
				int i = 0;
				while(!((i = in.read()) == -1)){
					out.write(i);
				}
				out.flush();
			}else {
				errorMsg.add(line);
				System.out.println("【影像系统-下载】失败, appNo = "+path[2]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null)
					out.close();
				if(in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
}
