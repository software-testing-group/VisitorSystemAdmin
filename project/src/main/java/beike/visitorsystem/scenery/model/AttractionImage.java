package beike.visitorsystem.scenery.model;

import java.math.BigInteger;

public class AttractionImage {
    private BigInteger id;
    private BigInteger attractionId;
    private String imageUrl;
    private Integer isDeleted;
    private String createTime;
    private String updateTime;

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

	public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
