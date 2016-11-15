package com.itrip.cms.dao.assist.impl;

import org.springframework.stereotype.Repository;

import com.itrip.cms.dao.assist.CmsVoteReplyDao;
import com.itrip.cms.entity.assist.CmsVoteReply;
import com.itrip.common.hibernate3.Finder;
import com.itrip.common.hibernate3.HibernateBaseDao;
import com.itrip.common.page.Pagination;

@Repository
public class CmsVoteReplyDaoImpl extends HibernateBaseDao<CmsVoteReply, Integer> implements CmsVoteReplyDao {

	public Pagination getPage(Integer subTopicId, int pageNo, int pageSize) {
		String hql = "select bean from CmsVoteReply bean";
		Finder f = Finder.create(hql);
		if (subTopicId != null) {
			f.append(" where bean.subTopic.id=:subTopicId").setParam("subTopicId", subTopicId);
		}
		return find(f, pageNo, pageSize);
	}

	public CmsVoteReply findById(Integer id) {
		CmsVoteReply entity = get(id);
		return entity;
	}

	public CmsVoteReply save(CmsVoteReply bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsVoteReply deleteById(Integer id) {
		CmsVoteReply entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsVoteReply> getEntityClass() {
		return CmsVoteReply.class;
	}
}