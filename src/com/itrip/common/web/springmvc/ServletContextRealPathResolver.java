package com.itrip.common.web.springmvc;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.itrip.cms.Constants;

@Component
public class ServletContextRealPathResolver implements RealPathResolver, ServletContextAware {
	public String get(String path) {
		String realpath = context.getRealPath(path);
		// tomcat8.0获取不到真实路径，通过/获取路径
		if (realpath == null) {
			realpath = context.getRealPath("/") + path;
		}
		return realpath;
	}

	/**
	 * 获取PC静态文件存放绝对路径
	 * 
	 * @param filename
	 * @return
	 */
	public File getRealFile(String fileName) {
		// 将静态文件存储于项目之外的文件夹，避免每次发版都需要重新生成静态文件；白飞龙 2015-08-31
		fileName = StringUtils.isBlank(fileName) ? "tmp.html" : fileName;
		String real = fileName.startsWith("/") ? Constants.STATIC_PATH + fileName : Constants.STATIC_PATH + "/" + fileName;
		return new File(real);
	}

	/**
	 * 获取mobile静态文件存放绝对路径
	 * 
	 * @param filename
	 * @return
	 */
	public File getMobileRealFile(String fileName) {
		// 将静态文件存储于项目之外的文件夹，避免每次发版都需要重新生成静态文件；白飞龙 2015-12-16
		fileName = StringUtils.isBlank(fileName) ? "tmp.html" : fileName;
		String real = fileName.startsWith("/") ? Constants.STATIC_MOBILE_PATH + fileName : Constants.STATIC_MOBILE_PATH + "/" + fileName;
		return new File(real);
	}

	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}

	private ServletContext context;
}
