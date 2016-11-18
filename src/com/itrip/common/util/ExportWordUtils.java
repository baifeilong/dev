package com.itrip.common.util;

import com.itrip.cms.entity.main.CmsLegislation;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Map;

public class ExportWordUtils {

    private static final Logger log = LoggerFactory.getLogger(ExportWordUtils.class);

    /**
     * 导出word文档
     *
     * @param fileName 下载文件名称
     * @param tplName  模板名称
     * @param tplPath  模板目录
     * @param dataMap  导出数据
     * @param response
     */
    public static void createDoc(String fileName, String tplName, String tplPath, Map<String, CmsLegislation> dataMap, HttpServletResponse response) {
        response.setContentType("application/x-download;charset=UTF-8");
        try {
            fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        } catch (Exception e) {
            log.error("文件名编码出错！");
        }
        //给文件名加上时间戳
        fileName += DateUtils.getNowDateTime() + ".doc";
        response.addHeader("Content-disposition", "filename=\"" + fileName + "\";charset=UTF-8");
        Template template = null;
        try {
            //设置模本装置方法和路径，默认指定在/resources/exportWord目录下
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            configuration.setDirectoryForTemplateLoading(new File(tplPath));
            //装载模板
            template = configuration.getTemplate(tplName);
            template.setEncoding("UTF-8");
        } catch (IOException e) {
            log.error("导出word装载模版出错！");
            e.printStackTrace();
        }
        Writer out = null;
        try {
            //输出对象，并对文件名进行编码
            out = response.getWriter();
        } catch (IOException e) {
            log.error("导出word输出流创建错误！");
            e.printStackTrace();
        }
        try {
            //装载并输出数据，生成word文档
            if (template != null) {
                template.process(dataMap, out);
            }
        } catch (TemplateException e) {
            e.printStackTrace();
            log.error("导出word出错！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}