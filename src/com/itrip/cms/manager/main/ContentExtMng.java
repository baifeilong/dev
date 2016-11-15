package com.itrip.cms.manager.main;

import com.itrip.cms.entity.main.Content;
import com.itrip.cms.entity.main.ContentExt;

public interface ContentExtMng {
	public ContentExt save(ContentExt ext, Content content);

	public ContentExt update(ContentExt ext);
}