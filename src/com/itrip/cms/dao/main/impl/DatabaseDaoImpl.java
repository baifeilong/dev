package com.itrip.cms.dao.main.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.itrip.cms.dao.main.DatabaseDao;

@Repository
public class DatabaseDaoImpl extends JdbcDaoSupport implements DatabaseDao {

	@Override
	public void execute(String sql) {
		// TODO Auto-generated method stub
		getJdbcTemplate().execute(sql);
	}
}