package beike.visitorsystem.authority.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @email:ping982313926@qq.com
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import beike.visitorsystem.authority.mapper.AdminUserMapper;
import beike.visitorsystem.authority.model.AdminAction;
import beike.visitorsystem.authority.model.AdminLog;
import beike.visitorsystem.authority.model.AdminUser;
import beike.visitorsystem.authority.model.Role;
import beike.visitorsystem.authority.model.Settings;
import beike.visitorsystem.utils.GenerateID;
import redis.clients.jedis.Jedis;

@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService{

	@Autowired
	private AdminUserMapper adminUserMapper;
	@Autowired
	GenerateID generateID;
	Jedis jedis = new Jedis("172.27.65.63",6379);
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	
	@Override
	public boolean addAdminUser(String username,String password,String staffId) {
		// TODO Auto-generated method stub
		AdminUser adminUser = new AdminUser();
		adminUser.setId(generateID.getID());
		adminUser.setIsBanned(1);
		adminUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		adminUser.setUsername(username);
		adminUser.setStaffId(staffId);
		adminUser.setCreateTime(df.format(new Date()).toString());
		adminUser.setUpdateTime(df.format(new Date()).toString());
		adminUser.setSignature(md5(adminUser.getId() + adminUser.getUsername() + adminUser.getPassword() + adminUser.getStaffId() + listToString(adminUser.getRoleList(),',') + adminUser.getIsBanned()));
		
		return adminUserMapper.addAdminUser(adminUser);
	}
	@Override
	public boolean editPassword(BigInteger id, String password) {
		// TODO Auto-generated method stub
		AdminUser adminUser = adminUserMapper.getAdminUserById(id);
		adminUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		String signature = md5(adminUser.getId() + adminUser.getUsername() + adminUser.getPassword() + adminUser.getStaffId() + listToString(adminUser.getRoleList(),',') + adminUser.getIsBanned());
		
		return adminUserMapper.editPassword(adminUser.getPassword(), df.format(new Date()).toString(), id,signature);
	}
	@Override
	public boolean banAdminUser(BigInteger id, int isBanned) {
		// TODO Auto-generated method stub
		AdminUser adminUser = adminUserMapper.getAdminUserById(id);
		adminUser.setIsBanned(isBanned);
		String signature = md5(adminUser.getId() + adminUser.getUsername() + adminUser.getPassword() + adminUser.getStaffId() + listToString(adminUser.getRoleList(),',') + adminUser.getIsBanned());
		
		return adminUserMapper.banAdminUser(id, df.format(new Date()).toString(), isBanned,signature);
	}
	@Override
	public List<AdminUser> getAdminUserList(int page, int number) {
		// TODO Auto-generated method stub
		return adminUserMapper.getAdminUserList((page - 1) * number, number);
	}
	@Override
	public List<Role> getRoleList(int page, int number) {
		// TODO Auto-generated method stub
		return adminUserMapper.getRoleList((page - 1)* number, number);
	}
	@Override
	public boolean dealLogin(BigInteger id, String ip) {
		// TODO Auto-generated method stub
		return adminUserMapper.dealLogin(id, ip,df.format(new Date()).toString());
	}
	@Override
	public AdminUser getAdminUser(String username) {
		// TODO Auto-generated method stub
		return adminUserMapper.getAdminUser(username);
	}
	
	@Override
	public boolean addAdminLog(AdminLog log) {
		// TODO Auto-generated method stub
		log.setCreateTime(df.format(new Date()).toString());
		return adminUserMapper.addAdminLog(log);
	}
	@Override
	public boolean editAdminUserRole(BigInteger id, String roleList) {
		// TODO Auto-generated method stub
		AdminUser adminUser = adminUserMapper.getAdminUserById(id);
		String signature = md5(adminUser.getId() + adminUser.getUsername() + adminUser.getPassword() + adminUser.getStaffId() + roleList + adminUser.getIsBanned());
		
		return adminUserMapper.editAdminUserRole(id, roleList, df.format(new Date()).toString(),signature);
	}
	@Override
	public boolean deleteRole(BigInteger id) {
		// TODO Auto-generated method stub
		return adminUserMapper.deleteRole(df.format(new Date()).toString(), id);
	}
	@Override
	public List<AdminAction> getAdminActionListAll() {
		// TODO Auto-generated method stub
		return adminUserMapper.getAdminActionListAll();
	}
	@Override
	public boolean editRole(BigInteger id, String actionList,String roleName,String roleDescription) {
		// TODO Auto-generated method stub
		return adminUserMapper.editRole(id, roleName, roleDescription, actionList, df.format(new Date()).toString());
	}
	@Override
	public Role getRoleByRoleId(BigInteger id) {
		// TODO Auto-generated method stub
		return adminUserMapper.getRoleByRoleId(id);
	}
	
	/**
	 * signature About
	 * @param str   用户id,用户名,密码,学工号,角色列表,是否禁用
	 * @return
	 */
	private String md5(String str){
		
		str = str + "yigexigua";
		try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytes = md.digest(str.getBytes("utf-8"));
	        return toHex(bytes);
	    }
	    catch (Exception e) {
	        throw new RuntimeException(e);
	    }
    }
	private String toHex(byte[] bytes) {

	    final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
	    StringBuilder ret = new StringBuilder(bytes.length * 2);
	    for (int i=0; i<bytes.length; i++) {
	        ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
	        ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
	    }
	    return ret.toString();
	}
	private String listToString(List<Role> list, char separator) {
		
		if(list == null)
			return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i).getId());
            } else {
                sb.append(list.get(i).getId());
                sb.append(separator);
            }
        }
        return sb.toString();
    }
	@Override
	public boolean addRole(String roleName, String roleDescription, String actionList) {
		// TODO Auto-generated method stub
		BigInteger id = generateID.getID();
		String createTime = df.format(new Date()).toString();
		return adminUserMapper.addRole(id, roleName, roleDescription, actionList, createTime, createTime, 0);
	}
	@Override
	public List<Role> getRoleListAll() {
		// TODO Auto-generated method stub
		return adminUserMapper.getRoleListAll();
	}
	@Override
	public List<AdminUser> getAdminUserListFilter(BigInteger staffId,BigInteger roleId, String username, String start, String end,
			int page, int number) {
		// TODO Auto-generated method stub
		System.out.println("33333333333333333:执行到过滤了！！！");

		return adminUserMapper.getAdminUserListFilter(staffId,roleId, username, start, end, (page - 1)*number, number);
	}
	@Override
	public List<AdminLog> getAdminLogList(String username, String description, String start, String end, int page,
			int number) {
		// TODO Auto-generated method stub
		return adminUserMapper.getAdminLogList(username, description, start, end, (page - 1) * number, number);
	}
	@Override
	public List<Settings> selectSettings() {
		// TODO Auto-generated method stub

		    List<Settings> list = adminUserMapper.selectSettings();
		    StringBuilder sb = new StringBuilder("{");
			for(Settings temp:list)
			{
				sb.append("\""+temp.getSettingCode() +"\""+ ":" + "\""+temp.getValue()+"\"" + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("}");
			
			jedis.del("settings");
			jedis.set("settings", sb.toString());
			
			return list;
		
	}
	@Override
	public boolean updateSettings(BigInteger id, String value) {
		// TODO Auto-generated method stub		
		List<Settings> list = adminUserMapper.selectSettings();
	    StringBuilder sb = new StringBuilder("{");
		for(Settings temp:list)
		{
			if(temp.getId().equals(id))
				sb.append("\""+temp.getSettingCode() +"\""+ ":" + "\""+value+"\"" + ",");
			else
				sb.append("\""+temp.getSettingCode() +"\""+ ":" + "\""+temp.getValue()+"\"" + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		
		jedis.del("settings");
		jedis.set("settings", sb.toString());
		return adminUserMapper.updateSettings(value, df.format(new Date()).toString(), id);
	}
	@Override
	public AdminUser getAdminUserByStaffId(String staffId) {
		// TODO Auto-generated method stub
		return adminUserMapper.getAdminUserByStaffId(staffId);
	}
	@Override
	public boolean checkRoleName(String name) {
		// TODO Auto-generated method stub
		Role role = adminUserMapper.getRoleByRoleName(name);
		
		if(role == null)
			return false;
		else
			return true;
	}

	public int getSumAdminUser(String username,BigInteger staffId,BigInteger roleId,String start,String end)
	{
		System.out.println("222222222222222:执行到计算总数了！！！");
		return adminUserMapper.getSumAdminUser(username,staffId,roleId,start,end);
	}

	public int getSumLogs(String username,String content,String start,String end)
	{
		System.out.println("222222222222222:执行到计算总数了！！！");
		return adminUserMapper.getSumLogs(username,content,start,end);
	}

}
