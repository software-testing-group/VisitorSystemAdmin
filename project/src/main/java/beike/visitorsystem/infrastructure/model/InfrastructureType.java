package beike.visitorsystem.infrastructure.model;

import java.math.BigInteger;
import java.util.List;

public class InfrastructureType {
    private BigInteger id;
    private String name;
    private String createTime;
    private String updateTime;
    private List<Infrastructure> infrastructureList;

    @Override
    public String toString() {
        return "InfrastructureType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", infrastructureList=" + infrastructureList +
                '}';
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Infrastructure> getInfrastructureList() {
        return infrastructureList;
    }

    public void setInfrastructureList(List<Infrastructure> infrastructureList) {
        this.infrastructureList = infrastructureList;
    }
}
