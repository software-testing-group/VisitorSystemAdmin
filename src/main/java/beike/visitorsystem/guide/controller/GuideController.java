package beike.visitorsystem.guide.controller;


import beike.visitorsystem.guide.model.ResultData;
import beike.visitorsystem.guide.service.GuideService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/guide")
public class GuideController {

    @Autowired
    private GuideService guideService;

    @RequestMapping(value="/addSignIn" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResultData addSignIn(BigInteger user_id, double longitude, double latitude ){
        return guideService.addSignIn(user_id,longitude,latitude);
    }

    @RequestMapping(value="/getSignInStatus" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResultData getSignInStatus(BigInteger user_id){
        return guideService.getSignInStatus(user_id);
    }

    @RequestMapping(value="/getRoutes" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResultData getRoutes(){
        return guideService.getRoutes();
    }

    @RequestMapping(value="/getAttractionsByName" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResultData getAttractionsByName(String name){
        return guideService.getAttractionsByName(name);
    }

    @RequestMapping(value="/getAttractionsByRouteId" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResultData getAttractionsByRouteId(BigInteger route_id){
        return guideService.getAttractionsByRouteId(route_id);
    }

    @RequestMapping(value="/getAttractions" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResultData getAttractions(){
        return guideService.getAttractions();
    }

    @RequestMapping(value="/getAttractionByAttractionId" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResultData getAttractionByAttractionId(BigInteger attraction_id){
        return guideService.getAttractionByAttractionId(attraction_id);
    }

    @RequestMapping(value="/getAttractionImagesByAttractionId" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResultData getAttractionImagesByAttractionId(BigInteger attraction_id){
        return guideService.getAttractionImagesByAttractionId(attraction_id);
    }

    @RequestMapping(value="/getAttractionAudioesByAttractionId" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResultData getAttractionAudioesByAttractionId(BigInteger attraction_id){
        return guideService.getAttractionAudioesByAttractionId(attraction_id);
    }

    @RequestMapping(value="/getAttractionsNearByByAttractionId" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResultData getAttractionsNearByByAttractionId(BigInteger attraction_id){
        return guideService.getAttractionsNearByByAttractionId(attraction_id);
    }

    @RequestMapping(value="/getInfrastructureTypes" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResultData getInfrastructureTypes(){
        return guideService.getInfrastructureTypes();
    }

    @RequestMapping(value="/getInfrastructuresByTypeId" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResultData getInfrastructuresByTypeId(BigInteger type_id){
        return guideService.getInfrastructuresByTypeId(type_id);
    }

    @RequestMapping(value="/getAttractionsBySignIn" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResultData getAttractionsBySignIn(BigInteger user_id){
        return guideService.getAttractionsBySignIn(user_id);
    }




}
