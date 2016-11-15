package com.itrip.cms.staticpage;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.itrip.cms.entity.main.Channel;
import com.itrip.cms.entity.main.CmsSite;
import com.itrip.cms.entity.main.Content;

import freemarker.template.TemplateException;

public interface StaticPageSvc {

	/**
	 * 生成--->全部内容静态化
	 * 
	 * @param siteId
	 *            站点ID
	 * @param channelId
	 *            栏目ID
	 * @param start
	 *            开始日期
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public int content(Integer siteId, Integer channelId, Date start) throws IOException, TemplateException;

	/**
	 * 内容静态化
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public boolean content(Content content) throws IOException, TemplateException;

	public void contentRelated(Content content) throws IOException, TemplateException;

	public void deleteContent(Content content);

	public int channel(Integer siteId, Integer channelId, boolean containChild) throws IOException, TemplateException;

	public void channel(Channel channel, boolean firstOnly) throws IOException, TemplateException;

	public void deleteChannel(Channel channel);

	public void index(CmsSite site) throws IOException, TemplateException;

	/**
	 * @Title: index
	 * @Description: 首页静态化
	 * @param path
	 *            静态文件路径
	 * @param tpl
	 *            模板路径
	 * @param data
	 *            封装数据
	 * @param @throws IOException
	 * @param @throws TemplateException
	 * @return void 返回类型
	 */
	public void index(String path, String tpl, Map<String, Object> data) throws IOException, TemplateException;

	public boolean deleteIndex(CmsSite site);
}
