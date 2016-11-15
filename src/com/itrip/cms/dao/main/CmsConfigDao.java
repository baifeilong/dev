package com.itrip.cms.dao.main;

import com.itrip.cms.entity.main.CmsConfig;
import com.itrip.common.hibernate3.Updater;

public interface CmsConfigDao {
	public CmsConfig findById(Integer id);

	public CmsConfig updateByUpdater(Updater<CmsConfig> updater);
}