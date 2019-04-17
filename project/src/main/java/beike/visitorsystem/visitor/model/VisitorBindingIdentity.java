package beike.visitorsystem.visitor.model;

import java.math.BigInteger;

public class VisitorBindingIdentity {
	BigInteger id;
	BigInteger userId;
	BigInteger identityId;
    Integer isMain;
    Integer isDeleted;
    String createTime;
    String updateTime;
    VisitorIdentity visitorIdentity;
	@Override
	public String toString() {
		return "VisitorBindingIdentity [id=" + id + ", userId=" + userId + ", identityId=" + identityId + ", isMain="
				+ isMain + ", isDeleted=" + isDeleted + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", visitorIdentity=" + visitorIdentity + "]";
	}
	public VisitorIdentity getVisitorIdentity() {
		return visitorIdentity;
	}
	public void setVisitorIdentity(VisitorIdentity visitorIdentity) {
		this.visitorIdentity = visitorIdentity;
	}

	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	public BigInteger getIdentityId() {
		return identityId;
	}
	public void setIdentityId(BigInteger identityId) {
		this.identityId = identityId;
	}
	public Integer getIsMain() {
		return isMain;
	}
	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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
    

}
