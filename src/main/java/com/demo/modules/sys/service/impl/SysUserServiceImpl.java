package com.demo.modules.sys.service.impl;

import com.demo.common.entity.Page;
import com.demo.common.entity.Query;
import com.demo.common.entity.R;
import com.demo.common.utils.CommonUtils;
import com.demo.common.utils.MD5Utils;
import com.demo.modules.sys.entity.SysUserEntity;
import com.demo.modules.sys.entity.SysUserTokenEntity;
import com.demo.modules.sys.manager.SysUserManager;
import com.demo.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;
import java.util.Set;

/**
 * 系统用户
 *
 * @author Centling Techonlogies
 * @email xxx@demo.com
 * @url www.demo.com
 * @date 2017年8月11日 上午11:47:17
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserManager sysUserManager;
	
	@Override
	public Page<SysUserEntity> listUser(Map<String, Object> params) {
		Query form = new Query(params);
		Page<SysUserEntity> page = new Page<>(form);
		sysUserManager.listUser(page, form);
		return page;
	}

	@Override
	public R saveUser(SysUserEntity user) {
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		int count = sysUserManager.saveUser(user);
		return CommonUtils.msg(count);
	}

	@Override
	public R getUserById(Long userId) {
		SysUserEntity user = sysUserManager.getById(userId);
		return CommonUtils.msg(user);
	}

	@Override
	public R updateUser(SysUserEntity user) {
		int count = sysUserManager.updateUser(user);
		return CommonUtils.msg(count);
	}

	@Override
	public R batchRemove(Long[] id) {
		int count = sysUserManager.batchRemove(id);
		return CommonUtils.msg(count);
	}

	@Override
	public R listUserPerms(Long userId) {
		Set<String> perms = sysUserManager.listUserPerms(userId);
		return CommonUtils.msgNotCheckNull(perms);
	}

	@Override
	public R updatePswdByUser(SysUserEntity user) {
		String username = user.getUsername();
		String pswd = user.getPassword();
		String newPswd = user.getEmail();
		pswd = MD5Utils.encrypt(username, pswd);
		newPswd = MD5Utils.encrypt(username, newPswd);
		Query query = new Query();
		query.put("userId", user.getUserId());
		query.put("pswd", pswd);
		query.put("newPswd", newPswd);
		int count = sysUserManager.updatePswdByUser(query);
		if(!CommonUtils.isIntThanZero(count)) {
			return R.error("原密码错误");
		}
		return CommonUtils.msg(count);
	}

	@Override
	public R updateUserEnable(Long[] id) {
		int count = sysUserManager.updateUserEnable(id);
		return CommonUtils.msg(id, count);
	}

	@Override
	public R updateUserDisable(Long[] id) {
		int count = sysUserManager.updateUserDisable(id);
		return CommonUtils.msg(id, count);
	}

	@Override
	public R updatePswd(SysUserEntity user) {
		//SysUserEntity currUser = sysUserManager.getUserById(user.getUserId());
		//SysUserEntity currUser=sysUserManager.getObjectByTelephone(user.getMobile());
		// user.setPassword(MD5Utils.encrypt(currUser.getUsername(), user.getPassword()));
		int count = sysUserManager.updatePswd(user);
		return CommonUtils.msg(count);
	}

	@Override
	public R saveUserToken(Long userId) {
		SysUserTokenEntity token = sysUserManager.saveUserToken(userId);
		R r = R.ok().put("token", token.getToken()).put("expire", token.getGmtExpire());
		return r;
	}

	@Override
	public R saveUserToken(SysUserEntity user) {
		SysUserTokenEntity token = sysUserManager.saveUserToken(user);
		R r = R.ok().put("token", token.getToken()).put("expire", token.getGmtExpire());
		return r;
	}

	@Override
	public R updateUserToken(Long userId) {
		int count = sysUserManager.updateUserToken(userId);
		return CommonUtils.msg(count);
	}

	@Override
	public SysUserEntity getByUserName(String username) {
		return sysUserManager.getByUserName(username);
	}

	@Override
	public int getObjectByTelephone(String telephone) {

		return sysUserManager.getObjectByTelephone(telephone);
	}

}
