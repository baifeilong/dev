package com.itrip.cms.dao.main;

import com.itrip.cms.entity.main.ContentTxt;
import com.itrip.common.hibernate3.Updater;

public interface ContentTxtDao {
	public ContentTxt findById(Integer id);

	public ContentTxt save(ContentTxt bean);

	public ContentTxt updateByUpdater(Updater<ContentTxt> updater);
}