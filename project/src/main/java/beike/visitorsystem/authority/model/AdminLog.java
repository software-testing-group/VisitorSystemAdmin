package beike.visitorsystem.authority.model;

import java.math.BigInteger;

public class AdminLog {
	
	private BigInteger id;  //记录id,唯一
	private BigInteger adminUserId;  //记录涉及 的管理员id
	private String adminUsername; //记录涉及的管理员用户名
	private String description;  //文字——操作描述
	private String ip;  //操作发生时管理员的ip
	private String createTime;  //创建时间
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public BigInteger getAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(BigInteger adminUserId) {
		this.adminUserId = adminUserId;
	}
	public String getAdminUsername() {
		return adminUsername;
	}
	public void setAdminUsername(String string) {
		this.adminUsername = string;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "AdminLog{" +
				"id=" + id +
				", adminUserId=" + adminUserId +
				", adminUsername='" + adminUsername + '\'' +
				", description='" + description + '\'' +
				", ip='" + ip + '\'' +
				", createTime='" + createTime + '\'' +
				'}';
	}
}
