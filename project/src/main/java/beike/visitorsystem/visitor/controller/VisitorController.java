package beike.visitorsystem.visitor.controller;

import beike.visitorsystem.aspect.SystemLog;
import beike.visitorsystem.utils.CreateSimpleExcelToDisk;
import beike.visitorsystem.visitor.model.VisitorIdentity;
import beike.visitorsystem.visitor.model.VisitorUser;
import beike.visitorsystem.visitor.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/visitor")
public class VisitorController {
//    @Autowired
//    private HttpSession session;
    @Autowired
    private VisitorService visitorService;

    @RequestMapping("/manageVisitorUser")
    @PreAuthorize("hasRole('manage_visitor')")
    public String manageVisitorUser() {
        return "/visitor/manageVisitorUser";
    }
    
    @RequestMapping(value="/getAllVisitorUser" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    @PreAuthorize("hasRole('manage_visitor')")
    public List<VisitorUser> getVisitorUser(int page) {
		if(page < 1)
			page = 1;
		// 测试
		System.out.println("8888888888888888888888888"+visitorService.getAllVisitorUserAndIdentity(page,10).size());
        return visitorService.getAllVisitorUserAndIdentity(page,10);
    }

    @RequestMapping("/getAllNoBannedVisitorUser")
    @ResponseBody
    @PreAuthorize("hasRole('manage_visitor')")
    public List<VisitorUser> getAllNoBannedVisitorUser(int page) {
		if(page < 1)
			page = 1;   
        return visitorService.getAllNoBannedVisitorUser(page, 10);
    }

    @RequestMapping("/getAllBannedVisitorUser")
    @ResponseBody
    @PreAuthorize("hasRole('manage_visitor')")
    public List<VisitorUser> getAllBannedVisitorUser(int page) {
		if(page < 1)
			page = 1;   
        return visitorService.getAllBannedVisitorUser(page, 10);
    }
    @RequestMapping("/getAllNoBannedVisitorIdentity")
    @ResponseBody
    @PreAuthorize("hasRole('manage_identity')")
    public List<VisitorIdentity> getAllNoBannedVisitorIdentity(int page) {
    	if(page<1)
    		page=1;
        return visitorService.getAllNoBannedVisitorIdentity(page, 10);
    }
    @RequestMapping("/getAllBannedVisitorIdentity")
    @ResponseBody
    @PreAuthorize("hasRole('manage_identity')")
    public List<VisitorIdentity> getAllBannedVisitorIdentity(int page) {
    	if(page<1)
    		page=1;
        return visitorService.getAllBannedVisitorIdentity(page, 10);
    }

    @RequestMapping(value="/defriendVisitorUser",method=RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('defriend_visitor')")
    @SystemLog(module = "用户管理",action = "禁用用户",details = "id:用户id")
    public boolean defriendVisitorUser(BigInteger id) throws IOException {
        return visitorService.defriendVisitorUser(id);
    }
    @RequestMapping(value="/refriendVisitorUser",method=RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('refriend_visitor')")
    @SystemLog(module = "用户管理",action = "解禁用户",details = "id:用户id")
    public boolean refriendVisitorUser(BigInteger id) throws IOException {
        return visitorService.refriendVisitorUser(id);
    }
    
    @RequestMapping("/manageVisitorIdentity")
    @PreAuthorize("hasRole('manage_identity')")
    public String VisitorIdentityManager(){
        return "/visitor/manageVisitorIdentity";
    }
    @RequestMapping(value="/getAllVisitorIdentity" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    @PreAuthorize("hasRole('manage_identity')")
    public List<VisitorIdentity> getVisitorIdentity(int page) {
		if(page < 1)
			page = 1;   
        return visitorService.getAllVisitorIdentity(page, 10);
    }

    @RequestMapping(value="/defriendVisitorIdentity",method=RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('defriend_identity')")
    @SystemLog(module = "证件管理",action = "禁用证件",details = "id:证件id")
    public boolean defriendVisitorIdentity(BigInteger id) {
    	return visitorService.defriendVisitorIdentity(id);
        
    }
    @RequestMapping(value="/refriendVisitorIdentity",method=RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('refriend_identity')")
    @SystemLog(module = "证件管理",action = "解禁证件",details = "id:证件id")
    public boolean refriendVisitorIdentity(BigInteger id) {
    	return visitorService.refriendVisitorIdentity(id);
    }

    @RequestMapping("/VisitorUserDetails")
    @ResponseBody
    @PreAuthorize("hasRole('manage_visitor')")
    public VisitorUser VisitorUserDetails(BigInteger id) {
    	VisitorUser visitorUser=visitorService.getVisitorDetailsById(id);
    	//获得预约信息
    	VisitorUser visitorReservation=visitorService.getVisitorReservationById(id);
    	//装填预约信息         reservation中包含reservationTime信息
    	visitorUser.setVisitorReservationList(visitorReservation.getVisitorReservationList());
    	return visitorUser;
    	
    }
    @RequestMapping(value="/searchVisitorUser",method=RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('manage_visitor')")
    public List<VisitorUser> searchVisitorUser(String identity,int page) {
 
    	return visitorService.getVisitorUserByIdentity(identity,page,10);
    }
    @RequestMapping(value="/getVisitorIdentityById",method=RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('manage_identity')")
    public VisitorIdentity getVisitorIdentityById(BigInteger id) {

    	return visitorService.getVisitorIdentityById(id);
    }
    @RequestMapping(value="/searchVisitorIdentity",method=RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('manage_identity')")
    public List<VisitorIdentity> searchVisitorIdentity(String identity,int isBanned,int page) {
    	
    	return visitorService.getIdentityByCard(identity,isBanned,page,10);
    }

    @RequestMapping(value="/searchVisitorIdentityByName",method=RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('manage_identity')")
    public List<VisitorIdentity> searchVisitorIdentityByName(String name,int isBanned,int page) {

        return visitorService.getIdentityByName(name,isBanned,page,10);
    }

    @RequestMapping(value="/getSumVisitorUser",method=RequestMethod.POST)
   	@ResponseBody
   	@PreAuthorize("hasRole('manage_visitor')")
    public int getSumVisitorUser() {
    	return visitorService.getSumVisitorUser();
    }
    //yulei
    @RequestMapping(value="/getSumBannedVisitorUser",method=RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('manage_visitor')")
    public int getSumBannedVisitorUser() {
        return visitorService.getSumBannedVisitorUser();
    }
    @RequestMapping(value="/getSumNoBannedVisitorUser",method=RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('manage_visitor')")
    public int getSumNoBannedVisitorUser() {
        return visitorService.getSumNoBannedVisitorUser();
    }


    @RequestMapping(value="/getSumIdentity",method=RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('manage_identity')")
    public int getSumVisitorIdentity() {
    	return visitorService.getSumIdentity();
    }
    //yulei
    @RequestMapping(value="/getSumBannedIdentity",method=RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('manage_visitor')")
    public int getSumBannedIdentity() {
        return visitorService.getSumBannedIdentity();
    }
    @RequestMapping(value="/getSumNoBannedIdentity",method=RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('manage_visitor')")
    public int getSumNoBannedIdentity() {
        return visitorService.getSumNoBannedIdentity();
    }

    @RequestMapping(value="/getSumByIdentity",method=RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('manage_visitor')")
    public int getSumByIdentity(String identity,int isBanned) {
        return visitorService.getSumByIdentity(identity,isBanned);
    }

    @RequestMapping(value="/getSumByName",method=RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('manage_visitor')")
    public int getSumByName(String name,int isBanned) {
        return visitorService.getSumByName(name,isBanned);
    }


    @RequestMapping(value="/downloadExcelToDisk",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    @PreAuthorize("hasRole('export_visitor')")
    public boolean downloadExcelToDisk(HttpServletResponse response,int page,String DownloadType) throws IOException{
    	if(DownloadType.equals("VisitorUser")) {
        List<VisitorUser> visitorUserList=visitorService.getAllVisitorUserAndIdentity(page,10);
        CreateSimpleExcelToDisk csetd=new CreateSimpleExcelToDisk();
        csetd.downloadVisitorExcelToDisk(visitorUserList,response);
    	}
		if (DownloadType.equals("VisitorIdentity")) {
			List<VisitorIdentity> visitorIdentityList = visitorService.getAllVisitorIdentity(page, 10);
			CreateSimpleExcelToDisk csetd = new CreateSimpleExcelToDisk();
			csetd.downloadVisitorIdentityExcelToDisk(visitorIdentityList, response);
		}
    	return true;
    }



}
