package com.itrip.cms.dao.main;

import java.util.List;

import com.itrip.cms.entity.main.CmsRole;
import com.itrip.common.hibernate3.Updater;

public interface CmsRoleDao {
	public List<CmsRole> getList();

	public CmsRole findById(Integer id);

	public CmsRole save(CmsRole bean);

	public CmsRole updateByUpdater(Updater<CmsRole> updater);

	public CmsRole deleteById(Integer id);
}