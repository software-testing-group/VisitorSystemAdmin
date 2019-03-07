package beike.visitorsystem.scenery.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import beike.visitorsystem.scenery.model.*;
import org.apache.ibatis.annotations.Param;

public interface SceneryService {
	public boolean updateVoiceIntroductionById(BigInteger attractionId,String voiceIntroduction);
	public boolean updateTextIntroductionById(BigInteger attractionId,String textIntroduction);
	public boolean updateTitleById(BigInteger attractionId,String title);
	public boolean updateAttractionNumberById(BigInteger attractionId,String attractionNumber);

//Route
	//NO.1
	boolean addRoute(Route route,BigInteger[] attractionIds);
	//NO.2
	boolean updateRoute(Route route);
	//NO.3
	boolean deleteRouteById(BigInteger routeId);
	//NO.4
	List<Route> getRoutes(int page,int number);
	//NO.5
	Route getRouteDetailById(BigInteger routeId);

	int getSumOfRoutes();

	public List<Route> getAllRoute();

	public Route getRouteById(BigInteger routeId);
	public boolean updateRouteNameById(BigInteger routeId,String routeName);
	public boolean updateSpecificRouteById(BigInteger routeId,String specificRoute);
	public boolean updateRouteTextIntroductionById(BigInteger routeId,String textIntroduction);

//Attraction
	//NO.1
	boolean addAttraction(Attraction attraction);
	//NO.2
	boolean deleteAttractionById(BigInteger attractionId);
	//NO.3
	boolean updateAttraction(Attraction attraction);
    //NO.4
	List<Attraction> getAllAttraction(int page,int number);
	//NO.5 £¿
	//Attraction getAttractionAndImagesById(BigInteger attractionId);
	//Attraction getAttractionAndAudiosById(BigInteger attractionId);
    //NO.6
	Attraction getAttractionSummaryById(BigInteger attractionId);

	int getSumOfAttractions();


	public Attraction getAttractionByTitle(String title);
	public int getSumAttraction();
	public List<Attraction> getAllAttractionTitle();
    //NO USE
	public Attraction getAttractionById(BigInteger attractionId);

//AttractionImage
	//NO.1
	boolean addImage(AttractionImage attractionImage);
	//NO.2
	boolean updateImageById(BigInteger imageId,String imageUrl);
	//NO.3
	boolean deleteImageById(BigInteger imageId);
    //NO USE
	//public List<AttractionImage> getImageByAttractionId(BigInteger attractionId);

//AttractionAudio
	//NO.1
	boolean addAudio(AttractionAudio attractionAudio);
	//NO.2
	boolean updateAudioById(BigInteger audioId,String type,String audioUrl);
	//NO.3
	boolean deleteAudioById(BigInteger audioId);

//RouteBindingAttraction
	//NO.1
	boolean addRouteBindingAttraction(RouteBindingAttraction routeBindingAttraction);
	//NO.2
	boolean deleteRouteBindingAttractionByAttractionId(BigInteger routeId,BigInteger attractionId);
	//NO.3
	boolean deleteRouteBindingAttractionsByRouteId(BigInteger routeId);
	//NO.4
	boolean updateOrderOfRouteBindingAttractionById(BigInteger id,int order);
}
