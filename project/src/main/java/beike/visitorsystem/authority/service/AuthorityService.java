package beike.visitorsystem.authority.service;
import java.math.BigInteger;
import java.util.List;

import beike.visitorsystem.authority.model.AdminAction;
import beike.visitorsystem.authority.model.AdminLog;
import beike.visitorsystem.authority.model.AdminUser;
import beike.visitorsystem.authority.model.Role;
import beike.visitorsystem.authority.model.Settings;

public interface AuthorityService {

	/**
	 * 新增管理员
	 * @param adminUser
	 * @return AdminUser
	 */
	public boolean addAdminUser(String username,String password,String staffId);
	/**
	 * 修改管理员密码
	 * @param id
	 * @param password
	 * @return
	 */
	public boolean editPassword(BigInteger id,String password);
	/**
	 * 禁用账户
	 * @param id  管理员id
	 * @parem isBanned 是否禁用
	 * @return true or false
	 */
	public boolean banAdminUser(BigInteger id,int isBanned);
	/**
	 * 获取管理员列表(no Filter)
	 * @param page  页数
	 * @param number  显示条数
	 * @return List<AdminUser>
	 */
	public List<AdminUser> getAdminUserList(int page,int number);
	/**
	 * 获取管理员列表(filter)
	 * @param roleId  角色id
	 * @param username  用户名
	 * @param start  登录时间小于
	 * @param end  登录时间大于
	 * @param page  页数
	 * @param number  条目数
	 * @return List<AdminUser>
	 */
	public List<AdminUser> getAdminUserListFilter(BigInteger staffId,BigInteger roleId,String username,String start,String end,int page,int number);
	/**
	 * 获取角色列表
	 * @param page 页数
	 * @param number  条数
	 * @return List<Role>
	 */
	public List<Role> getRoleList(int page,int number);
	/**
	 * 获取所有角色
	 * @return  List<Role>
	 */
	public List<Role> getRoleListAll();
	/**
	 * 登录后修改用户记录
	 * @param id  用户id
	 * @param ip  登陆时ip
	 * @return  true or false
	 */
	public boolean dealLogin(BigInteger id,String ip);
	/**
	 * 获得一个管理员实例
	 * @param username  用户名
	 * @return 管理员实例
	 */
	public AdminUser getAdminUser(String username);
	/**
	 * 通过STAFFID获得一个管理员实例
	 * @param staffId  
	 * @return
	 */
	public AdminUser getAdminUserByStaffId(String staffId);
	/**
	 * 获得操作记录
	 * @param page  页数
	 * @param number 条数
	 * @return 记录列表
	 */
	public List<AdminLog> getAdminLogList(String username,String description,String start,String end,int page,int number);
	/**
	 * 插入一条操作记录
	 * @param log 实例
	 * @return true or false
	 */
	public boolean addAdminLog(AdminLog log);
	/**
	 * 修改管理员角色
	 * @param adminUserId  管理员id
	 * @param roleList   角色id的String   ->   1,2,3,4   形式
	 * @return  true or false
	 */
	public boolean editAdminUserRole(BigInteger id,String roleList);
	/**
	 * 删除一个角色
	 * @param id 角色id
	 * @return true or false
	 */
	public boolean deleteRole(BigInteger id);
	/**
	 * 获取所有权限
	 * @return  权限List
	 */
	public List<AdminAction> getAdminActionListAll();
	/**
	 * 修改角色权限
	 * @param roleId  角色id
	 * @param actionList  权限id的字符串
	 * @return  是/否
	 */
	public boolean editRole(BigInteger id,String actionList,String roleName,String roleDescription);
	/**
	 * 通过角色id获取该角色
	 * @param id  角色id
	 * @return 角色对象
	 */
	public Role getRoleByRoleId(BigInteger id);
	/**
	 * 检查是否已经存在该角色名字
	 * @param name
	 * @return
	 */
	public boolean checkRoleName(String name);
	/**
	 * 新增角色
	 * @param roleName  角色代码
	 * @param roleDescription  角色描述
	 * @param actionList 权限列表
	 * @return
	 */
	public boolean addRole(String roleName,String roleDescription,String actionList);
	/**
	 * 得到所有的设置
	 * @return
	 */
	public List<Settings> selectSettings();
	/**
	 * 修改一个设置
	 * @param id   设置的id
	 * @param value   设置的值
	 * @return  
	 */
	public boolean updateSettings(BigInteger id,String value);

	public int getSumAdminUser(String username,BigInteger staffId,BigInteger roleId,String start,String end);

	public int getSumLogs(String username,String content,String start,String end);
}
