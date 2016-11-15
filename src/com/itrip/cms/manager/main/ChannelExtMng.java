package com.itrip.cms.manager.main;

import com.itrip.cms.entity.main.Channel;
import com.itrip.cms.entity.main.ChannelExt;

public interface ChannelExtMng {
	public ChannelExt save(ChannelExt ext, Channel channel);

	public ChannelExt update(ChannelExt ext);
}