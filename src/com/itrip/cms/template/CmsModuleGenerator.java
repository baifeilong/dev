package com.itrip.cms.template;


public class CmsModuleGenerator {
	private static String packName = "com.itrip.cms.template";
	private static String fileName = "jeecms.properties";

	public static void main(String[] args) {
		new ModuleGenerator(packName, fileName).generate();
	}
}
