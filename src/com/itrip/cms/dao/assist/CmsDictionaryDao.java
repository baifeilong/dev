package com.itrip.cms.dao.assist;

import java.util.List;

import com.itrip.cms.entity.assist.CmsDictionary;
import com.itrip.common.hibernate3.Updater;
import com.itrip.common.page.Pagination;

public interface CmsDictionaryDao {
	public Pagination getPage(String queryType, int pageNo, int pageSize);

	public List<CmsDictionary> getList(String type);

	public List<String> getTypeList();

	public CmsDictionary findById(Integer id);

	public CmsDictionary findByValue(String type, String value);

	public CmsDictionary save(CmsDictionary bean);

	public CmsDictionary updateByUpdater(Updater<CmsDictionary> updater);

	public CmsDictionary deleteById(Integer id);

	public int countByValue(String value, String type);
}