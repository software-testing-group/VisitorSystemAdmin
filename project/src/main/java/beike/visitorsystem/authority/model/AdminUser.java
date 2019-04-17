package beike.visitorsystem.authority.model;

import java.math.BigInteger;
import java.util.List;

public class AdminUser {

	private BigInteger id;  //����Աid��Ψһ
	private String username;  //�û��������ڵ�½
	private String password;  //����
	private String staffId;  //ѧ����
	private String lastLogin;  //�ϴε�½ʱ��
	private String lastIp;  //�ϴε�½ip
	private Integer isBanned; //�Ƿ����
	private String createTime;  //����ʱ��
	private String updateTime;  //����ʱ��
	private List<Role> roleList;  //����Ա�Ľ�ɫ�б�
	private String signature; //ǩ��
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getLastIp() {
		return lastIp;
	}
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
	public Integer getIsBanned() {
		return isBanned;
	}
	public void setIsBanned(Integer isBanned) {
		this.isBanned = isBanned;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "AdminUser{" +
				"username='" + username + '\'' +
				'}';
	}
}
