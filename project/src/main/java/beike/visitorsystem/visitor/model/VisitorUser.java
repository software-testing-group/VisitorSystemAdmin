package beike.visitorsystem.visitor.model;

import java.math.BigInteger;
import java.util.List;

public class VisitorUser {
	BigInteger id;
    String cellphone;
    String openId;
    //String password;
    Integer credit;
    Integer isBanned;
    Integer isDeleted;
    String createTime;
    String updateTime;
    List<VisitorBindingIdentity> visitorBindingIdentityList;
    List<VisitorReservation> visitorReservationList;
    
    
	@Override
	public String toString() {
		return "VisitorUser [id=" + id + ", cellphone=" + cellphone + ", openId=" + openId + ", credit=" + credit
				+ ", isBanned=" + isBanned + ", isDeleted=" + isDeleted + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", visitorBindingIdentityList=" + visitorBindingIdentityList
				+ ", visitorReservationList=" + visitorReservationList + "]";
	}
	public List<VisitorReservation> getVisitorReservationList() {
		return visitorReservationList;
	}
	public void setVisitorReservationList(List<VisitorReservation> visitorReservationList) {
		this.visitorReservationList = visitorReservationList;
	}
	public List<VisitorBindingIdentity> getVisitorBindingIdentityList() {
		return visitorBindingIdentityList;
	}
	public void setVisitorBindingIdentityList(List<VisitorBindingIdentity> visitorBindingIdentityList) {
		this.visitorBindingIdentityList = visitorBindingIdentityList;
	}

	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
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
