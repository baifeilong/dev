package com.itrip.cms.action.admin.main;

import com.itrip.cms.entity.main.CmsLegislation;
import com.itrip.cms.manager.main.CmsLegislationMng;
import com.itrip.cms.manager.main.CmsLogMng;
import com.itrip.cms.web.WebErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class CmsLegislationAct {

    private static final Logger log = LoggerFactory.getLogger(CmsLegislationAct.class);

    private final CmsLogMng cmsLogMng;
    private final CmsLegislationMng manager;

    @Autowired
    public CmsLegislationAct(CmsLogMng cmsLogMng, CmsLegislationMng manager) {
        this.cmsLogMng = cmsLogMng;
        this.manager = manager;
    }

    @RequestMapping("/legislation/v_list.do")
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        List<CmsLegislation> list = manager.getList();
        model.addAttribute("list", list);
        return "legislation/list";
    }

    @RequestMapping("/legislation/v_add.do")
    public String add(ModelMap model) {
        return "legislation/add";
    }

    @RequestMapping("/legislation/v_edit.do")
    public String edit(Integer id, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("cmsLegislation", manager.findById(id));
        return "legislation/edit";
    }

    @RequestMapping("/legislation/o_save.do")
    public String save(CmsLegislation bean, HttpServletRequest request) throws IOException {
        bean = manager.save(bean);
        log.info("save CmsLegislation id={}", bean.getId());
        cmsLogMng.operating(request, "cmsLegislation.log.save", "id=" + bean.getId() + ";name=" + bean.getName());
        return "redirect:v_list.do";
    }

    @RequestMapping("/legislation/o_update.do")
    public String update(CmsLegislation bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = manager.update(bean);
        log.info("update CmsLegislation id={}.", bean.getId());
        cmsLogMng.operating(request, "cmsLegislation.log.update", "id=" + bean.getId() + ";name=" + bean.getName());
        return list(pageNo, request, model);
    }

    @RequestMapping("/legislation/o_delete.do")
    public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        CmsLegislation[] beans = manager.deleteByIds(ids);
        for (CmsLegislation bean : beans) {
            log.info("delete CmsLegislation id={}", bean.getId());
            cmsLogMng.operating(request, "cmsLegislation.log.delete", "id=" + bean.getId() + ";name=" + bean.getName());
        }
        return list(pageNo, request, model);
    }

    private WebErrors validateEdit(Integer id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(id, errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(id, errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors.ifEmpty(ids, "ids");
        for (Integer id : ids) {
            vldExist(id, errors);
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        CmsLegislation entity = manager.findById(id);
        return errors.ifNotExist(entity, CmsLegislation.class, id);
    }
}