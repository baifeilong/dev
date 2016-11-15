package com.itrip.cms.manager.main;

import com.itrip.cms.entity.main.CmsSite;
import com.itrip.cms.entity.main.CmsSiteCompany;

public interface CmsSiteCompanyMng {
	public CmsSiteCompany save(CmsSite site,CmsSiteCompany bean);

	public CmsSiteCompany update(CmsSiteCompany bean);
}