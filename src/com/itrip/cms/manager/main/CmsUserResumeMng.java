package com.itrip.cms.manager.main;

import com.itrip.cms.entity.main.CmsUser;
import com.itrip.cms.entity.main.CmsUserResume;

public interface CmsUserResumeMng {
	public CmsUserResume save(CmsUserResume ext, CmsUser user);

	public CmsUserResume update(CmsUserResume ext, CmsUser user);
}