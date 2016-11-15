package com.itrip.cms.staticpage;

import static com.itrip.common.web.Constants.UTF8;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itrip.cms.entity.main.Channel;
import com.itrip.cms.entity.main.CmsSite;
import com.itrip.cms.entity.main.Content;
import com.itrip.cms.entity.main.ContentCheck;
import com.itrip.cms.manager.assist.CmsKeywordMng;
import com.itrip.cms.web.FrontUtils;
import com.itrip.common.hibernate3.Finder;
import com.itrip.common.hibernate3.HibernateSimpleDao;
import com.itrip.common.page.Paginable;
import com.itrip.common.page.SimplePage;
import com.itrip.common.web.springmvc.RealPathResolver;
import com.itrip.core.web.front.URLHelper;
import com.itrip.core.web.front.URLHelper.PageInfo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Repository
public class StaticPageDaoImpl extends HibernateSimpleDao implements StaticPageDao {

	public int channelStatic(Integer siteId, Integer channelId, boolean containChild, Configuration conf, Map<String, Object> data)
			throws IOException, TemplateException {
		Finder finder = Finder.create("select bean from Channel bean");
		if (channelId != null) {
			if (containChild) {
				finder.append(",Channel parent where").append(" bean.lft between parent.lft and parent.rgt").append(
						" and parent.site.id=bean.site.id").append(" and parent.id=:channelId");
				finder.setParam("channelId", channelId);
			} else {
				finder.append(" where bean.id=:channelId");
				finder.setParam("channelId", channelId);
			}
		} else if (siteId != null) {
			finder.append(" where bean.site.id=:siteId");
			finder.setParam("siteId", siteId);
		}
		Session session = getSession();
		ScrollableResults channels = finder.createQuery(session).setCacheMode(CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
		int count = 0;
		CmsSite site;
		Channel c;
		int quantity, totalPage;
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		while (channels.next()) {
			c = (Channel) channels.get(0);
			site = c.getSite();
			FrontUtils.frontData(data, site, null, null, null);
			// 如果是外部链接或者不需要生产静态页，则不生成
			if (!StringUtils.isBlank(c.getLink()) || !c.getStaticChannel()) {
				continue;
			}
			// 没有内容或者有子栏目，则只生成一页
			int childs = childsOfChannel(c.getId());
			if (!c.getModel().getHasContent()) {
				totalPage = 1;
			} else {
				if (c.getListChild()) {
					quantity = childs;
				} else {
					if (!c.getListChild() && childs > 0) {
						quantity = contentsOfParentChannel(c.getId());
					} else {
						quantity = contentsOfChannel(c.getId());
					}
				}
				if (quantity <= 0) {
					totalPage = 1;
				} else {
					totalPage = (quantity - 1) / c.getPageSize() + 1;
				}
			}
			this.staticChannel(c, totalPage, conf, data);
			if (++count % 20 == 0) {
				session.clear();
			}
		}
		log.info("channel static success! total: " + count);
		return count;
	}

	public void channelStatic(Channel c, boolean firstOnly, Configuration conf, Map<String, Object> data) throws IOException, TemplateException {
		// 如果是外部链接或者不需要生产静态页，则不生成
		if (!StringUtils.isBlank(c.getLink()) || !c.getStaticChannel()) {
			return;
		}
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		// 没有内容或者有子栏目，则只生成一页
		int childs = childsOfChannel(c.getId());
		int quantity, totalPage;
		if (firstOnly || !c.getModel().getHasContent() || (!c.getListChild() && childs > 0)) {
			totalPage = 1;
		} else {
			if (c.getListChild()) {
				quantity = childs;
			} else {
				quantity = contentsOfChannel(c.getId());
			}
			if (quantity <= 0) {
				totalPage = 1;
			} else {
				totalPage = (quantity - 1) / c.getPageSize() + 1;
			}
		}
		FrontUtils.frontData(data, c.getSite(), null, null, null);
		this.staticChannel(c, totalPage, conf, data);
	}

	/**
	 * 生成--->全部内容静态化
	 * 
	 * @param siteId
	 * @param channelId
	 * @param start
	 * @param conf
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public int contentStatic(Integer siteId, Integer channelId, Date start, Configuration conf, Map<String, Object> data) throws IOException,
			TemplateException {
		Finder f = Finder.create("select bean from Content bean");
		if (channelId != null) {
			f.append(" join bean.channel node,Channel parent");
			f.append(" where node.lft between parent.lft and parent.rgt");
			f.append(" and parent.id=:channelId");
			f.append(" and node.site.id=parent.site.id");
			f.setParam("channelId", channelId);
		} else if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else {
			f.append(" where 1=1");
		}
		if (start != null) {
			f.append(" and bean.sortDate>=:start");
			f.setParam("start", start);
		}
		f.append(" and bean.status=" + ContentCheck.CHECKED);
		Session session = getSession();
		ScrollableResults contents = f.createQuery(session).setCacheMode(CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
		int count = 0;
		Content c;
		Channel chnl;
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		while (contents.next()) {
			c = (Content) contents.get(0);
			chnl = c.getChannel();
			// 如果是外部链接或者不生成静态页面，则不生成
			if (!StringUtils.isBlank(c.getLink()) || !chnl.getStaticContent()) {
				continue;
			}
			// 如果不需要生成静态页面，则不生成
			if (!c.getNeedRegenerate()) {
				continue;
			}
			FrontUtils.frontData(data, c.getSite(), null, null, null);
			data.put("content", c);
			data.put("channel", c.getChannel());
			this.staticContent(c, conf, data);// 内容静态化
			c.setNeedRegenerate(false);
			if (++count % 20 == 0) {
				session.clear();
			}
		}
		log.info("content static success! total: " + count);
		return count;
	}

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
	public boolean contentStatic(Content c, Configuration conf, Map<String, Object> data) throws IOException, TemplateException {
		// 如果是外部链接或者不生成静态页面，则不生成
		Channel chnl = c.getChannel();
		if (!StringUtils.isBlank(c.getLink()) || !chnl.getStaticContent()) {
			return false;
		}
		// 如果不需要生成静态页面，则不生成
		if (!c.getNeedRegenerate()) {
			return false;
		}
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		FrontUtils.frontData(data, c.getSite(), null, null, null);
		data.put("content", c);
		data.put("channel", chnl);
		this.staticContent(c, conf, data);// 内容静态化
		c.setNeedRegenerate(false);
		return true;
	}

	public int contentsOfChannel(Integer channelId) {
		String hql = "select count(*) from Content bean" + " join bean.channels as channel" + " where channel.id=:channelId and bean.status="
				+ ContentCheck.CHECKED;
		Query query = getSession().createQuery(hql);
		query.setParameter("channelId", channelId);
		return ((Number) query.iterate().next()).intValue();
	}

	public int contentsOfParentChannel(Integer channelId) {
		String hql = "select count(*) from Content bean" + " join bean.channel channel,Channel parent"
				+ "  where channel.lft between parent.lft and parent.rgt and channel.site.id=parent.site.id and parent.id=:parentId and bean.status="
				+ ContentCheck.CHECKED;
		Query query = getSession().createQuery(hql);
		query.setParameter("parentId", channelId);
		return ((Number) query.iterate().next()).intValue();
	}

	public int childsOfChannel(Integer channelId) {
		String hql = "select count(*) from Channel bean" + " where bean.parent.id=:channelId";
		Query query = getSession().createQuery(hql);
		query.setParameter("channelId", channelId);
		return ((Number) query.iterate().next()).intValue();
	}

	/**
	 * @Title: staticContent
	 * @Description: 生成静态内容页
	 * @param content
	 *            内容对象
	 * @param conf
	 *            freemarker配置
	 * @param data
	 *            封装数据
	 * @return void 返回类型
	 * @throws TemplateException
	 * @throws IOException
	 */
	private void staticContent(Content content, Configuration conf, Map<String, Object> data) throws TemplateException, IOException {
		for (int pageNo = 1; pageNo <= content.getPageCount(); pageNo++) {
			String txt = content.getTxtByNo(pageNo);
			// 内容加上关键字
			txt = cmsKeywordMng.attachKeyword(content.getSite().getId(), txt);
			Paginable pagination = new SimplePage(pageNo, 1, content.getPageCount());
			data.put("pagination", pagination);
			String url = content.getUrlStatic(pageNo);
			PageInfo info = URLHelper.getPageInfo(url.substring(url.lastIndexOf("/")), null);
			FrontUtils.putLocation(data, url);
			FrontUtils.frontPageData(pageNo, info.getHref(), info.getHrefFormer(), info.getHrefLatter(), data);
			data.put("title", content.getTitleByNo(pageNo));
			data.put("txtDetail", txt);
			data.put("pic", content.getPictureByNo(pageNo));
			String fileName = content.getStaticFilename(pageNo); // 文件相对路径及名称，例如：/itrip/au/index.html
			// 生成pc静态文件
			String tpl = content.getTplContentOrDef();
			File file = this.getStaticFile(fileName, pageNo == 1, false);
			this.createFile(tpl, file, conf, data);
			// 生成mobile静态文件
			String mobileTpl = FrontUtils.getMobileTplPath(tpl);
			file = this.getStaticFile(fileName, pageNo == 1, true);
			this.createFile(mobileTpl, file, conf, data);
		}
	}

	/**
	 * @Title: staticChannel
	 * @Description: 生成静态栏目页
	 * @param channel
	 *            栏目对象
	 * @param totalPage
	 *            总页数
	 * @param conf
	 *            freemarker配置
	 * @param data
	 *            封装数据
	 * @return void 返回类型
	 * @throws TemplateException
	 * @throws IOException
	 */
	private void staticChannel(Channel channel, int totalPage, Configuration conf, Map<String, Object> data) throws TemplateException, IOException {
		for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
			String fileName = channel.getStaticFilename(pageNo); // 文件相对路径及名称，例如：/itrip/au/index.html
			String urlStatic = channel.getUrlStatic(pageNo);
			PageInfo info = URLHelper.getPageInfo(fileName.substring(fileName.lastIndexOf("/")), null);
			FrontUtils.frontPageData(pageNo, info.getHref(), info.getHrefFormer(), info.getHrefLatter(), data);
			FrontUtils.putLocation(data, urlStatic);
			data.put("channel", channel);
			// 列表页左侧菜单树保证最后一级有数据，最后一级时显示同级所有菜单
			if (channel.getChild() == null || channel.getChild().size() == 0) {
				data.put("parent", channel.getParent());
			} else {
				data.put("parent", channel);
			}
			// 生成pc静态文件
			String tpl = channel.getTplChannelOrDef();
			File file = this.getStaticFile(fileName, true, false);
			this.createFile(tpl, file, conf, data);
			// 生成mobile静态文件
			String mobileTpl = FrontUtils.getMobileTplPath(tpl);
			file = this.getStaticFile(fileName, pageNo == 1, true);
			this.createFile(mobileTpl, file, conf, data);
		}
	}

