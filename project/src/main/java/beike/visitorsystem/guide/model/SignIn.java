package beike.visitorsystem.guide.model;

import java.math.BigInteger;

public class SignIn {

    private BigInteger id;
    private BigInteger userId;
    private BigInteger attractionId;
    private String createTime;
    private String updateTime;

    @Override
    public String toString() {
        return "SignIn{" +
                "id=" + id +
                ", userId=" + userId +
                ", attractionId=" + attractionId +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
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

    public BigInteger getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(BigInteger attractionId) {
        this.attractionId = attractionId;
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
