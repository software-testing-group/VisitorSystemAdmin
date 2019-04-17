package beike.visitorsystem.authority.model;

import java.math.BigInteger;
import java.util.List;

public class Role {
	
	private BigInteger id;  //��ɫid��Ψһ
	private String roleName;  //��ĸ������ɫ����
	private String roleDescription;  //��ɫ����
	private String createTime;  //����ʱ��
	private String updateTime;  //����ʱ��
	private Integer isDeleted;  //�Ƿ�ɾ��
	private List<AdminAction> actionList;  //��ɫ��Ӧ�Ĳ����б�
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
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
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDelete(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public List<AdminAction> getActionList() {
		return actionList;
	}
	public void setActionList(List<AdminAction> actionList) {
		this.actionList = actionList;
	}
	
	
	
	
}
