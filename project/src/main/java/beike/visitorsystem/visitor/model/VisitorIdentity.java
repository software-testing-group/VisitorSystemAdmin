package beike.visitorsystem.visitor.model;

import java.math.BigInteger;

public class VisitorIdentity {
	BigInteger id;
    String fullName;
    String identityCard;
    Integer identityCardType;
    Integer isBanned;
    Integer isDeleted;
    String createTime;
    String updateTime;
    
	@Override
	public String toString() {
		return "VisitorIdentity [id=" + id + ", fullName=" + fullName + ", identityCard=" + identityCard
				+ ", identityCardType=" + identityCardType + ", isBanned=" + isBanned + ", isDeleted=" + isDeleted
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public Integer getIdentityCardType() {
		return identityCardType;
	}
	public void setIdentityCardType(Integer identityCardType) {
		this.identityCardType = identityCardType;
	}
	public Integer getIsBanned() {
		return isBanned;
	}
	public void setIsBanned(Integer isBanned) {
		this.isBanned = isBanned;
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
