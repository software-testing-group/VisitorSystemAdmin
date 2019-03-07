package beike.visitorsystem.guide.service;

import beike.visitorsystem.guide.model.ResultData;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigInteger;
import java.util.List;

public interface GuideService {

    public ResultData addSignIn(BigInteger user_id,double longitude, double latitude);
    public ResultData getSignInStatus(BigInteger user_id);
    public ResultData getRoutes();
    public ResultData getAttractionsByName(String name);
    public ResultData getAttractionsByRouteId(BigInteger route_id);
    public ResultData getAttractions();
    public ResultData getAttractionByAttractionId(BigInteger attraction_id);
    public ResultData getAttractionImagesByAttractionId(BigInteger attraction_id);
    public ResultData getAttractionAudioesByAttractionId(BigInteger attraction_id);
    public ResultData getAttractionsNearByByAttractionId(BigInteger attraction_id);
    public ResultData getInfrastructureTypes();
    public ResultData getInfrastructuresByTypeId(BigInteger type_id);
    public ResultData getAttractionsBySignIn(BigInteger user_id);
}
