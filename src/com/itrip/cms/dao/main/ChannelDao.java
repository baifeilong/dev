package com.itrip.cms.dao.main;

import java.util.List;

import com.itrip.cms.entity.main.Channel;
import com.itrip.common.hibernate3.Updater;
import com.itrip.common.page.Pagination;

public interface ChannelDao {
	public List<Channel> getTopList(Integer siteId, boolean hasContentOnly, boolean displayOnly, boolean cacheable);

	public Pagination getTopPage(Integer siteId, boolean hasContentOnly, boolean displayOnly, boolean cacheable, int pageNo, int pageSize);

	public List<Channel> getTopListByRigth(Integer userId, Integer siteId, boolean hasContentOnly);

	public List<Channel> getChildList(Integer parentId, boolean hasContentOnly, boolean displayOnly, boolean cacheable);

	public Pagination getChildPage(Integer parentId, boolean hasContentOnly, boolean displayOnly, boolean cacheable, int pageNo, int pageSize);

	public List<Channel> getChildListByRight(Integer userId, Integer parentId, boolean hasContentOnly);

	public Channel findByPath(String path, Integer siteId, boolean cacheable);

	public Channel findById(Integer id);

	public Channel save(Channel bean);

	public Channel updateByUpdater(Updater<Channel> updater);

	public Channel deleteById(Integer id);

	/**
	 * 旧版攻略平台301跳转获取新版攻略平台栏目地址
	 * 
	 * @param cpath
	 *            栏目静态路径
	 * @return
	 */
	public String findByOldPath(String cpath);
}