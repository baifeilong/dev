package com.itrip.cms.dao.main;

import com.itrip.cms.entity.main.CmsLegislation;
import com.itrip.cms.entity.main.CmsSite;
import com.itrip.common.hibernate3.Updater;

import java.util.List;

public interface CmsLegislationDao {
    /**
     * 获得站点数量
     *
     * @param cacheable
     * @return
     */
    public int siteCount(boolean cacheable);

    public List<CmsLegislation> getList(boolean cacheable);

    public CmsLegislation findByDomain(String domain, boolean cacheable);

    public CmsLegislation findById(Integer id);

    public CmsLegislation save(CmsLegislation bean);

    public CmsLegislation updateByUpdater(Updater<CmsLegislation> updater);

    public CmsLegislation deleteById(Integer id);
}