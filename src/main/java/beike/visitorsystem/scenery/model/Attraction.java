package beike.visitorsystem.scenery.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class Attraction {
	private BigInteger id;
	private int orderNumber;
	private String name;
    private String textIntroduction;
    private String openTime;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private int readCount;
    private Integer isDeleted;
    private String createTime;
    private String updateTime;

    private List<AttractionImage> images;
    private List<AttractionAudio> audios;

    @Override
    public String toString() {
        return "Attraction{" +
                "id=" + id +
                ", orderNumber=" + orderNumber +
                ", name='" + name + '\'' +
                ", textIntroduction='" + textIntroduction + '\'' +
                ", openTime='" + openTime + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", readCount=" + readCount +
                ", isDeleted=" + isDeleted +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", images=" + images +
                ", audios=" + audios +
                '}';
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextIntroduction() {
        return textIntroduction;
    }

    public void setTextIntroduction(String textIntroduction) {
        this.textIntroduction = textIntroduction;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
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

    public List<AttractionImage> getImages() {
        return images;
    }

    public void setImages(List<AttractionImage> images) {
        this.images = images;
    }

    public List<AttractionAudio> getAudios() {
        return audios;
    }

    public void setAudios(List<AttractionAudio> audios) {
        this.audios = audios;
    }
}
