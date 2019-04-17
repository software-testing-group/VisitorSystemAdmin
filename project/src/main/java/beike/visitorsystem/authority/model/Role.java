package beike.visitorsystem.authority.model;

import java.math.BigInteger;
import java.util.List;

public class Role {
	
	private BigInteger id;  //角色id，唯一
	private String roleName;  //字母——角色名称
	private String roleDescription;  //角色描述
	private String createTime;  //创建时间
	private String updateTime;  //更新时间
	private Integer isDeleted;  //是否删除
	private List<AdminAction> actionList;  //角色对应的操作列表
	
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
