package beike.visitorsystem.guide.service;


import beike.visitorsystem.guide.mapper.SignInMapper;
import beike.visitorsystem.guide.model.ResultData;
import beike.visitorsystem.guide.model.SignIn;
import beike.visitorsystem.infrastructure.mapper.InfrastructureMapper;
import beike.visitorsystem.infrastructure.mapper.InfrastructureTypeMapper;
import beike.visitorsystem.infrastructure.model.Infrastructure;
import beike.visitorsystem.infrastructure.model.InfrastructureType;
import beike.visitorsystem.scenery.mapper.AttractionAudioMapper;
import beike.visitorsystem.scenery.mapper.AttractionImageMapper;
import beike.visitorsystem.scenery.mapper.AttractionMapper;
import beike.visitorsystem.scenery.mapper.RouteMapper;
import beike.visitorsystem.scenery.model.Attraction;
import beike.visitorsystem.scenery.model.AttractionAudio;
import beike.visitorsystem.scenery.model.AttractionImage;
import beike.visitorsystem.scenery.model.Route;
import beike.visitorsystem.utils.GenerateID;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GuideServiceImpl implements GuideService{

    @Autowired
    private SignInMapper signInMapper;

    @Autowired
    private AttractionMapper attractionMapper;

    @Autowired
    private AttractionImageMapper attractionImageMapper;

    @Autowired
    private AttractionAudioMapper attractionAudioMapper;

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private InfrastructureTypeMapper infrastructureTypeMapper;

    @Autowired
    private InfrastructureMapper infrastructureMapper;

    @Autowired
    GenerateID generateID;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public ResultData addSignIn(BigInteger user_id, double longitude, double latitude) {
        ResultData rd = new ResultData();
        JSONObject jo2 = new JSONObject();
        List<Attraction> attractionList = attractionMapper.getAttractions();
        if(attractionList.size()==0){
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        JSONArray ja = new JSONArray();

        for(int i=0;i<attractionList.size();i++){
            JSONObject jo = new JSONObject();
            jo.put("attraction_id",attractionList.get(i).getId());
            jo.put("distance",(int)getDistance(longitude,latitude,attractionList.get(i).getLongitude().doubleValue(),attractionList.get(i).getLatitude().doubleValue()));
            ja.add(jo);
        }
        ja.sort(Comparator.comparing(obj -> ((JSONObject) obj).getBigDecimal("distance")));//升序排序

        int range = 20;
        int minDistance = (int)((JSONObject)ja.get(0)).get("distance");
        if(minDistance>range){
            rd.setCode(500);
            rd.setMessage("out of range");
            return rd;
        }
        BigInteger attraction_id = (BigInteger)((JSONObject)ja.get(0)).get("attraction_id");
        SignIn si = new SignIn();
        BigInteger id = generateID.getID();
        si.setId(id);
        si.setUserId(user_id);
        si.setAttractionId(attraction_id);
        si.setCreateTime(df.format(new Date()).toString());
        si.setUpdateTime(df.format(new Date()).toString());
        try{
            signInMapper.addSignIn(si);
        }catch (Exception e){
            rd.setCode(500);
            rd.setMessage("false");
            jo2.put("res",false);
            rd.setData(jo2);
            return rd;
        }
        rd.setCode(200);
        rd.setMessage("success");
        jo2.put("res",true);
        rd.setData(jo2);
        return rd;
    }

    @Override
    public ResultData getSignInStatus(BigInteger user_id) {
        ResultData rd = new ResultData();
        List<BigInteger> signInList = signInMapper.getSignInByUserId(user_id);
        if(signInList.size()==0){
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        JSONArray ja = new JSONArray();
        for(int i=0;i<signInList.size();i++){
            JSONObject jo = new JSONObject();
            jo.put("attraction_id",signInList.get(i));
            ja.add(jo);
        }
        rd.setCode(200);
        rd.setMessage("success");
        rd.setData(ja);
        return rd;
    }

    @Override
    public ResultData getRoutes() {
        List<Route> routeList = routeMapper.getAllRoute();
        ResultData rd = new ResultData();
        if(routeList.size()==0){
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        JSONArray ja = new JSONArray();
        for(int i=0;i<routeList.size();i++){
            JSONObject jo = new JSONObject();
            jo.put("id",routeList.get(i).getId());
            jo.put("route_name",routeList.get(i).getRouteName());
            jo.put("content",routeList.get(i).getContent());
            jo.put("time",routeList.get(i).getTime());
            jo.put("use_count",routeList.get(i).getUseCount());
            ja.add(jo);
        }
        rd.setCode(200);
        rd.setMessage("success");
        rd.setData(ja);
        return rd;
    }

    @Override
    public ResultData getAttractionsByName(String name) {
        ResultData rd = new ResultData();
        String[] str = name.split("");
        StringBuffer sb = new StringBuffer();
        sb.append("%");
        for(int i=0;i<str.length;i++){
            sb.append(str[i]);
            sb.append("%");
        }
        List<Attraction> attractionList = attractionMapper.getAttractionByName(sb.toString());
        if(attractionList.size()==0){
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        JSONArray ja = new JSONArray();
        for(int i = 0;i<attractionList.size();i++){
            JSONObject jo = new JSONObject();
            jo.put("id",attractionList.get(i).getId());
            jo.put("name",attractionList.get(i).getName());
            ja.add(jo);
        }
        rd.setCode(200);
        rd.setMessage("success");
        rd.setData(ja);
        return rd;
    }

    @Override
    public ResultData getAttractionsByRouteId(BigInteger route_id) {
        ResultData rd = new ResultData();
        List<Attraction> attractionList = attractionMapper.getAttractionsByRouteId(route_id);
        if(attractionList.size()==0){
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        Route route=routeMapper.getRouteById(route_id);
        JSONArray ja = new JSONArray();
        for(int i = 0;i<attractionList.size();i++){
            JSONObject jo = new JSONObject();
            jo.put("id",attractionList.get(i).getId());
            jo.put("order_number",attractionList.get(i).getOrderNumber());
            jo.put("name",attractionList.get(i).getName());
            jo.put("longitude",attractionList.get(i).getLongitude());
            jo.put("latitude",attractionList.get(i).getLatitude());
            jo.put("read_count",attractionList.get(i).getReadCount());
            ja.add(jo);
        }
        JSONObject res= new JSONObject();
        res.put("attractions",ja);
        res.put("polyline",route.getPolyline());
        rd.setCode(200);
        rd.setMessage("success");
        rd.setData(res);
        return rd;
    }

    @Override
    public ResultData getAttractions() {
        ResultData rd = new ResultData();
        List<Attraction> attractionList = attractionMapper.getAttractions();
        if(attractionList.size()==0){
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        JSONArray ja = new JSONArray();
        for(int i = 0;i<attractionList.size();i++){
            JSONObject jo = new JSONObject();
            jo.put("id",attractionList.get(i).getId());
            jo.put("order_number",attractionList.get(i).getOrderNumber());
            jo.put("name",attractionList.get(i).getName());
            jo.put("longitude",attractionList.get(i).getLongitude());
            jo.put("latitude",attractionList.get(i).getLatitude());
            jo.put("read_count",attractionList.get(i).getReadCount());
            ja.add(jo);
        }
        List<JSONObject> list = JSONArray.parseArray(ja.toJSONString(), JSONObject.class);
        Collections.sort(list, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                int a = o1.getInteger("order_number");
                int b = o2.getInteger("order_number");
                int c = o1.getInteger("read_count");
                int d = o2.getInteger("read_count");
                if (a > b) {
                    return 1;
                } else if(a == b) {
                    if(c>d)return 1;
                    else if(c<d)return -1;
                    else return 0;
                } else
                    return -1;
            }
        });
         ja = JSONArray.parseArray(list.toString());
        rd.setCode(200);
        rd.setMessage("success");
        rd.setData(ja);
        return rd;
    }

    @Override
    public ResultData getAttractionByAttractionId(BigInteger attraction_id) {
        ResultData rd = new ResultData();
        Attraction attraction = attractionMapper.getAttractionById(attraction_id);
        if(attraction==null){
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        int read_count = attraction.getReadCount();
        attractionMapper.updateAttractionReadCountById(attraction_id,read_count+1);
        JSONArray ja = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("id",attraction.getId());
        jo.put("order_number",attraction.getOrderNumber());
        jo.put("name",attraction.getName());
        jo.put("text_introduction",attraction.getTextIntroduction());
        jo.put("open_time",attraction.getOpenTime());
        jo.put("longitude",attraction.getLongitude());
        jo.put("latitude",attraction.getLatitude());
        jo.put("read_count",attraction.getReadCount());
        ja.add(jo);
        rd.setCode(200);
        rd.setMessage("success");
        rd.setData(ja);
        return rd;
    }

    @Override
    public ResultData getAttractionImagesByAttractionId(BigInteger attraction_id) {
        ResultData rd = new ResultData();
        List<AttractionImage> attractionImageList = attractionImageMapper.getImageByAttractionId(attraction_id);
        if(attractionImageList.size()==0){
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        JSONArray ja = new JSONArray();
        for(int i = 0;i<attractionImageList.size();i++){
            JSONObject jo = new JSONObject();
            jo.put("id",attractionImageList.get(i).getId());
            jo.put("attraction_id",attractionImageList.get(i).getAttractionId());
            jo.put("image_url",attractionImageList.get(i).getImageUrl());
            ja.add(jo);
        }
        rd.setCode(200);
        rd.setMessage("success");
        rd.setData(ja);
        return rd;
    }

    @Override
    public ResultData getAttractionAudioesByAttractionId(BigInteger attraction_id) {
        ResultData rd = new ResultData();
        List<AttractionAudio> attractionAudioList = attractionAudioMapper.getAttractionAudioesByAttractionId(attraction_id);
        if(attractionAudioList.size()==0){
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        JSONArray ja = new JSONArray();
        for(int i = 0;i<attractionAudioList.size();i++){
            JSONObject jo = new JSONObject();
            jo.put("id",attractionAudioList.get(i).getId());
            jo.put("attraction_id",attractionAudioList.get(i).getAttractionId());
            jo.put("type",attractionAudioList.get(i).getType());
            jo.put("audio_url",attractionAudioList.get(i).getAudioUrl());
            ja.add(jo);
        }
        rd.setCode(200);
        rd.setMessage("success");
        rd.setData(ja);
        return rd;
    }

    @Override
    public ResultData getAttractionsNearByByAttractionId(BigInteger attraction_id) {
        ResultData rd = new ResultData();
        Attraction aim = attractionMapper.getAimAttractionById(attraction_id);
        if(aim == null){//判断attraction_id是否存在
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        double aimLatitude = aim.getLatitude().doubleValue();
        double aimLongitude = aim.getLongitude().doubleValue();
        List<Attraction> nearBy = attractionMapper.getOtherAttractions(attraction_id);
        if(nearBy.size()==0){//判断是否存在其他景点
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        JSONArray ja = new JSONArray();
        for(int j=0;j<nearBy.size();j++){
            JSONObject jo = new JSONObject();
            jo.put("id",nearBy.get(j).getId());
            jo.put("name",nearBy.get(j).getName());
            jo.put("distance",(int)getDistance(aimLongitude,aimLatitude,nearBy.get(j).getLongitude().doubleValue(),nearBy.get(j).getLatitude().doubleValue()));
            ja.add(jo);
        }

        ja.sort(Comparator.comparing(obj -> ((JSONObject) obj).getBigDecimal("distance")));//升序排序
        JSONArray jA = new JSONArray();
        int n=3;
        for(int k=0;k<ja.size();k++){
            if(k>=n){
                rd.setCode(200);
                rd.setMessage("success");
                rd.setData(jA);
                return rd;
            }
            JSONObject jO = ja.getJSONObject(k);
            String image = "";
            List<AttractionImage> attractionImageList = attractionImageMapper.getImageByAttractionId((BigInteger)jO.get("id"));
            if(attractionImageList.size()==0){//判断景点是否有图片数据
                System.out.println("Image Error");
            }else{
                image = attractionImageList.get(0).getImageUrl();
            }
            jO.put("image_url",image);
            jA.add(jO);
        }
        rd.setCode(200);
        rd.setMessage("success");
        rd.setData(jA);
        return rd;
    }

    private double OD(double a,double b,double c){
        while (a>c) a -= c - b;
        while (a<b) a += c - b;
        return a;
    }
    private double SD(double a,double b,double c){
        a = Math.max(a, b);
        a = Math.min(a, c);
        return a;
    }
    private double getDistance(double a_lng,double a_lat,double b_lng,double b_lat){
        double a = Math.PI * OD(a_lng,-180,180) / 180.0;
        double b = Math.PI * OD(b_lng,-180,180) / 180.0;
        double c = Math.PI * SD(a_lat,-74,74) / 180.0;
        double d = Math.PI * SD(b_lat,-74,74) / 180.0;
        double s = 6370996.81 * Math.acos(Math.sin(c) * Math.sin(d) + Math.cos(c) * Math.cos(d) * Math.cos(b-a));
        return s;
    }

    @Override
    public ResultData getInfrastructureTypes() {
        ResultData rd = new ResultData();
        List<InfrastructureType> infrastructureTypeList = infrastructureTypeMapper.getInfrastructureTypeList();
        if(infrastructureTypeList.size()==0){
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        JSONArray ja = new JSONArray();
        for(int i=0;i<infrastructureTypeList.size();i++){
            JSONObject jo = new JSONObject();
            jo.put("id",infrastructureTypeList.get(i).getId());
            jo.put("name",infrastructureTypeList.get(i).getName());
            ja.add(jo);
        }
        rd.setCode(200);
        rd.setMessage("success");
        rd.setData(ja);
        return rd;
    }

    @Override
    public ResultData getInfrastructuresByTypeId(BigInteger type_id) {
        ResultData rd = new ResultData();
        List<Infrastructure> infrastructureList = infrastructureMapper.getInfrastructuresByTypeId(type_id);
        if(infrastructureList.size()==0){
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        JSONArray ja = new JSONArray();
        for(int i = 0;i<infrastructureList.size();i++){
            JSONObject jo = new JSONObject();
            jo.put("id",infrastructureList.get(i).getId());
            jo.put("name",infrastructureList.get(i).getName());
            jo.put("type_id",infrastructureList.get(i).getTypeId());
            jo.put("longitude",infrastructureList.get(i).getLongitude());
            jo.put("latitude",infrastructureList.get(i).getLatitude());
            ja.add(jo);
        }
        rd.setCode(200);
        rd.setMessage("success");
        rd.setData(ja);
        return rd;
    }

    @Override
    public ResultData getAttractionsBySignIn(BigInteger user_id) {
        //用户可能重复签到：可能在在某个景点连续签到多次，也可能在一个景点签到后跑到另一个景点签到又跑回原来的景点签到，也就是不连续的重复签到
        //连续重复签到的情况则只给前端返回重复签到中最早签到的记录，其余多余重复记录不返回
        //不连续的重复签到的情况则是都向前端返回签到记录
        //例子如下（字母表示景点名，数字表示在该景点的不同签到记录）：A1 A2 A3 B1 A4
        //返回的结果是：A1 B1 A4
        ResultData rd = new ResultData();
        List<BigInteger> attractionIdList = signInMapper.getSignInStatusByUserId(user_id);
        if(attractionIdList.size()==0){
            rd.setCode(500);
            rd.setMessage("error");
            return rd;
        }
        JSONArray ja = new JSONArray();
        BigInteger previousID = BigInteger.valueOf(0);
        for(int i = 0;i<attractionIdList.size();i++){
            JSONObject jo = new JSONObject();
            BigInteger attraction_id = attractionIdList.get(i);
            if(previousID.compareTo(attraction_id)==0){
                continue;
            }
            Attraction attraction = attractionMapper.getAimAttractionById(attraction_id);
            if(attraction==null){
                rd.setCode(500);
                rd.setMessage("error");
                return rd;
            }
            jo.put("id",attraction.getId());
            jo.put("order_number",attraction.getOrderNumber());
            jo.put("name",attraction.getName());
            jo.put("text_introduction",attraction.getTextIntroduction());
            jo.put("open_time",attraction.getOpenTime());
            jo.put("longitude",attraction.getLongitude());
            jo.put("latitude",attraction.getLatitude());
            jo.put("read_count",attraction.getReadCount());
            JSONArray ja2 = new JSONArray();
            List<AttractionImage> attractionImageList = attractionImageMapper.getImageByAttractionId(attraction_id);
            for(int j=0;j<attractionImageList.size();j++){
                JSONObject jo2 = new JSONObject();
                jo2.put("id",attractionImageList.get(j).getId());
                jo2.put("image_url",attractionImageList.get(j).getImageUrl());
                ja2.add(jo2);
            }
            jo.put("image_url",ja2);
            ja.add(jo);
            previousID = attraction_id;
        }
        rd.setCode(200);
        rd.setMessage("success");
        rd.setData(ja);
        return rd;
    }
}
