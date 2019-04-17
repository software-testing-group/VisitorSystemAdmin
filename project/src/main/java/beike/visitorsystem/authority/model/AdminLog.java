package beike.visitorsystem.authority.model;

import java.math.BigInteger;

public class AdminLog {
	
	private BigInteger id;  //��¼id,Ψһ
	private BigInteger adminUserId;  //��¼�漰 �Ĺ���Աid
	private String adminUsername; //��¼�漰�Ĺ���Ա�û���
	private String description;  //���֡�����������
	private String ip;  //��������ʱ����Ա��ip
	private String createTime;  //����ʱ��
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
