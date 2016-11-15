package com.itrip.cms.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itrip.cms.dao.main.DatabaseDao;
import com.itrip.cms.manager.main.DatabaseMng;

@Service
@Transactional
public class DatabaseMngImpl implements DatabaseMng {

	@Override
	public void execute(String sql) {
		// TODO Auto-generated method stub
		String[] sqls = sql.split(";");
		for (String subSql : sqls) {
			dao.execute(subSql);
		}
	}

	private DatabaseDao dao;

	@Autowired
	public void setDao(DatabaseDao dao) {
		this.dao = dao;
	}
}