package site.eris.test.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by wulinjie on 2015/11/30.
 */
public class HttpUtil {
	public static String sendRequest(String url) throws MalformedURLException, IOException {
		StringBuffer sb = new StringBuffer();
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(30000);
		conn.setUseCaches(false);
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String str = null;
		while ((str = reader.readLine()) != null) {
			sb.append(str);
		}
		return sb.toString();
	}

	public static String sendPostRequest(String url, String body) throws IOException {
		StringBuffer sb = new StringBuffer();
		HttpURLConnection conn;
		BufferedReader reader = null;
		OutputStream os = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(30000);
			if (StringUtils.isNotEmpty(body)) {
				os = conn.getOutputStream();
				os.write(body.getBytes("UTF-8"));
			}
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String str = null;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
			return sb.toString();
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(os);
		}
	}
	public static String convert(String utfString){
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while((i=utfString.indexOf("\\u", pos)) != -1){
			sb.append(utfString.substring(pos, i));
			if(i+5 < utfString.length()){
				pos = i+6;
				sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
			}
		}

		return sb.toString();
	}

	/**
	 * 获取序列化后的参数列表
	 * @return
	 */
	public static String getSerializeParams(Map<String, String> params){
		Object[] objs = params.keySet().toArray();
		Arrays.sort(objs);
		StringBuffer buffer = new StringBuffer();
		for( int i = 0; i < objs.length ; i++ ){
			buffer.append(objs[i].toString()).append("=").append(params.get(objs[i])).append("&");
		}
		String URI = buffer.toString();
		URI = URI.substring(0, URI.length()-1);
		return URI;
	}
}
