package com.itrip.cms.dao.main;

import com.itrip.cms.entity.main.ContentCheck;
import com.itrip.common.hibernate3.Updater;

public interface ContentCheckDao {
	public ContentCheck findById(Long id);

	public ContentCheck save(ContentCheck bean);

	public ContentCheck updateByUpdater(Updater<ContentCheck> updater);
}