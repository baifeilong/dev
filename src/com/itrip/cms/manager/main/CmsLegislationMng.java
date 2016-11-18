package com.itrip.cms.manager.main;

import com.itrip.cms.entity.main.CmsLegislation;
import com.itrip.cms.entity.main.CmsUser;

import java.io.IOException;
import java.util.List;

public interface CmsLegislationMng {

    public List<CmsLegislation> getList();

    public CmsLegislation findById(Integer id);

    public CmsLegislation save(CmsLegislation bean);

    public CmsLegislation update(CmsLegislation bean);

    public CmsLegislation deleteById(Integer id);

    public CmsLegislation[] deleteByIds(Integer[] ids);

}