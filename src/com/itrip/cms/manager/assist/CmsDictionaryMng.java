package com.itrip.cms.manager.assist;

import java.util.List;

import com.itrip.common.page.Pagination;
import com.itrip.cms.entity.assist.CmsDictionary;

public interface CmsDictionaryMng {
	public Pagination getPage(String queryType,int pageNo, int pageSize);
	
	public List<CmsDictionary> getList(String queryType);
	
	public List<String> getTypeList();

	public CmsDictionary findById(Integer id);
	
	public CmsDictionary findByValue(String type,String value);

	public CmsDictionary save(CmsDictionary bean);

	public CmsDictionary update(CmsDictionary bean);

	public CmsDictionary deleteById(Integer id);
	
	public CmsDictionary[] deleteByIds(Integer[] ids);
	
	public boolean dicDeplicateValue(String value,String type);
}