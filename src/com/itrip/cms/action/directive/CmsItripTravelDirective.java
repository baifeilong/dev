package com.itrip.cms.action.directive;

import static com.itrip.common.web.freemarker.DirectiveUtils.OUT_LIST;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itrip.common.http_conn.HttpRequestUtils;
import com.itrip.common.web.freemarker.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 获取itrip相关线路推荐
 */
public class CmsItripTravelDirective implements TemplateDirectiveModel {

	/** 输入参数文章内容 */
	public static final String PARAM_TEXT = "key";

	/** 结果数 */
	public static final String COUNT = "count";

	/** 排序类型 */
	public static final String SORT_TYPE = "sortType";

	/** 排序desc/asc */
	public static final String SORT_ORDER = "sortOrder";

	/** 请求url */
	private static final String URL = "http://10.132.66.177:18014/main/search/listGuide";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String text = DirectiveUtils.getString(PARAM_TEXT, params);
		Object result = new ArrayList<String>();
		if (StringUtils.isNotBlank(text)) {
			text = text.split(",")[0];
			Integer sortType = DirectiveUtils.getInt(SORT_TYPE, params);
			Integer sortOrder = DirectiveUtils.getInt(SORT_ORDER, params);
			Integer total = DirectiveUtils.getInt(COUNT, params);
			sortType = sortType != null ? sortType : 3;// 默认评论数
			sortOrder = sortOrder != null ? sortOrder : 0;// 默认降序
			total = total != null ? total : 4;// 默认4条
			String resJSON = HttpRequestUtils.getInstance().getSendHttp(
					URL + "?wd=" + text + "&pageSize=" + total + "&sortType=" + sortType + "&sortOrder=" + sortOrder);
			if (resJSON != null) {
				JSONObject json = JSON.parseObject(resJSON);
				Object data = json.get("data");
				if (data != null && !"".equals(data)) {
					result = data;
				}
			}
		}
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(result));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}
}
