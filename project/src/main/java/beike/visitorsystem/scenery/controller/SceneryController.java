package beike.visitorsystem.scenery.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beike.visitorsystem.scenery.model.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import beike.visitorsystem.aspect.SystemLog;
import beike.visitorsystem.scenery.service.SceneryService;
import beike.visitorsystem.utils.GenerateID;
import beike.visitorsystem.utils.HttpRequestor;
import beike.visitorsystem.visitor.model.VisitorUser;

@Controller
@RequestMapping("/scenery")
public class SceneryController {
//    @Autowired
//    private HttpSession session;
	@Autowired
	private SceneryService sceneryService;
	@Autowired
	GenerateID generateID;
	@Autowired
	HttpRequestor httpRequestor;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequestMapping("manageAttraction")
	@PreAuthorize("hasRole('manage_attraction')")
	public String manageAttraction() {
		return "/scenery/manageAttraction";
	}

	@RequestMapping("manageRoute")
	@PreAuthorize("hasRole('manage_route')")
	public String manageRoute() {
		return "/scenery/manageRoute";
	}


	@RequestMapping(value="fileUpload",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@PreAuthorize("hasAnyRole('edit_attraction','add_attraction')")
	public String fileUpload(HttpServletRequest request,HttpServletResponse response,@RequestPart("file") MultipartFile file,@RequestPart("audioFile") MultipartFile audioFile,BigInteger attractionId,BigInteger imageId)  throws IOException {
		
		//String uploadUrl = "172.27.65.65";
		String uploadUrl = "C:\\fkyyRes\\";
		String accessUrl = "http://fkyy.xmu.edu.cn/resources/fkyyRes/";

		System.out.println("---------------");
		System.out.println(file);
		System.out.println(audioFile);
		System.out.println("---------------");

		//获得物理路径webapp所在路径
		String pathRoot = request.getSession().getServletContext().getRealPath("")+"resources/fkyyRes/";
		//String path="";
		//String path2="";

		String path3="";

		if (audioFile.getSize()!=0) { // 音频：上传更新二合一！！
            System.out.println("进入音频上传逻辑");
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            String contentType=audioFile.getContentType();
            //获得文件后缀名称
            String audioName=contentType.substring(contentType.indexOf("/")+1);
            System.out.println("-------:"+audioName);
          //path="resources\\static\\audios\\"+uuid+"."+audioName;

			path3="audios/"+uuid+"."+audioName;

            audioFile.transferTo(new File(pathRoot+path3));
          //String voiceIntroduction = uploadUrl+path3;  // 音频文件存储url
			String voiceIntroduction = accessUrl+path3;  // 音频文件访问url
            sceneryService.updateVoiceIntroductionById(attractionId, voiceIntroduction);  // 更新景点的语音介绍地址
            System.out.println("-------------更新语音 success");

		}	
		//System.out.println(id);
		if (imageId == null&&file.getSize()!=0) {   // 如果图片id为空且图片文件不为空（之前没上传过图片）

			System.out.println("进入图片上传逻辑");
			//生成uuid作为文件名称
			String uuid = UUID.randomUUID().toString().replaceAll("-","");
			String contentType=file.getContentType();
			//获得文件后缀名称
			String imageName=contentType.substring(contentType.indexOf("/")+1);
			System.out.println("-------:"+imageName);
			//path="/static/images/"+uuid+"."+imageName;
		    //path="resources\\static\\images\\"+uuid+"."+imageName;
			//path2="\\upload\\"; // 本机文件服务器的文件路径

			path3="images/"+uuid+"."+imageName;

			file.transferTo(new File(pathRoot+path3));
			AttractionImage attractionImage=new AttractionImage();
			attractionImage.setAttractionId(attractionId);
			attractionImage.setImageUrl(accessUrl+path3);      ///      !!!!往图片表里面加数据
			sceneryService.addImage(attractionImage);
			System.out.println("----------添加图片 success");

		}

		if(file.getSize()!=0&&imageId!=null){     // 该景点之前已经上传过图片了（更新图片）
            System.out.println("进入图片更新逻辑");
            //生成uuid作为文件名称
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            String contentType=file.getContentType();
            //获得文件后缀名称
            String imageName=contentType.substring(contentType.indexOf("/")+1);
            System.out.println("-------:"+imageName);
            //path="/static/images/"+uuid+"."+imageName;
            //path="resources\\static\\images\\"+uuid+"."+imageName;

			path3="images/"+uuid+"."+imageName;

            file.transferTo(new File(pathRoot+path3));

            sceneryService.updateImageById(imageId,accessUrl+path3);
            System.out.println("----------更新图片 success");

		}

		
		return "redirect:/scenery/manageAttraction";

	}
    @RequestMapping(value="/getAllAttractionName" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    @PreAuthorize("hasRole('manage_route')")
    public List<Attraction> getAllAttractionName() {
        return sceneryService.getAllAttractionTitle();
    }



//Attraction
	//NO.1
@RequestMapping("addAttraction")
@ResponseBody
@PreAuthorize("hasRole('add_attraction')")
@SystemLog(module = "景点管理",action = "添加景点",details = "title:景点名称;attractionNumber:景点编号;textIntroduction:文字介绍")
public Attraction addAttraction(Attraction attraction) {

	attraction.setId(generateID.getID());
	attraction.setReadCount(0);
	attraction.setIsDeleted(0);
    sceneryService.addAttraction(attraction);
	return attraction;
}   //NO.2
	@RequestMapping("updateAttraction")
	@ResponseBody
	@PreAuthorize("hasRole('edit_attraction')")
	@SystemLog(module = "景点管理",action = "编辑景点",details = "title:景点名称;attractionNumber:景点编号;textIntroduction:文字介绍;attractionId:景点id")
	public boolean updateAttraction(Attraction attraction) {
		return sceneryService.updateAttraction(attraction);
	}
    //NO.3
	@RequestMapping(value="deleteAttraction")
	@ResponseBody
	@PreAuthorize("hasRole('delete_attraction')")
	@SystemLog(module = "景点管理",action = "删除景点",details = "id:景点id")
	public boolean deleteAttraction(BigInteger id)
	{
		return sceneryService.deleteAttractionById(id);
	}
    //NO.4
	@RequestMapping(value="/getAllAttraction" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('manage_attraction')")
	public List<Attraction> getAllAttraction(int page) {
		if(page < 1)
			page = 1;
		return sceneryService.getAllAttraction(page,10);
	}
    //NO.5
	@RequestMapping("getAttractionById")
	@ResponseBody
	@PreAuthorize("hasRole('manage_attraction')")
	public Attraction getAttractionById(BigInteger id)
	{
		return sceneryService.getAttractionById(id);
	}
    //NO.6
	@RequestMapping(value="/getSumOfAttractions")
	@ResponseBody
	@PreAuthorize("hasRole('manage_attraction')")
	public int getSumOfAttractions()
	{
		return sceneryService.getSumOfAttractions();
	}

//Route
    //NO.1
	@RequestMapping("addRoute")
	@ResponseBody
	@PreAuthorize("hasRole('add_route')")
	@SystemLog(module = "路线管理",action = "新增路线",details = "routeName:路线名称;specificRoute:具体路线;textIntroduction:文字介绍")
	public boolean addRoute(String routeName,int time,String content,String attractionIds) {
	    Route route = new Route();
		route.setUseCount(0);
		route.setRouteName(routeName);
		route.setTime(time);
		route.setContent(content);
		JSONObject json = new JSONObject();

        List<BigInteger> idList = json.parseArray(attractionIds,BigInteger.class);
		BigInteger[] ids = new BigInteger[idList.size()];
		for(int i=0;i<idList.size();i++)
		{
			ids[i]=idList.get(i);
		}

		System.out.println("------------:这里是controller！ ");

		for(int i=0;i<ids.length;i++) {
			System.out.println("------------: " +ids[i]);
		}

		return sceneryService.addRoute(route,ids);
	}
	//NO.2
	@RequestMapping("updateRoute")
	@ResponseBody
	@PreAuthorize("hasRole('edit_route')")
	@SystemLog(module = "路线管理",action = "修改路线",details = "id:路线id;routeName:路线名称;specificRoute:具体路线;textIntroduction:文字介绍")
	public boolean updateRoute(Route route) {
		return  sceneryService.updateRoute(route);
	}
	//NO.3
	@RequestMapping("deleteRoute")
	@ResponseBody
	@PreAuthorize("hasRole('delete_route')")
	public boolean deleteRoute(BigInteger id)
	{
		return sceneryService.deleteRouteById(id);
	}
	//NO.4
	@RequestMapping("getRoutes")
	@ResponseBody
	@PreAuthorize("hasRole('manage_route')")
	public List<Route> getRoutes(int page) {
		return sceneryService.getRoutes(page,10);
	}
	//NO.5
	@RequestMapping("getRouteDetail")
	@ResponseBody
	@PreAuthorize("hasRole('manage_route')")
	public Route getRouteDetail(BigInteger id) {
		return sceneryService.getRouteDetailById(id);
	}

	@RequestMapping(value="/getSumOfRoutes")
	@ResponseBody
	@PreAuthorize("hasRole('manage_attraction')")
	public int getSumOfRoutes()
	{
		return sceneryService.getSumOfRoutes();
	}


	@RequestMapping(value="/getAllRoute" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('manage_route')")
	public List<Route> getAllRoute()
	{
		return sceneryService.getAllRoute();

	}

//RouteBindingAttraction
	//NO.1
@RequestMapping(value="/addBindingAttractionByRouteId" ,produces = "application/json;charset=utf-8")
@ResponseBody
@PreAuthorize("hasRole('manage_route')")
public boolean addRouteBindingAttraction(RouteBindingAttraction routeBindingAttraction)
{
	routeBindingAttraction.setId(generateID.getID());
	routeBindingAttraction.setIsDeleted(0);
	routeBindingAttraction.setCreateTime(df.format(new Date()).toString());
	routeBindingAttraction.setUpdateTime(df.format(new Date()).toString());
	return sceneryService.addRouteBindingAttraction(routeBindingAttraction);
}
    //NO.2
	@RequestMapping(value="/deleteBindingAttractionByAttractionId" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('manage_route')")
	public boolean deleteBindingAttractionByAttractionId(BigInteger routeId,BigInteger attractionId)
	{
		return sceneryService.deleteRouteBindingAttractionByAttractionId(routeId, attractionId);
	}
    //NO.3
	@RequestMapping(value="/modifyBindingAttractionOrder" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('manage_route')")
	public boolean modifyBindingAttractionOrderById(BigInteger id,int order)
	{
		return sceneryService.updateOrderOfRouteBindingAttractionById(id, order);
	}


	//AttractionImages&AttractionAudios
	  //addAttractionImages
	@RequestMapping(value="/imagesUpload",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@PreAuthorize("hasAnyRole('edit_attraction','add_attraction')")
	public String imagesUpload(HttpServletRequest request,@RequestParam("files") MultipartFile[] files,BigInteger attractionId)
	{
		//存入数据库的文件URL
		String accessUrl = "http://fkyy.xmu.edu.cn/resources/fkyyRes/";
		String path="";
		//判断file数组不能为空并且长度大于0
		if(files!=null&&files.length>0){
			//循环获取file数组中得文件
			for(int i = 0;i<files.length;i++){
				MultipartFile file = files[i];
				//保存文件
				if (!file.isEmpty()) {
					try {
						// 文件保存路径(服务器存放文件的地址)
						//String pathRoot =  request.getSession().getServletContext().getRealPath("")+"resources/fkyyRes/";
						//test
						String pathRoot = "/usr/local/apache-tomcat-9.0.7/fkyyRes/";

						String contentType=file.getContentType();
						//获得文件后缀名称
						String imageName=contentType.substring(contentType.indexOf("/")+1);
						String uuid = UUID.randomUUID().toString().replaceAll("-","");
						path="images/"+uuid+"."+imageName;
						//转存文件到服务器上
						file.transferTo(new File(pathRoot+path));

						BigInteger imageId = generateID.getID();
						AttractionImage attractionImage = new AttractionImage();
						attractionImage.setId(imageId);
						attractionImage.setAttractionId(attractionId);
						attractionImage.setImageUrl(accessUrl+path);
						sceneryService.addImage(attractionImage);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}


			}
		}

		return "redirect:/scenery/manageAttraction";
	}


	  //addAttractionAudios
	@RequestMapping(value="/audiosUpload",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@PreAuthorize("hasAnyRole('edit_attraction','add_attraction')")
	public String audiosUpload(HttpServletRequest request,@RequestParam("files") MultipartFile[] files,BigInteger attractionId,String[] types)
	{
		//存入数据库的文件URL
		String accessUrl = "http://fkyy.xmu.edu.cn/resources/fkyyRes/";
		String path="";
		//判断file数组不能为空并且长度大于0
		if(files!=null&&files.length>0){
			//循环获取file数组中得文件
			for(int i = 0;i<files.length;i++){
				MultipartFile file = files[i];
				//保存文件
				if (!file.isEmpty()) {
					try {
						// 文件保存路径(服务器存放文件的地址)
						//String pathRoot =  request.getSession().getServletContext().getRealPath("")+"resources/fkyyRes/";
						//test
						String pathRoot = "/usr/local/apache-tomcat-9.0.7/fkyyRes/";

						String contentType=file.getContentType();
						//获得文件后缀名称
						String audioName=contentType.substring(contentType.indexOf("/")+1);
						String uuid = UUID.randomUUID().toString().replaceAll("-","");
						path="audios/"+uuid+"."+audioName;
						//转存文件到服务器上
						file.transferTo(new File(pathRoot+path));

						System.out.println("---------: 音频文件已上传到服务器");

						BigInteger audioId = generateID.getID();
						AttractionAudio attractionAudio = new AttractionAudio();
						attractionAudio.setId(audioId);
						attractionAudio.setType(types[i]);
						attractionAudio.setAttractionId(attractionId);
						attractionAudio.setAudioUrl(accessUrl+path);
						sceneryService.addAudio(attractionAudio);

						System.out.println("---------: 记录已插入到数据库");

					} catch (Exception e) {
						e.printStackTrace();
					}
				}


			}
		}

		return "redirect:/scenery/manageAttraction";
	}
	
/*add image audio test
    @RequestMapping(value="/addImage",produces="text/html;charset=UTF-8")
    @PreAuthorize("hasAnyRole('edit_attraction','add_attraction')")
    public String addImage(String imageUrl)
    {
		BigInteger imageId = generateID.getID();
		AttractionImage attractionImage = new AttractionImage();
		attractionImage.setId(imageId);
		attractionImage.setAttractionId(imageId);
		attractionImage.setImageUrl(imageUrl);
		sceneryService.addImage(attractionImage);
	    return  "redirect:/scenery/manageAttraction";
    }

	@RequestMapping(value="/addAudio",produces="text/html;charset=UTF-8")
	@PreAuthorize("hasAnyRole('edit_attraction','add_attraction')")
	public String addAudio(String audioUrl)
	{
		BigInteger audioId = generateID.getID();
		AttractionAudio attractionAudio = new AttractionAudio();
		attractionAudio.setId(audioId);
		attractionAudio.setAttractionId(audioId);
		attractionAudio.setAudioUrl(audioUrl);
		attractionAudio.setType("English");
		sceneryService.addAudio(attractionAudio);
		return  "redirect:/scenery/manageAttraction";
	}
*/

	@RequestMapping(value="/deleteImage")
	@ResponseBody
	@PreAuthorize("hasAnyRole('edit_attraction','add_attraction')")
	public boolean deleteImage(String id)
	{
		BigInteger imageId=new BigInteger(id);
		return sceneryService.deleteImageById(imageId);
	}

	@RequestMapping(value="/deleteAudio")
	@ResponseBody
	@PreAuthorize("hasAnyRole('edit_attraction','add_attraction')")
	public boolean deleteAudio(String id)
	{
		BigInteger audioId=new BigInteger(id);
		return sceneryService.deleteAudioById(audioId);
	}

	//addAttractionImage
	@RequestMapping(value="/imageUpload",method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('edit_attraction','add_attraction')")
	@ResponseBody
	public boolean imageUpload(HttpServletRequest request,@RequestParam("file") MultipartFile file,BigInteger attractionId)
	{
		//存入数据库的文件URL
		String accessUrl = "http://fkyy.xmu.edu.cn/resources/fkyyRes/";
		String path="";
		//判断file数组不能为空并且长度大于0
		if(file!=null){
				//保存文件
				if (!file.isEmpty()) {
					try {
						// 文件保存路径(服务器存放文件的地址)
						//String pathRoot =  request.getSession().getServletContext().getRealPath("")+"resources/fkyyRes/";
						//test
						String pathRoot = "/usr/local/apache-tomcat-9.0.7/fkyyRes/";

						String contentType=file.getContentType();
						//获得文件后缀名称
						String imageName=contentType.substring(contentType.indexOf("/")+1);
						String uuid = UUID.randomUUID().toString().replaceAll("-","");
						path="images/"+uuid+"."+imageName;
						//转存文件到服务器上
						file.transferTo(new File(pathRoot+path));

						BigInteger imageId = generateID.getID();
						AttractionImage attractionImage = new AttractionImage();
						attractionImage.setId(imageId);
						attractionImage.setAttractionId(attractionId);
						attractionImage.setImageUrl(accessUrl+path);
						sceneryService.addImage(attractionImage);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else
				{
					return false;
				}

		}
		else
		{
			return false;
		}

		return true;
	}


	//addAttractionAudio
	@RequestMapping(value="/audioUpload",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@PreAuthorize("hasAnyRole('edit_attraction','add_attraction')")
	public String audioUpload(HttpServletRequest request,@RequestParam("file") MultipartFile file,BigInteger attractionId,String type)
	{
		//存入数据库的文件URL
		String accessUrl = "http://fkyy.xmu.edu.cn/resources/fkyyRes/";
		String path="";
		//判断file数组不能为空并且长度大于0
		if(file!=null){
				//保存文件
				if (!file.isEmpty()) {
					try {
						// 文件保存路径(服务器存放文件的地址)
						//String pathRoot =  request.getSession().getServletContext().getRealPath("")+"resources/fkyyRes/";
						//test
						String pathRoot = "/usr/local/apache-tomcat-9.0.7/fkyyRes/";

						String contentType=file.getContentType();
						//获得文件后缀名称
						String audioName=contentType.substring(contentType.indexOf("/")+1);
						String uuid = UUID.randomUUID().toString().replaceAll("-","");
						path="audios/"+uuid+"."+audioName;
						//转存文件到服务器上
						file.transferTo(new File(pathRoot+path));

						BigInteger audioId = generateID.getID();
						AttractionAudio attractionAudio = new AttractionAudio();
						attractionAudio.setId(audioId);
						attractionAudio.setType(type);
						attractionAudio.setAttractionId(attractionId);
						attractionAudio.setAudioUrl(accessUrl+path);
						sceneryService.addAudio(attractionAudio);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		}

		return "redirect:/scenery/manageAttraction";
	}

	@RequestMapping("infrastructureRoute")
	public String infrastructureRoute() {
		return "/navigation/manageInfrastructure";
	}

	@RequestMapping("questionRoute")
	public String questionRoute() {
		return "/navigation/questionAndResponse";
	}

}


		

