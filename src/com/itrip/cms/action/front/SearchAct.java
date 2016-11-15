package com.itrip.cms.action.front;

import static com.itrip.cms.Constants.TPLDIR_SEARCH;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itrip.cms.entity.main.CmsSite;
import com.itrip.cms.web.CmsUtils;
import com.itrip.cms.web.FrontUtils;
import com.itrip.common.web.RequestUtils;

/**
 * 前端搜索
 * 
 * @ClassName: SearchAct
 * @Description: 提供前端搜索引擎支撑
 * @author Benny
 * @date 2015-12-9 下午05:42:41
 * 
 */
@Controller
public class SearchAct {

	public static final String SEARCH = "tpl.search";
	public static final String SEARCH_ERROR = "tpl.searchError";

	/**
	 * @Title: 搜索功能
	 * @Description: 搜索功能
	 * @param request
	 * @param response
	 * @param model
	 * @return String 返回类型
	 */
	@RequestMapping(value = "/search*.jspx", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		// 将request中所有参数保存至model中。
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		String q = RequestUtils.getQueryParam(request, "q");
		if (q != null) {
			String parseQ = parseKeywords(q);
			model.addAttribute("input", q);
			model.addAttribute("q", parseQ);
		}
		return FrontUtils.getTplPath(request, site, TPLDIR_SEARCH, SEARCH);
	}

	public static String parseKeywords(String q) {
		char c = '\\';
		int cIndex = q.indexOf(c);
		if (cIndex != -1 && cIndex == 0) {
			q = q.substring(1);
		}
		if (cIndex != -1 && cIndex == q.length() - 1) {
			q = q.substring(0, q.length() - 1);
		}
		try {
			String regular = "[\\+\\-\\&\\|\\!\\(\\)\\{\\}\\[\\]\\^\\~\\*\\?\\:\\\\]";
			Pattern p = Pattern.compile(regular);
			Matcher m = p.matcher(q);
			String src = null;
			while (m.find()) {
				src = m.group();
				q = q.replaceAll("\\" + src, ("\\\\" + src));
			}
			q = q.replaceAll("AND", "and").replaceAll("OR", "or").replace("NOT", "not");
		} catch (Exception e) {
		}
		return q;
	}
}
