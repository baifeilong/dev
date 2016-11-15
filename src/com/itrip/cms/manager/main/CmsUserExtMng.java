package com.itrip.cms.manager.main;

import com.itrip.cms.entity.main.CmsUser;
import com.itrip.cms.entity.main.CmsUserExt;

public interface CmsUserExtMng {
	public CmsUserExt save(CmsUserExt ext, CmsUser user);

	public CmsUserExt update(CmsUserExt ext, CmsUser user);
}