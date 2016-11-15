package com.itrip.cms.dao.main;

import com.itrip.cms.entity.main.ChannelTxt;
import com.itrip.common.hibernate3.Updater;

public interface ChannelTxtDao {
	public ChannelTxt findById(Integer id);

	public ChannelTxt save(ChannelTxt bean);

	public ChannelTxt updateByUpdater(Updater<ChannelTxt> updater);
}