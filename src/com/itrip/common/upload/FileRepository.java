package com.itrip.common.upload;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.itrip.cms.Constants;

/**
 * 本地文件存储
 */
public class FileRepository implements ServletContextAware {
	private Logger log = LoggerFactory.getLogger(FileRepository.class);

	/**
	 * 发文中类型图附件上传
	 * 
	 * @param path
	 * @param ext
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String storeByExt(String path, String ext, MultipartFile file) throws IOException {
		String filename = UploadUtils.generateFilename(path, ext);
		File dest = new File(getRealPath(filename));
		dest = UploadUtils.getUniqueFile(dest);
		store(file, dest);
		// 文件路径已经改为绝对路径，例如：E:/upload_file/201508/28173012frkt.jpg
		// 所以此处需要更改文件路径，映射到虚拟路径，前缀加"/file/" 白飞龙 2015-08-28
		filename = filename.replaceFirst(Constants.UPLOAD_PATH, Constants.VIRTUAL_PATH);
		return filename;
	}

	public String storeByFilename(String filename, MultipartFile file) throws IOException {
		File dest = new File(getRealPath(filename));
		store(file, dest);
		return filename;
	}

	public String storeByExt(String path, String ext, File file) throws IOException {
		String filename = UploadUtils.generateFilename(path, ext);
		File dest = new File(getRealPath(filename));
		dest = UploadUtils.getUniqueFile(dest);
		store(file, dest);
		return filename;
	}

	public String storeByFilename(String filename, File file) throws IOException {
		File dest = new File(getRealPath(filename));
		store(file, dest);
		return filename;
	}

	private void store(MultipartFile file, File dest) throws IOException {
		try {
			UploadUtils.checkDirAndCreate(dest.getParentFile());
			file.transferTo(dest);
		} catch (IOException e) {
			log.error("Transfer file error when upload file", e);
			throw e;
		}
	}

	private void store(File file, File dest) throws IOException {
		try {
			UploadUtils.checkDirAndCreate(dest.getParentFile());
			FileUtils.copyFile(file, dest);
		} catch (IOException e) {
			log.error("Transfer file error when upload file", e);
			throw e;
		}
	}

	public File retrieve(String name) {
		return new File(ctx.getRealPath(name));
	}

	private String getRealPath(String name) {
		// String realpath=ctx.getRealPath(name);
		// if(realpath==null){
		// realpath=ctx.getRealPath("/")+name;
		// }
		// return realpath;
		// 直接在常量类Constants中配置 UPLOAD_PATH = "E:/upload_file/" 为绝对路径
		return name;
	}

	private ServletContext ctx;

	public void setServletContext(ServletContext servletContext) {
		this.ctx = servletContext;
	}
}
