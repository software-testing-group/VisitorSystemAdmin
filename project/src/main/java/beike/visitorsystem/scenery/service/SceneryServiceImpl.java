package beike.visitorsystem.scenery.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beike.visitorsystem.scenery.mapper.*;
import beike.visitorsystem.scenery.model.*;
import beike.visitorsystem.utils.RoutePlanning;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beike.visitorsystem.utils.GenerateID;
import org.w3c.dom.Attr;


@Service
public class SceneryServiceImpl implements SceneryService{
	@Autowired
	private AttractionMapper attractionMapper;
	@Autowired
	private AttractionImageMapper attractionImageMapper;
	@Autowired
	private AttractionAudioMapper attractionAudioMapper;
	@Autowired
	private RouteMapper routeMapper;
	@Autowired
	private RouteBindingAttractionMapper routeBindingAttractionMapper;
	@Autowired
	GenerateID generateID;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean updateVoiceIntroductionById(BigInteger attractionId,String voiceIntroduction) {
		return attractionMapper.updateVoiceIntroductionById(attractionId, voiceIntroduction);
	}
	@Override
	public boolean updateTextIntroductionById(BigInteger attractionId,String textIntroduction) {
		return attractionMapper.updateTextIntroductionById(attractionId, textIntroduction);
	}
	@Override
	public Route getRouteById(BigInteger routeId) {
		return routeMapper.getRouteById(routeId);
	}

	@Override
	public boolean updateTitleById(BigInteger attractionId,String title) {
		return attractionMapper.updateTitleById(attractionId, title);
	}
	@Override
	public boolean updateAttractionNumberById(BigInteger attractionId,String attractionNumber) {
		return attractionMapper.updateAttractionNumberById(attractionId, attractionNumber);
	}


