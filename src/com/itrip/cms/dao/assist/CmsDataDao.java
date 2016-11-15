package com.itrip.cms.dao.assist;

import java.util.List;

import com.itrip.cms.entity.back.CmsConstraints;
import com.itrip.cms.entity.back.CmsField;
import com.itrip.cms.entity.back.CmsTable;

public interface CmsDataDao {
	public List<CmsTable> listTables();

	public List<CmsField> listFields(String tablename);

	public List<CmsConstraints> listConstraints(String tablename);

	public CmsTable findTable(String tablename);

}