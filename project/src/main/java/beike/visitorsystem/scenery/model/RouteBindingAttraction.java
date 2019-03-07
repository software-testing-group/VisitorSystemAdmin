package beike.visitorsystem.scenery.model;

import java.math.BigInteger;

public class RouteBindingAttraction {

    private BigInteger id;
    private BigInteger attractionId;
    private BigInteger routeId;
    private Integer sequence;
    private Integer isDeleted;
    private String createTime;
    private String updateTime;

    private Attraction attraction;

    @Override
    public String toString() {
        return "RouteBindingAttraction{" +
                "id=" + id +
                ", attractionId=" + attractionId +
                ", routeId=" + routeId +
                ", sequence=" + sequence +
                ", isDeleted=" + isDeleted +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", attraction=" + attraction +
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

    public BigInteger getRouteId() {
        return routeId;
    }

    public void setRouteId(BigInteger routeId) {
        this.routeId = routeId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }
}
