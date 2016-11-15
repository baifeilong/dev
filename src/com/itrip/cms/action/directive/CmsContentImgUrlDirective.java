package com.itrip.cms.action.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.itrip.common.web.freemarker.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 获取内容中图片url标签
 */
public class CmsContentImgUrlDirective implements TemplateDirectiveModel {

	/** 输入参数文章内容 */
	public static final String PARAM_TEXT = "text";

	/** 页面输出对象 */
	public static final String OUT_LIST = "url_list";

	/** 页面输出对象 */
	public static final String COUNT = "count";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<String> list = new ArrayList<String>();
		String text = DirectiveUtils.getString(PARAM_TEXT, params);
		if (StringUtils.isNotBlank(text)) {
			Integer total = DirectiveUtils.getInt(COUNT, params);
			// String reg = "<img[^<>]*?\\ssrc=['\"]?(.*?)['\"].*?>";
			// //匹配整个img标签
			String reg = "src=['\"]?(.*?)['\"].*?"; // 匹配src属性值
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(text);
			int i = 0;
			while (matcher.find() && i++ < total) {// 最多获取四张图片的url
				list.add(matcher.group());
			}
		}
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}
}
