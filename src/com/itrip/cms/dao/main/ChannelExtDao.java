package com.itrip.cms.dao.main;

import com.itrip.cms.entity.main.ChannelExt;
import com.itrip.common.hibernate3.Updater;

public interface ChannelExtDao {
	public ChannelExt save(ChannelExt bean);

	public ChannelExt updateByUpdater(Updater<ChannelExt> updater);
}