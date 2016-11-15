package com.itrip.common.web.springmvc;

import java.io.File;

/**
 * 绝对路径提供类
 */
public interface RealPathResolver {
	/**
	 * 获得绝对路径
	 * 
	 * @param path
	 * @return
	 * @see javax.servlet.ServletContext#getRealPath(String)
	 */
	public String get(String path);

	/**
	 * 获取PC静态文件存放绝对路径
	 * 
	 * @param fileName
	 * @return
	 */
	public File getRealFile(String fileName);

	/**
	 * 获取mobile静态文件存放绝对路径
	 * 
	 * @param fileName
	 * @return
	 */
	public File getMobileRealFile(String fileName);
}
