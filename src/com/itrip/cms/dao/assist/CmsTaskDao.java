package com.itrip.cms.dao.assist;

import java.util.List;

import com.itrip.cms.entity.assist.CmsTask;
import com.itrip.common.hibernate3.Updater;
import com.itrip.common.page.Pagination;

public interface CmsTaskDao {
	public Pagination getPage(Integer siteId, int pageNo, int pageSize);

	public List<CmsTask> getList();

	public CmsTask findById(Integer id);

	public CmsTask save(CmsTask bean);

	public CmsTask updateByUpdater(Updater<CmsTask> updater);

	public CmsTask deleteById(Integer id);
}