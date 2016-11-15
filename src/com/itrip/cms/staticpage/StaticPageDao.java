package com.itrip.cms.staticpage;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.itrip.cms.entity.main.Channel;
import com.itrip.cms.entity.main.Content;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public interface StaticPageDao {

	/**
	 * 生成--->栏目页静态化
	 * 
	 * @param siteId
	 *            站点ID
	 * @param channelId
	 *            栏目ID
	 * @param containChild
	 *            是否包含下级
	 * @param conf
	 *            freemarker配置
	 * @param data
	 *            封装数据
	 * @return count 已静态化栏目数量
	 * @throws IOException
	 * @throws TemplateException
	 */
	public int channelStatic(Integer siteId, Integer channelId, boolean containChild, Configuration conf, Map<String, Object> data)
			throws IOException, TemplateException;

	/**
	 * @Title: channelStatic
	 * @Description: 栏目静态化
	 * @param c
	 *            栏目对象
	 * @param firstOnly
	 *            是否首次静态化
	 * @param conf
	 *            freemarker配置
	 * @param data
	 *            封装数据
	 * @return void 返回类型
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void channelStatic(Channel c, boolean firstOnly, Configuration conf, Map<String, Object> data) throws IOException, TemplateException;

	/**
	 * 生成--->全部内容静态化
	 * 
	 * @param siteId
	 *            站点ID
	 * @param channelId
	 *            栏目ID
	 * @param start
	 *            开始日期
	 * @param conf
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public int contentStatic(Integer siteId, Integer channelId, Date start, Configuration conf, Map<String, Object> data) throws IOException,
			TemplateException;

	/**
	 * 内容静态化
	 * 
	 * @param c
	 * @param conf
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public boolean contentStatic(Content c, Configuration conf, Map<String, Object> data) throws IOException, TemplateException;

	/**
	 * 
	 * @Title: contentsOfChannel
	 * @Description: 获取栏目下内容总数，末级栏目
	 * @param channelId
	 *            栏目ID
	 * @return int 内容总数
	 */
	public int contentsOfChannel(Integer channelId);

	/**
	 * @Title: childsOfChannel
	 * @Description: 获取栏目下包含的子栏目数
	 * @param channelId
	 * @return int 子栏目数
	 */
	public int childsOfChannel(Integer channelId);
}
