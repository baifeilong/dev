package com.itrip.cms.action.admin.main;

import com.itrip.cms.entity.main.CmsLegislation;
import com.itrip.cms.manager.main.CmsLegislationMng;
import com.itrip.cms.manager.main.CmsLogMng;
import com.itrip.cms.web.WebErrors;
import com.itrip.common.util.DateFormatUtils;
import com.itrip.common.util.DateUtils;
import com.itrip.common.util.ExportWordUtils;
import com.itrip.common.web.springmvc.RealPathResolver;
import org.omg.CORBA.DATA_CONVERSION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/legislation")
public class CmsLegislationAct {

    private static final Logger log = LoggerFactory.getLogger(CmsLegislationAct.class);

    private final CmsLogMng cmsLogMng;
    private final CmsLegislationMng manager;
    private final RealPathResolver realPathResolver;

    @Autowired
    public CmsLegislationAct(CmsLogMng cmsLogMng, CmsLegislationMng manager, RealPathResolver realPathResolver) {
        this.cmsLogMng = cmsLogMng;
        this.manager = manager;
        this.realPathResolver = realPathResolver;
    }

    @RequestMapping("/exportWord.do")
    public void exportWord(Integer id, HttpServletRequest request, HttpServletResponse response) {
        CmsLegislation legislation = manager.findById(id);
        legislation.setWordTime(DateUtils.getNowCHSDate());
        Map<String, CmsLegislation> dataMap = new HashMap<String, CmsLegislation>();
        dataMap.put("bean", legislation);
        String tplPath = realPathResolver.get("/WEB-INF/word_tpls");
        ExportWordUtils.createDoc("被执行人财产状况表", "legislation.xml", tplPath, dataMap, response);
    }

    @RequestMapping("/v_list.do")
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        List<CmsLegislation> list = manager.getList();
        model.addAttribute("list", list);
        return "legislation/list";
    }

    @RequestMapping("/v_add.do")
    public String add(ModelMap model) {
        return "legislation/add";
    }

    @RequestMapping("/v_edit.do")
    public String edit(Integer id, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("cmsLegislation", manager.findById(id));
        return "legislation/edit";
    }

    @RequestMapping("/o_save.do")
    public String save(CmsLegislation bean, HttpServletRequest request) throws IOException {
        bean = manager.save(bean);
        log.info("save CmsLegislation id={}", bean.getId());
        cmsLogMng.operating(request, "cmsLegislation.log.save", "id=" + bean.getId() + ";name=" + bean.getName());
        return "redirect:v_list.do";
    }

    @RequestMapping("/o_update.do")
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

    @RequestMapping("/o_delete.do")
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