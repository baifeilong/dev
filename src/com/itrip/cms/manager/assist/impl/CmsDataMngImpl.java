package com.itrip.cms.manager.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itrip.cms.dao.assist.CmsDataDao;
import com.itrip.cms.entity.back.CmsConstraints;
import com.itrip.cms.entity.back.CmsField;
import com.itrip.cms.entity.back.CmsTable;
import com.itrip.cms.manager.assist.CmsDataMng;

@Service
@Transactional
public class CmsDataMngImpl implements CmsDataMng {

	@Transactional(readOnly = true)
	public List<CmsTable> listTabels() {
		return dao.listTables();
	}

	@Transactional(readOnly = true)
	public CmsTable findTable(String tablename) {
		return dao.findTable(tablename);
	}

	@Transactional(readOnly = true)
	public List<CmsField> listFields(String tablename) {
		return dao.listFields(tablename);
	}

	@Transactional(readOnly = true)
	public List<CmsConstraints> listConstraints(String tablename) {
		return dao.listConstraints(tablename);
	}


	private CmsDataDao dao;

	@Autowired
	public void setDao(CmsDataDao dao) {
		this.dao = dao;
	}


}