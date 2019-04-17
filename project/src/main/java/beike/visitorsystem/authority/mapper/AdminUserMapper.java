package beike.visitorsystem.authority.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import beike.visitorsystem.authority.model.AdminAction;
import beike.visitorsystem.authority.model.AdminLog;
import beike.visitorsystem.authority.model.AdminUser;
import beike.visitorsystem.authority.model.Role;
import beike.visitorsystem.authority.model.Settings;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserMapper {
	
	public AdminUser getAdminUser(@Param("username")String username);
	public AdminUser getAdminUserById(@Param("id")BigInteger id); 
	public AdminUser getAdminUserByStaffId(@Param("staffId")String id);
	public boolean addAdminUser(@Param("adminUser")AdminUser adminUser);
	public boolean editPassword(@Param("password")String password,@Param("updateTime")String updateTime,@Param("id")BigInteger id,@Param("signature")String signature);
	public boolean banAdminUser(@Param("id")BigInteger id,@Param("updateTime")String updateTime,@Param("isBanned")int isBanned,@Param("signature")String signature);
	public boolean dealLogin(@Param("id")BigInteger id,@Param("lastIp")String ip,@Param("lastLogin")String lastLogin);
	public List<AdminUser> getAdminUserList(@Param("count")int count,@Param("number")int number);
	public List<AdminUser> getAdminUserListFilter(@Param("staffId")BigInteger staffId,@Param("roleId")BigInteger roleId,@Param("username")String username,@Param("start")String start,@Param("end")String end,@Param("count")int count,@Param("number")int number);
	public List<Role> getRoleList(@Param("count")int count,@Param("number")int number);
	public List<Role> getRoleListAll();
	public List<AdminLog> getAdminLogList(@Param("username")String username,@Param("description")String description,@Param("start")String start,@Param("end")String end,@Param("count")int count,@Param("number")int number);
	public boolean deleteRole(@Param("updateTime")String updateTime,@Param("id")BigInteger id);
	public List<AdminAction> getAdminActionListAll();
	public Role getRoleByRoleId(@Param("id")BigInteger id);
	public Role getRoleByRoleName(@Param("name")String name);
	public boolean addAdminLog(@Param("log")AdminLog log);
	public boolean editAdminUserRole(@Param("id")BigInteger id,@Param("roleList")String roleList,@Param("updateTime")String updateTime,@Param("signature")String signature);
	public boolean editRole(@Param("id")BigInteger id,@Param("roleName")String roleName,@Param("roleDescription")String description,@Param("actionList")String actionList,@Param("updateTime")String updateTime);
	public boolean addRole(@Param("id")BigInteger id,@Param("roleName")String roleName,@Param("roleDescription")String roleDescription,@Param("actionList")String actionList,@Param("createTime")String createTime,@Param("updateTime")String updateTime,@Param("isDeleted")int isDeleted);
	public List<Settings> selectSettings();
	public boolean updateSettings(@Param("value")String value,@Param("updateTime")String updateTime,@Param("id")BigInteger id);
	public int getSumAdminUser(@Param("username")String username,@Param("staffId")BigInteger staffId,@Param("roleId")BigInteger roleId,@Param("start")String start,@Param("end")String end);
	public int getSumLogs(@Param("username")String username,@Param("content")String content,@Param("start")String start,@Param("end")String end);
	public Settings getVersionIntroduction();
}
