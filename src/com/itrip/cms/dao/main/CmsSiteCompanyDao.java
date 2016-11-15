package com.itrip.cms.dao.main;

import com.itrip.cms.entity.main.CmsSiteCompany;
import com.itrip.common.hibernate3.Updater;

public interface CmsSiteCompanyDao {

	public CmsSiteCompany save(CmsSiteCompany bean);

	public CmsSiteCompany updateByUpdater(Updater<CmsSiteCompany> updater);
}