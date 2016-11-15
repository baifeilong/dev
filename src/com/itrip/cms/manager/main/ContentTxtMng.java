package com.itrip.cms.manager.main;

import com.itrip.cms.entity.main.Content;
import com.itrip.cms.entity.main.ContentTxt;

public interface ContentTxtMng {
	public ContentTxt save(ContentTxt txt, Content content);

	public ContentTxt update(ContentTxt txt, Content content);
}