package com.itrip.cms.dao.main;

import com.itrip.cms.entity.main.CmsUserExt;
import com.itrip.common.hibernate3.Updater;

public interface CmsUserExtDao {
	public CmsUserExt findById(Integer id);

	public CmsUserExt save(CmsUserExt bean);

	public CmsUserExt updateByUpdater(Updater<CmsUserExt> updater);
}