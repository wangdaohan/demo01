package com.demo.modules.sys.manager;

import com.demo.common.entity.Query;
import com.demo.modules.sys.entity.SysMenuEntity;

import java.util.List;

/**
 * 系统菜单
 *
 * @author Centling Techonlogies
 * @email xxx@demo.com
 * @url www.demo.com
 * @date 2017年8月10日 上午10:34:59
 */
public interface SysMenuManager {
	
	List<SysMenuEntity> listUserMenu(Long userId);
	
	List<SysMenuEntity> listParentId(Long parentId, List<Long> menuIdList);
	
	List<SysMenuEntity> listMenu(Query search);
	
	List<SysMenuEntity> listNotButton();
	
	int saveMenu(SysMenuEntity menu);

	SysMenuEntity getMenuById(Long id);
	
	int updateMenu(SysMenuEntity menu);
	
	int batchRemove(Long[] id);
	
	boolean hasChildren(Long[] id);
	
}
