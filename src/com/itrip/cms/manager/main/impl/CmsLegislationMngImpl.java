package com.itrip.cms.manager.main.impl;

import com.itrip.cms.dao.main.CmsLegislationDao;
import com.itrip.cms.entity.main.CmsLegislation;
import com.itrip.cms.manager.main.CmsLegislationMng;
import com.itrip.common.hibernate3.Updater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CmsLegislationMngImpl implements CmsLegislationMng {

    private static final Logger log = LoggerFactory.getLogger(CmsLegislationMngImpl.class);

    private final CmsLegislationDao dao;

    @Autowired
    public CmsLegislationMngImpl(CmsLegislationDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public List<CmsLegislation> getList() {
        return dao.getList(false);
    }

    @Transactional(readOnly = true)
    public List<CmsLegislation> getListFromCache() {
        return dao.getList(true);
    }

    @Transactional(readOnly = true)
    public CmsLegislation findById(Integer id) {
        return dao.findById(id);
    }

    public CmsLegislation save(CmsLegislation bean){
        dao.save(bean);
        return bean;
    }

    public CmsLegislation update(CmsLegislation bean) {
        Updater<CmsLegislation> updater = new Updater<CmsLegislation>(bean);
        return dao.updateByUpdater(updater);
    }

    public CmsLegislation deleteById(Integer id) {
        return dao.deleteById(id);
    }

    public CmsLegislation[] deleteByIds(Integer[] ids) {
        CmsLegislation[] beans = new CmsLegislation[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }
}