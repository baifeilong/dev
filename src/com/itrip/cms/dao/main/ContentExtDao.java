package com.itrip.cms.dao.main;

import com.itrip.cms.entity.main.ContentExt;
import com.itrip.common.hibernate3.Updater;

public interface ContentExtDao {
	public ContentExt findById(Integer id);

	public ContentExt save(ContentExt bean);

	public ContentExt updateByUpdater(Updater<ContentExt> updater);
}