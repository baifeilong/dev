package com.itrip.cms.manager.main;

import java.io.IOException;
import java.util.List;

import com.itrip.cms.entity.main.CmsSite;
import com.itrip.cms.entity.main.CmsUser;

public interface CmsSiteMng {
	public List<CmsSite> getList();

	public List<CmsSite> getListFromCache();

	public CmsSite findByDomain(String domain, boolean cacheable);

	public CmsSite findById(Integer id);

	public CmsSite save(CmsSite currSite, CmsUser currUser, CmsSite bean, Integer uploadFtpId) throws IOException;

	public CmsSite update(CmsSite bean, Integer uploadFtpId);

	/**
	 * 
	 * @Title: updateTplSolution
	 * @Description: 更新模板方案
	 * @param siteId
	 *            站点ID
	 * @param solution
	 *            模板方案名称
	 * @return void 返回类型
	 */
	public void updateTplSolution(Integer siteId, String solution);

	/**
	 * @Title: updateTplMobileSolution
	 * @Description: 更新移动端模板方案
	 * @param siteId
	 *            站点ID
	 * @param solution
	 *            模板方案名称
	 * @return void 返回类型
	 */
	public void updateTplMobileSolution(Integer siteId, String solution);

	public CmsSite deleteById(Integer id);

	public CmsSite[] deleteByIds(Integer[] ids);
}