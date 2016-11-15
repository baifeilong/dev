package com.itrip.cms.action.admin.main;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itrip.cms.manager.main.DatabaseMng;

/**
 * 数据库操作action
 * 
 * @author Benny
 * 
 */
@Controller
public class DatabaseAct {

	private static final Logger log = LoggerFactory.getLogger(DatabaseAct.class);

	@RequestMapping("/database/v_execute.do")
	public String list(HttpServletRequest request, ModelMap model) {
		return "database/execute";
	}

	@RequestMapping("/database/o_execute.do")
	public String add(String sql, HttpServletRequest request, ModelMap model) {
		dbMng.execute(sql);
		model.addAttribute("sql", sql);
		model.addAttribute("message", "脚本执行成功！");
		log.info("execute sql ={}.", sql);
		return "database/execute";
	}

	@Autowired
	private DatabaseMng dbMng;
}