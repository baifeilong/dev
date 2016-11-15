package com.itrip.cms.dao.main;

import com.itrip.cms.entity.main.CmsUserResume;
import com.itrip.common.hibernate3.Updater;

public interface CmsUserResumeDao {
	public CmsUserResume findById(Integer id);

	public CmsUserResume save(CmsUserResume bean);

	public CmsUserResume updateByUpdater(Updater<CmsUserResume> updater);
}