package beike.visitorsystem.scenery.model;

import java.math.BigInteger;
import java.util.List;

public class Route {
	private BigInteger id;
	private String routeName;
	private String content;
	private int time;
	private Integer useCount;
	private String  polyline;
	private Integer isDeleted;
	private String createTime;
	private String updateTime;

	private List<RouteBindingAttraction> routeBindingAttractionList;

	@Override
	public String toString() {
		return "Route{" +
				"id=" + id +
				", routeName='" + routeName + '\'' +
				", content='" + content + '\'' +
				", time='" + time + '\'' +
				", useCount=" + useCount +
				", polyline='" + polyline + '\'' +
				", isDeleted=" + isDeleted +
				", createTime='" + createTime + '\'' +
				", updateTime='" + updateTime + '\'' +
				", routeBindingAttractionList=" + routeBindingAttractionList +
				'}';
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}

	public String getPolyline() {
		return polyline;
	}

	public void setPolyline(String polyline) {
		this.polyline = polyline;
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

	public List<RouteBindingAttraction> getRouteBindingAttractionList() {
		return routeBindingAttractionList;
	}

	public void setRouteBindingAttractionList(List<RouteBindingAttraction> routeBindingAttractionList) {
		this.routeBindingAttractionList = routeBindingAttractionList;
	}
}
