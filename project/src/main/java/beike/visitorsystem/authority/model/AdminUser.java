package beike.visitorsystem.authority.model;

import java.math.BigInteger;
import java.util.List;

public class AdminUser {

	private BigInteger id;  //管理员id，唯一
	private String username;  //用户名，用于登陆
	private String password;  //密码
	private String staffId;  //学工号
	private String lastLogin;  //上次登陆时间
	private String lastIp;  //上次登陆ip
	private Integer isBanned; //是否禁用
	private String createTime;  //创建时间
	private String updateTime;  //更新时间
	private List<Role> roleList;  //管理员的角色列表
	private String signature; //签名
	
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
