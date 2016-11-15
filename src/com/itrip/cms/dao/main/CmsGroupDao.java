package com.itrip.cms.dao.main;

import java.util.List;

import com.itrip.cms.entity.main.CmsGroup;
import com.itrip.common.hibernate3.Updater;

public interface CmsGroupDao {
	public List<CmsGroup> getList();

	public CmsGroup getRegDef();

	public CmsGroup findById(Integer id);

	public CmsGroup save(CmsGroup bean);

	public CmsGroup updateByUpdater(Updater<CmsGroup> updater);

	public CmsGroup deleteById(Integer id);
}