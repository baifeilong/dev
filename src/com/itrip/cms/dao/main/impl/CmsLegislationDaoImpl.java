package com.itrip.cms.dao.main.impl;

import com.itrip.cms.dao.main.CmsLegislationDao;
import com.itrip.cms.entity.main.CmsLegislation;
import com.itrip.common.hibernate3.HibernateBaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CmsLegislationDaoImpl extends HibernateBaseDao<CmsLegislation, Integer> implements CmsLegislationDao {

    public int siteCount(boolean cacheable) {
        String hql = "select count(*) from CmsLegislation bean";
        return ((Number) getSession().createQuery(hql).setCacheable(cacheable).iterate().next()).intValue();
    }

    @SuppressWarnings("unchecked")
    public List<CmsLegislation> getList(boolean cacheable) {
        String hql = "from CmsLegislation bean order by bean.id asc";
        return getSession().createQuery(hql).setCacheable(cacheable).list();
    }

    public CmsLegislation findByDomain(String domain, boolean cacheable) {
        String hql = "from CmsLegislation bean where domain=:domain";
        Query query = getSession().createQuery(hql).setString("domain", domain);
        query.setCacheable(cacheable);
        return (CmsLegislation) query.uniqueResult();
    }

    public CmsLegislation findById(Integer id) {
        CmsLegislation entity = get(id);
        return entity;
    }

    public CmsLegislation save(CmsLegislation bean) {
        getSession().save(bean);
        return bean;
    }

    public CmsLegislation deleteById(Integer id) {
        CmsLegislation entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<CmsLegislation> getEntityClass() {
        return CmsLegislation.class;
    }
}