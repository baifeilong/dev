package com.itrip.common.http_conn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

public class HttpRequestUtils {

	private static HttpRequestUtils instance;

	public static HttpRequestUtils getInstance() {
		if (instance == null) {
			instance = new HttpRequestUtils();
		}
		return instance;
	}

	private HttpRequestUtils() {

	}

	/**
	 * @Title: postSendHttp
	 * @Description: post方式发送请求
	 * @param url
	 * @param inputObj
	 * @return Object 返回类型
	 * @throws
	 */
	public Object postSendHttp(String url, Object inputObj) {
		if (url == null || "".equals(url)) {
			return null;
		}
		HttpClient httpClient = CustomHttpClient.GetHttpClient();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/octet-stream");
		java.io.ByteArrayOutputStream bOut = new java.io.ByteArrayOutputStream(1024);
		java.io.InputStream bInput = null;
		java.io.ObjectOutputStream out = null;
		Serializable returnObj = null;
		try {
			out = new java.io.ObjectOutputStream(bOut);
			out.writeObject(inputObj);
			out.flush();
			out.close();
			out = null;
			bInput = new java.io.ByteArrayInputStream(bOut.toByteArray());
			InputStreamEntity inputStreamEntity = new InputStreamEntity(bInput, bOut.size(), null);
			inputStreamEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "UTF-8"));
			// 设置请求主体
			post.setEntity(inputStreamEntity);
			// 发起交易
			HttpResponse resp = httpClient.execute(post);
			int ret = resp.getStatusLine().getStatusCode();
			if (ret == HttpStatus.SC_OK) {
				// 响应分析
				HttpEntity entity = resp.getEntity();

				java.io.InputStream in = entity.getContent();
				java.io.ObjectInputStream oInput = new java.io.ObjectInputStream(in);
				returnObj = (Serializable) oInput.readObject();
				oInput.close();
				oInput = null;
				return returnObj;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @Title: postSendHttp
	 * @Description: post方式发送请求
	 * @param url
	 * @param body
	 * @return String 返回类型
	 */
	public String postSendHttp(String url, String body) {
		if (url == null || "".equals(url)) {
			return null;
		}
		HttpClient httpClient = CustomHttpClient.GetHttpClient();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/json;charset=UTF-8");
		try {
			if (StringUtils.isNotBlank(body)) {
				StringEntity stringEntity = new StringEntity(body, "UTF-8");
				stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "UTF-8"));
				// 设置请求主体
				post.setEntity(stringEntity);
			}
			// 发起请求
			HttpResponse resp = httpClient.execute(post);
			int ret = resp.getStatusLine().getStatusCode();
			if (ret == HttpStatus.SC_OK) {
				// 响应分析
				HttpEntity entity = resp.getEntity();

				BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuffer responseString = new StringBuffer();
				String result = br.readLine();
				while (result != null) {
					responseString.append(result);
					result = br.readLine();
				}
				return responseString.toString();
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @Title: getSendHttp
	 * @Description: get方式发送请求
	 * @param url
	 * @return String 返回类型
	 */
	public String getSendHttp(String url) {
		if (url == null || "".equals(url)) {
			return null;
		}
		try {
			HttpClient httpClient = CustomHttpClient.GetHttpClient();
			HttpGet get = new HttpGet(url);
			get.setHeader("Content-Type", "text/html;charset=UTF-8");
			// 发起交易
			HttpResponse resp = httpClient.execute(get);
			int ret = resp.getStatusLine().getStatusCode();
			if (ret == HttpStatus.SC_OK) {
				// 响应分析
				HttpEntity entity = resp.getEntity();

				BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuffer responseString = new StringBuffer();
				String result = br.readLine();
				while (result != null) {
					responseString.append(result);
					result = br.readLine();
				}

				return responseString.toString();
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

}