	/**
	 * @Title: getStaticFile
	 * @Description: 获取待生成静态文件
	 * @param fileName
	 *            文件名称
	 * @param isFirst
	 *            是否首次生成
	 * @param isMobile
	 *            是否移动端
	 * @return File
	 */
	private File getStaticFile(String fileName, Boolean isFirst, Boolean isMobile) {
		File file = null;
		if (isMobile) {
			file = realPathResolver.getMobileRealFile(fileName);
		} else {
			file = realPathResolver.getRealFile(fileName);
		}
		if (isFirst) {
			File parent = file.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
		}
		return file;
	}

	/**
	 * @Title: createFile
	 * @Description:生成静态文件
	 * @param solution
	 *            模板方案路径
	 * @param file
	 *            待生成文件
	 * @param conf
	 *            freemarker配置类
	 * @param data
	 *            封装数据
	 * @return Boolean 返回类型
	 * @throws TemplateException
	 * @throws IOException
	 */
	private Boolean createFile(String solution, File file, Configuration conf, Map<String, Object> data) throws TemplateException, IOException {

		Template tpl = conf.getTemplate(solution);
		Writer out = null;
		try {
			// FileWriter不能指定编码确实是个问题，只能用这个代替了。
			out = new OutputStreamWriter(new FileOutputStream(file), UTF8);
			tpl.process(data, out);
			log.info("create static file: {}", file.getAbsolutePath());
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return true;
	}

	private CmsKeywordMng cmsKeywordMng;
	private RealPathResolver realPathResolver;

	@Autowired
	public void setCmsKeywordMng(CmsKeywordMng cmsKeywordMng) {
		this.cmsKeywordMng = cmsKeywordMng;
	}

	@Autowired
	public void setRealPathResolver(RealPathResolver realPathResolver) {
		this.realPathResolver = realPathResolver;
	}
}
