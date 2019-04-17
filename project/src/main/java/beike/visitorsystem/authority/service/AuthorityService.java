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
	 * ��������Ա
	 * @param adminUser
	 * @return AdminUser
	 */
	public boolean addAdminUser(String username,String password,String staffId);
	/**
	 * �޸Ĺ���Ա����
	 * @param id
	 * @param password
	 * @return
	 */
	public boolean editPassword(BigInteger id,String password);
	/**
	 * �����˻�
	 * @param id  ����Աid
	 * @parem isBanned �Ƿ����
	 * @return true or false
	 */
	public boolean banAdminUser(BigInteger id,int isBanned);
	/**
	 * ��ȡ����Ա�б�(no Filter)
	 * @param page  ҳ��
	 * @param number  ��ʾ����
	 * @return List<AdminUser>
	 */
	public List<AdminUser> getAdminUserList(int page,int number);
	/**
	 * ��ȡ����Ա�б�(filter)
	 * @param roleId  ��ɫid
	 * @param username  �û���
	 * @param start  ��¼ʱ��С��
	 * @param end  ��¼ʱ�����
	 * @param page  ҳ��
	 * @param number  ��Ŀ��
	 * @return List<AdminUser>
	 */
	public List<AdminUser> getAdminUserListFilter(BigInteger staffId,BigInteger roleId,String username,String start,String end,int page,int number);
	/**
	 * ��ȡ��ɫ�б�
	 * @param page ҳ��
	 * @param number  ����
	 * @return List<Role>
	 */
	public List<Role> getRoleList(int page,int number);
	/**
	 * ��ȡ���н�ɫ
	 * @return  List<Role>
	 */
	public List<Role> getRoleListAll();
	/**
	 * ��¼���޸��û���¼
	 * @param id  �û�id
	 * @param ip  ��½ʱip
	 * @return  true or false
	 */
	public boolean dealLogin(BigInteger id,String ip);
	/**
	 * ���һ������Աʵ��
	 * @param username  �û���
	 * @return ����Աʵ��
	 */
	public AdminUser getAdminUser(String username);
	/**
	 * ͨ��STAFFID���һ������Աʵ��
	 * @param staffId  
	 * @return
	 */
	public AdminUser getAdminUserByStaffId(String staffId);
	/**
	 * ��ò�����¼
	 * @param page  ҳ��
	 * @param number ����
	 * @return ��¼�б�
	 */
	public List<AdminLog> getAdminLogList(String username,String description,String start,String end,int page,int number);
	/**
	 * ����һ��������¼
	 * @param log ʵ��
	 * @return true or false
	 */
	public boolean addAdminLog(AdminLog log);
	/**
	 * �޸Ĺ���Ա��ɫ
	 * @param adminUserId  ����Աid
	 * @param roleList   ��ɫid��String   ->   1,2,3,4   ��ʽ
	 * @return  true or false
	 */
	public boolean editAdminUserRole(BigInteger id,String roleList);
	/**
	 * ɾ��һ����ɫ
	 * @param id ��ɫid
	 * @return true or false
	 */
	public boolean deleteRole(BigInteger id);
	/**
	 * ��ȡ����Ȩ��
	 * @return  Ȩ��List
	 */
	public List<AdminAction> getAdminActionListAll();
	/**
	 * �޸Ľ�ɫȨ��
	 * @param roleId  ��ɫid
	 * @param actionList  Ȩ��id���ַ���
	 * @return  ��/��
	 */
	public boolean editRole(BigInteger id,String actionList,String roleName,String roleDescription);
	/**
	 * ͨ����ɫid��ȡ�ý�ɫ
	 * @param id  ��ɫid
	 * @return ��ɫ����
	 */
	public Role getRoleByRoleId(BigInteger id);
	/**
	 * ����Ƿ��Ѿ����ڸý�ɫ����
	 * @param name
	 * @return
	 */
	public boolean checkRoleName(String name);
	/**
	 * ������ɫ
	 * @param roleName  ��ɫ����
	 * @param roleDescription  ��ɫ����
	 * @param actionList Ȩ���б�
	 * @return
	 */
	public boolean addRole(String roleName,String roleDescription,String actionList);
	/**
	 * �õ����е�����
	 * @return
	 */
	public List<Settings> selectSettings();
	/**
	 * �޸�һ������
	 * @param id   ���õ�id
	 * @param value   ���õ�ֵ
	 * @return  
	 */
	public boolean updateSettings(BigInteger id,String value);

	public int getSumAdminUser(String username,BigInteger staffId,BigInteger roleId,String start,String end);

	public int getSumLogs(String username,String content,String start,String end);
}
