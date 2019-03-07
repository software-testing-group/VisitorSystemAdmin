package beike.visitorsystem.scenery.model;

import java.math.BigInteger;

public class AttractionAudio {

    private BigInteger id;
    private BigInteger attractionId;
    private String type;
    private String audioUrl;
    private Integer isDeleted;
    private String createTime;
    private String updateTime;

    @Override
    public String toString() {
        return "AttractionAudio{" +
                "id=" + id +
                ", attractionId=" + attractionId +
                ", type='" + type + '\'' +
                ", audioUrl='" + audioUrl + '\'' +
                ", isDeleted=" + isDeleted +
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

    public BigInteger getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(BigInteger attractionId) {
        this.attractionId = attractionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
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
