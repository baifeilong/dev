package com.itrip.cms.action.directive;

import static com.itrip.common.web.freemarker.DirectiveUtils.OUT_LIST;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itrip.cms.action.directive.abs.AbstractContentDirective;
import com.itrip.cms.entity.main.Content;
import com.itrip.common.web.freemarker.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 内容列表标签 根据id获取内容
 */
public class ContentListByIdsDirective extends AbstractContentDirective {

	/** 输入参数，文章ID。允许多个文章ID，用","分开。排斥其他所有筛选参数。 */
	public static final String PARAM_IDS = "ids";

	/** 总数 */
	public static final String COUNT = "count";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<Content> list = new ArrayList<Content>();
		Integer[] ids = DirectiveUtils.getIntArray(PARAM_IDS, params);
		Integer count = DirectiveUtils.getInt(COUNT, params);
		count = count == null ? ids.length : count;
		if (ids != null) {
			for (Integer id : ids) {
				Content obj = contentMng.findById(id);
				if (obj != null) {
					list.add(obj);
					count--;
				}
				if (count == 0) {
					break;
				}
			}
		}
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	@Override
	protected boolean isPage() {
		// TODO Auto-generated method stub
		return false;
	}
}