    //Route
      //finished
    public boolean addRoute(Route route,BigInteger[] attractionIds) {
	    BigInteger routeId = generateID.getID();
		route.setId(routeId);

		System.out.println("------------: "+attractionIds);
		System.out.println("------------: "+attractionIds.length);

		//计算polyline
		String polyline = getThePolyline(attractionIds);
		route.setPolyline(polyline);
        if(routeMapper.addRoute(route))
		{
			for(int i=0;i<attractionIds.length;i++)
			{
				BigInteger bindingId = generateID.getID();
				RouteBindingAttraction routeBindingAttraction = new RouteBindingAttraction();
				routeBindingAttraction.setId(bindingId);
				routeBindingAttraction.setRouteId(routeId);
				routeBindingAttraction.setAttractionId(attractionIds[i]);
				routeBindingAttraction.setSequence(i);
				routeBindingAttraction.setIsDeleted(0);
				routeBindingAttraction.setCreateTime(df.format(new Date()).toString());
				routeBindingAttraction.setUpdateTime(df.format(new Date()).toString());
				routeBindingAttractionMapper.addRouteBindingAttraction(routeBindingAttraction);
			}
		}
		else
		{
			return false;
		}
	   return true;
    }
    public String getThePolyline(BigInteger[] attractionIds){
		ArrayList<Attraction> aList = new ArrayList<Attraction>();
		for(int i=0;i<attractionIds.length;i++){
			Attraction attraction = attractionMapper.getAimAttractionById(attractionIds[i]);
			aList.add(attraction);
		}
		String polyline = "";
		RoutePlanning rp = new RoutePlanning();
		try{
			for(int j=0;j<aList.size()-1;j++) {
				String message = rp.getPolyline(aList.get(j).getLatitude().doubleValue(), aList.get(j).getLongitude().doubleValue(), aList.get(j + 1).getLatitude().doubleValue(), aList.get(j + 1).getLongitude().doubleValue());
				JSONObject joo = JSONObject.parseObject(message);
				JSONArray result = ((JSONArray) ((JSONObject) joo.get("result")).get("routes"));
				JSONObject r = (JSONObject) result.get(0);
				String s = r.get("polyline").toString();
				int len = s.length();
				s = s.substring(1,len-1);
				String[] p=s.split(",");
				for(int k=2;k<p.length;k++){
					p[k]=String.valueOf(Double.parseDouble(p[k-2])+Double.parseDouble(p[k])/1000000);
				}
				polyline = polyline + p[0];
				for(int q=1;q<p.length;q++){
					polyline = polyline +","+ p[q];
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return polyline;
	}
	//finished
	public boolean updateRoute(Route route)
	{
		return routeMapper.updateRoute(route);
	}
	//finished
	public boolean deleteRouteById(BigInteger routeId)
	{
		if(routeMapper.deleteRouteById(routeId))
		{
			routeBindingAttractionMapper.deleteRouteBindingAttractionsByRouteId(routeId);
		}
		else
		{
			return false;
		}
		return true;
	}
	//finished
	public List<Route> getRoutes(int page,int number)
	{
		List<Route> routeList = routeMapper.getRoutes((page - 1)*number, number);
		List<Route> routeDetailList = new ArrayList<>();
		for(Route route:routeList)
		{
          Route routeDetail = routeMapper.getRouteDetailById(route.getId());
          routeDetailList.add(routeDetail);
		}
		return routeDetailList;
	}
	//finished
	public Route getRouteDetailById(BigInteger routeId)
	{
		Route route = routeMapper.getRouteDetailById(routeId);
		return route;
	}

	public List<Route> getAllRoute()
	{
		return routeMapper.getAllRoute();
	}
	//---
	public boolean updateRouteNameById(BigInteger routeId,String routeName) {
		return routeMapper.updateRouteNameById(routeId, routeName);
	}
	public boolean updateSpecificRouteById(BigInteger routeId,String specificRoute) {
		return routeMapper.updateSpecificRouteById(routeId, specificRoute);
	}
	public boolean updateRouteTextIntroductionById(BigInteger routeId,String textIntroduction) {
		return routeMapper.updateRouteTextIntroductionById(routeId, textIntroduction);
	}

	public int getSumOfRoutes()
	{
		return routeMapper.getSumOfRoutes();
	}

	//Attraction
	//finished
	public boolean addAttraction(Attraction attraction) {
		return attractionMapper.addAttraction(attraction);
	}
	//!!!finished (需要关联图片、音频吗？NO!参考淘宝取消订单)
	public boolean deleteAttractionById(BigInteger attractionId) {
		return  attractionMapper.deleteAttractionById(attractionId);
	}
	//finished
	public boolean updateAttraction(Attraction attraction)
	{
		return attractionMapper.updateAttraction(attraction);
	}
	//finished
	public List<Attraction> getAllAttraction(int page,int number){
		return attractionMapper.getAllAttraction((page - 1)*number, number);
	}
	//finished
	public Attraction getAttractionById(BigInteger attractionId)
	{
		Attraction attraction = attractionMapper.getAttractionAndImagesById(attractionId);
		Attraction attractionAudios = attractionMapper.getAttractionAndAudiosById(attractionId);
		attraction.setAudios(attractionAudios.getAudios());
		return attraction;
	}
	//NO USE
	public Attraction getAttractionSummaryById(BigInteger attractionId)
	{
		return attractionMapper.getAttractionSummaryById(attractionId);
	}

	public int getSumOfAttractions()
	{
		return attractionMapper.getSumOfAttractions();
	}


	@Override
	public Attraction getAttractionByTitle(String title)
	{
		return attractionMapper.getAttractionByTitle(title);
	}
	@Override
	public int getSumAttraction()
	{
		return attractionMapper.getSumAttraction();
	}
	@Override
	public List<Attraction> getAllAttractionTitle()
	{
		return attractionMapper.getAllAttractionTitle();
	}


	//AttractionImage
	public boolean addImage(AttractionImage attractionImage) {
		return attractionImageMapper.addImage(attractionImage);
	}
	public boolean updateImageById(BigInteger imageId,String imageUrl) {
		return attractionImageMapper.updateImageById(imageId, imageUrl);

	}
	public boolean deleteImageById(BigInteger imageId) {
		return attractionImageMapper.deleteImageById(imageId);
	}

	//AttractionAudio
	public boolean addAudio(AttractionAudio attractionAudio)
	{
		return attractionAudioMapper.addAudio(attractionAudio);
	}
	public boolean updateAudioById(BigInteger audioId,String type,String audioUrl)
	{
		return attractionAudioMapper.updateAudioById(audioId, type, audioUrl);
	}
	public boolean deleteAudioById(BigInteger audioId)
	{
		return attractionAudioMapper.deleteAudioById(audioId);
	}

	//RouteBindingAttraction
	//finished
	public boolean addRouteBindingAttraction(RouteBindingAttraction routeBindingAttraction)
	{
		return routeBindingAttractionMapper.addRouteBindingAttraction(routeBindingAttraction);
	}
	//finished
	public boolean deleteRouteBindingAttractionByAttractionId(BigInteger routeId,BigInteger attractionId)
	{
		return routeBindingAttractionMapper.deleteRouteBindingAttractionByAttractionId(routeId, attractionId);
	}
	//NO USE
	public boolean deleteRouteBindingAttractionsByRouteId(BigInteger routeId)
	{
		return routeBindingAttractionMapper.deleteRouteBindingAttractionsByRouteId(routeId);
	}
	//finished
	public boolean updateOrderOfRouteBindingAttractionById(BigInteger id,int order)
	{
		return routeBindingAttractionMapper.updateOrderOfRouteBindingAttractionById(id, order);
	}
}
