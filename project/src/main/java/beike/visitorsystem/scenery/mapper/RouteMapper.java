package beike.visitorsystem.scenery.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import beike.visitorsystem.scenery.model.Route;

@Repository
public interface RouteMapper {
	//NO.1
	boolean addRoute(Route route);
	//NO.2
	boolean updateRoute(@Param("route")Route route);
	//NO.3
	boolean deleteRouteById(@Param("id")BigInteger routeId);
    //NO.4  (按页获取路线)
	List<Route> getRoutes(@Param("count")int count,@Param("number")int number);
    //NO.5
	Route getRouteDetailById(@Param("id")BigInteger routeId);

	int getSumOfRoutes();


	public List<Route> getAllRoute();
	public Route getRouteById(BigInteger routeId);
	public boolean updateRouteNameById(@Param("routeId") BigInteger routeId,@Param("routeName") String routeName);
	public boolean updateSpecificRouteById(@Param("routeId")BigInteger routeId,@Param("specificRoute")String specificRoute);
	public boolean updateRouteTextIntroductionById(@Param("routeId")BigInteger routeId,@Param("textIntroduction")String textIntroduction);
	
}
