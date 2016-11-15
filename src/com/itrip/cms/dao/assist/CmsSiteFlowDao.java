package com.itrip.cms.dao.assist;

import com.itrip.cms.entity.assist.CmsSiteFlow;

public interface CmsSiteFlowDao {
	public CmsSiteFlow save(CmsSiteFlow cmsSiteFlow);

	public CmsSiteFlow findUniqueByProperties(Integer siteId, String accessDate, String sessionId, String page);
}
