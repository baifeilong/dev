package com.itrip.cms.manager.assist;

import com.itrip.cms.entity.assist.CmsGuestbook;
import com.itrip.cms.entity.assist.CmsGuestbookExt;

public interface CmsGuestbookExtMng {
	public CmsGuestbookExt save(CmsGuestbookExt ext, CmsGuestbook guestbook);

	public CmsGuestbookExt update(CmsGuestbookExt ext);
}