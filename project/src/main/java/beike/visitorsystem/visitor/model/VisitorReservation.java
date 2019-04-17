package beike.visitorsystem.visitor.model;

import java.math.BigInteger;

public class VisitorReservation {
	BigInteger id;
	BigInteger userId;
	BigInteger reservationTimeId;
	Integer isFinished;
	Integer reservationNumber;
	Integer isDeleted;
	String createTime;
	String updateTime;
	VisitorReservationTime visitorReservationTime;
	
	@Override
	public String toString() {
		return "VisitorReservation [id=" + id + ", userId=" + userId + ", reservationTimeId=" + reservationTimeId
				+ ", isFinished=" + isFinished + ", reservationNumber=" + reservationNumber + ", isDeleted=" + isDeleted
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", visitorReservationTime="
				+ visitorReservationTime + "]";
	}
	public VisitorReservationTime getVisitorReservationTime() {
		return visitorReservationTime;
	}
	public void setVisitorReservationTime(VisitorReservationTime visitorReservationTime) {
		this.visitorReservationTime = visitorReservationTime;
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
	public BigInteger getReservationTimeId() {
		return reservationTimeId;
	}
	public void setReservationTimeId(BigInteger reservationTimeId) {
		this.reservationTimeId = reservationTimeId;
	}
	public Integer getIsFinished() {
		return isFinished;
	}
	public void setIsFinished(Integer isFinished) {
		this.isFinished = isFinished;
	}

	public Integer getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(Integer reservationNumber) {
		this.reservationNumber = reservationNumber;
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
