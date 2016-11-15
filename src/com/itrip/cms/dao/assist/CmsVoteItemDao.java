package com.itrip.cms.dao.assist;

import com.itrip.cms.entity.assist.CmsVoteItem;
import com.itrip.common.hibernate3.Updater;
import com.itrip.common.page.Pagination;

public interface CmsVoteItemDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsVoteItem findById(Integer id);

	public CmsVoteItem save(CmsVoteItem bean);

	public CmsVoteItem updateByUpdater(Updater<CmsVoteItem> updater);

	public CmsVoteItem deleteById(Integer id);
}