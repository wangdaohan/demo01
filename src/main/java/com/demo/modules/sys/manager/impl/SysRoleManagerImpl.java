package com.demo.modules.sys.manager.impl;

import com.demo.common.entity.Page;
import com.demo.common.entity.Query;
import com.demo.modules.sys.dao.SysRoleMapper;
import com.demo.modules.sys.dao.SysRoleMenuMapper;
import com.demo.modules.sys.dao.SysRoleOrgMapper;
import com.demo.modules.sys.dao.SysUserRoleMapper;
import com.demo.modules.sys.entity.SysRoleEntity;
import com.demo.modules.sys.manager.SysRoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统角色
 *
 * @author Centling Techonlogies
 * @email xxx@demo.com
 * @url www.demo.com
 * @date 2017年8月12日 上午12:39:48
 */
@Component("sysRoleManager")
public class SysRoleManagerImpl implements SysRoleManager {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;
	
	@Autowired
	private SysRoleOrgMapper sysRoleOrgMapper;

	@Override
	public List<SysRoleEntity> listRole(Page<SysRoleEntity> page, Query search) {
		return sysRoleMapper.listForPage(page, search);
	}

	@Override
	public int saveRole(SysRoleEntity role) {
		return sysRoleMapper.save(role);
	}

	@Override
	public SysRoleEntity getRoleById(Long id) {
		SysRoleEntity role = sysRoleMapper.getObjectById(id);
		List<Long> menuId = sysRoleMenuMapper.listMenuId(id);
		List<Long> orgId = sysRoleOrgMapper.listOrgId(id);
		role.setMenuIdList(menuId);
		role.setOrgIdList(orgId);
		return role;
	}

	@Override
	public int updateRole(SysRoleEntity role) {
		return sysRoleMapper.update(role);
	}

	@Override
	public int batchRemove(Long[] id) {
		int count = sysRoleMapper.batchRemove(id);
		sysUserRoleMapper.batchRemoveByRoleId(id);
		sysRoleMenuMapper.batchRemoveByRoleId(id);
		sysRoleOrgMapper.batchRemoveByRoleId(id);
		return count;
	}

	@Override
	public List<SysRoleEntity> listRole() {
		return sysRoleMapper.list();
	}

	@Override
	public int updateRoleOptAuthorization(SysRoleEntity role) {
		Long roleId = role.getRoleId();
		int count = sysRoleMenuMapper.remove(roleId);
		Query query = new Query();
		query.put("roleId", roleId);
		List<Long> menuId = role.getMenuIdList();
		if(menuId.size() > 0) {
			query.put("menuIdList", role.getMenuIdList());
			count = sysRoleMenuMapper.save(query);
		}
		return count;
	}
	
	@Override
	public int updateRoleDataAuthorization(SysRoleEntity role) {
		Long roleId = role.getRoleId();
		int count = sysRoleOrgMapper.remove(roleId);
		Query query = new Query();
		query.put("roleId", roleId);
		List<Long> orgId = role.getOrgIdList();
		if(orgId.size() > 0) {
			query.put("orgIdList", role.getOrgIdList());
			count = sysRoleOrgMapper.save(query);
		}
		return count;
	}

	@Override
	public int saveUserRole(Long userId) {
		return sysUserRoleMapper.saveUserRole(userId);
	}

}
