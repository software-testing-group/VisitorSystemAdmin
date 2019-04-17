package beike.visitorsystem.authority.model;

import java.math.BigInteger;

public class AdminAction {
	
	private BigInteger id; //����id��Ψһ
	private String actionName;  //��������
	private BigInteger parentId;  //�ϼ�����id
	private String actionCode;  //��ĸ�Ĳ�������
	private String createTime;  //����ʱ��
	private String updateTime;  //����ʱ��
	private Integer isDeleted;  //�Ƿ�ɾ��/
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public BigInteger getParentId() {
		return parentId;
	}
	public void setParentId(BigInteger parentId) {
		this.parentId = parentId;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
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
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
}
