package com.itrip.cms.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.itrip.cms.entity.main.Content;
import com.itrip.cms.manager.main.ChannelMng;
import com.itrip.cms.manager.main.ContentMng;

/**
 * 内容详情静态页301重定向servlet
 * 
 * @author Benny
 * @since 2015-09-02
 * 
 */
public class RedirectServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static WebApplicationContext ctx;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id"); // 内容ID
		String cpath = req.getParameter("channel"); // 栏目path
		String filename = null;
		if (StringUtils.isNotBlank(id)) {// 内容页301跳转路径
			ContentMng contentMng = (ContentMng) BeanFactoryUtils.beanOfTypeIncludingAncestors(ctx, ContentMng.class);
			Content content = contentMng.findById(Integer.valueOf(id));
			if (content != null) {// 此处会出现NullPointerException
				filename = content.getStaticFilename(0);
			}
		} else if (StringUtils.isNotBlank(cpath)) {// 栏目页301跳转路径
			ChannelMng channelMng = (ChannelMng) BeanFactoryUtils.beanOfTypeIncludingAncestors(ctx, ChannelMng.class);
			filename = channelMng.findByOldPath(cpath);
			filename = filename != null && !filename.endsWith("/") ? filename + "/" : filename;
		}
		if (filename != null) {
			resp.setStatus(HttpStatus.SC_MOVED_PERMANENTLY);
			resp.setHeader("Location", filename);
			resp.setHeader("Connection", "close");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
}
