package beike.visitorsystem.scenery.mapper;

import beike.visitorsystem.scenery.model.RouteBindingAttraction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface RouteBindingAttractionMapper {
    //NO.1
    boolean addRouteBindingAttraction(@Param("RBA") RouteBindingAttraction routeBindingAttraction);
    // 应该用不到
    // boolean updateRouteBindingAttraction(RouteBindingAttraction routeBindingAttraction);
    // 分为删除路线中的一个景点和删除路线中的所有景点
    //NO.2
    boolean deleteRouteBindingAttractionByAttractionId(@Param("routeId") BigInteger routeId,@Param("attractionId")BigInteger attractionId);
    //NO.3
    boolean deleteRouteBindingAttractionsByRouteId(@Param("routeId")BigInteger routeId);
    // 返回binding对象数组时需要保证它的顺序，才能让显示的路线中的景点有序
    // 这个任务可以在前端做，即对这个数组排序
    // 是否还需要做一个更新order的接口  Yes!
    //NO.4
    boolean updateOrderOfRouteBindingAttractionById(@Param("id") BigInteger id,@Param("order") int order);

    /*
    ----类比公务访客订单、淘宝订单！
    路线模块不能修改景点的信息，比如属性、添加、删除音频文件和图片文件
    路线模块只能查看景点的信息，即只能读
    路线模块唯一能够修改的就只有binding对象中的信息，即order： 对景点在路线中的顺序进行修改
    路线模块能对binding对象进行增、删、改
    */
}
