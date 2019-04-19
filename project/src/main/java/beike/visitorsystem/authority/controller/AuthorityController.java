package beike.visitorsystem.authority.controller;
/**
 * @email:ping982313926@qq.com
 */

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import beike.visitorsystem.aspect.SystemLog;
import beike.visitorsystem.authority.model.AdminAction;
import beike.visitorsystem.authority.model.AdminLog;
import beike.visitorsystem.authority.model.AdminUser;
import beike.visitorsystem.authority.model.Role;
import beike.visitorsystem.authority.model.Settings;
import beike.visitorsystem.authority.service.AuthorityService;
import beike.visitorsystem.security.AdminUserAuthenticationProvider;
import beike.visitorsystem.security.AdminUserSecurityService;
import beike.visitorsystem.security.Users;
import beike.visitorsystem.utils.GenerateCaptcha;

@Controller
public class AuthorityController {

	@Autowired
	private GenerateCaptcha generateCaptcha;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	AdminUserSecurityService securityService;
	
	/**
	 * 没有权限时的处理
	 * @return  403界面
	 */
	@RequestMapping("/403")
	public String permissionDeny()
	{
		return "403";
	}
	
	/**
	 * 学工系统退出登录
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/xmulogin/logout")
	public void logout(HttpServletResponse response) throws IOException
	{
		response.sendRedirect("http://ids.xmu.edu.cn/authserver/logout?service=http://fkyy.xmu.edu.cn/");
	}
	
	/**
	 * 默认跳转
	 * @return
	 */
	@RequestMapping("/")
	public String init()
	{
		return "redirect:/login";
	}
	
	/**
	 * 新增一个管理员用户
	 * @adminUser AdminUser对象，其中必须包含有username,password
	 * @return
	 */
	@RequestMapping(value = "/authority/register", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	@SystemLog(module = "人员管理",action = "新增用户",details = "username:用户名;staffId:学工号")
	public String register(String username,String password,String staffId)
	{
		if(authorityService.getAdminUser(username) != null)
			return "该用户名已经被占用！";
		else if(!staffId.equals("") && authorityService.getAdminUserByStaffId(staffId) != null)
		{
			return "该学工号已存在于其他用户中！";
		}
		else 
		{

			if(authorityService.addAdminUser(username, password, staffId))
				return "新增成功";
			else
				return "新增失败";
		}
			
	}

	/**
	 * 登陆，若已登陆则自动跳转到主页
	 * @param error  参数
	 * @return 登陆页面  or 主页
	 */
	@RequestMapping("/login")
	public String login(@RequestParam(value = "error",required = false) boolean error) {

		//如果已经登陆跳转到个人首页
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if(authentication != null && !authentication.getPrincipal().equals("anonymousUser") &&
		   authentication.isAuthenticated())
	    {
	    	return "redirect:/mainpage";
	    }
	    
		return "login";
	}
	
	/**
	 * 学工登录，转发
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/xmulogin")
	public void xmuLogin (HttpServletRequest request,HttpServletResponse response)throws IOException {
        response.sendRedirect("http://ids.xmu.edu.cn/authserver/login?service=http://fkyy.xmu.edu.cn/xmulogin/callback");
    }
	
	/**
	 * 转发中介（忘了为啥要这样了）
	 * @return
	 */
	@RequestMapping("/loginDeal")
	@PreAuthorize("!isAnonymous()")
	public String loginDeal()
	{
		return "redirect:/mainpage";
	}
	
	/**
	 * 学工系统登陆的回调
	 * @param ticket
	 * @param model
	 * @return  
	 * @throws Exception
	 */
	@RequestMapping("/xmulogin/callback")
	public String callback(String ticket,Model model)throws Exception{
		
	        String XmlUrl="http://ids.xmu.edu.cn/authserver/serviceValidate?ticket="+ticket+"&service=http://fkyy.xmu.edu.cn/xmulogin/callback";
		
	        URL url = new URL(XmlUrl);////获得url对象
	        URLConnection conn = url.openConnection();  //创建URLConnection连接
	        conn.setReadTimeout(5*1000);
	        conn.setDoInput(true);
	        conn.connect();
	        
	        Document doc = new SAXReader().read(conn.getInputStream());
		    //根元素
	        Element root = doc.getRootElement();
	        List<Element> childElements = root.elements();//获取当前元素下的全部子元素
	        //登录标志
	        Element check = childElements.get(0);
	        //成功
	        if(check.getName().equals("authenticationSuccess")){
	            //获取用户名
	            Element userElement = check.element("user");
	            String staffId = userElement.getText();
	             
	    		try {
	    			Authentication authentication = new AdminUserAuthenticationProvider(securityService).xmuAuthenticate(staffId);
	    			SecurityContextHolder.getContext().setAuthentication(authentication);
	    			return "redirect:/login";
	    		}
	    		catch(AuthenticationException e)
	    		{
	    			model.addAttribute("message",e.getMessage());
	    			return "login";
	    		}
	    		
	        }
	        else {
	        	model.addAttribute("message","学工系统登录失败");
	        	return "redirect:/login?error=true";
	        }
	    }

	/**
	 * 验证验证码是否正确,人机验证
	 * 验证码存在了httpsession里，randomString
	 */
	@RequestMapping(value = "/checkCaptcha", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String checkCaptcha(String captcha,HttpSession session) {
		String answer;
		boolean result;
		String randomString = (String) session.getAttribute("randomString");
		randomString = randomString.toLowerCase();
		result = randomString.equals(captcha.toLowerCase());
		if(result)
			answer = "true" ;
		else
			answer = "false";

		return answer;
	}

	/**
	 * 获取验证码
	 * @return 图片流
	 */
	@RequestMapping("/captcha.form")
	@ResponseBody
	public void generateCaptcha(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException  
	{
		generateCaptcha.outputCaptcha(request, response);
	}

	/**
	 * 跳转到主页面
	 * @return 跳转到主页面
	 */
	@RequestMapping("/mainpage")
	@PreAuthorize("!isAnonymous()")
	public String mainView()
	{
		//如果已经登陆跳转到个人首页
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if(authentication == null && authentication.getPrincipal().equals("anonymousUser") &&
		   !authentication.isAuthenticated())
	    {
	    	return "redirect:/login";
	    }
	    
		return "mainpage";
	}

	/**
	 * 打开用户列表页
	 * @return 用户列表界面
	 */
	@RequestMapping("/authority/manageAdminUser")
	@PreAuthorize("hasRole('manage_person')")
	public String userManage()
	{
		return "authority/manageAdminUser";
	}

	/**
	 * 获取用户列表数据
	 * @param page   页数
	 * @return  List<AdminUser>
	 */
	@RequestMapping(value = "/authority/getPersonList",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('manage_person')")
	public List<AdminUser> getPersonList(int page)
	{
		if(page < 1)
			page = 1;
		
		return authorityService.getAdminUserList(page, 10);
	}

	/**
	 * 获取过滤后的用户列表数据
	 * @param page   页数
	 * @param username   用户名
	 * @param roleId  角色id
	 * @param start  开始时间
	 * @param end   结束时间
	 * @return  List<AdminUser>
	 */
	@RequestMapping(value = "/authority/getPersonListFilter",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('manage_person')")
	public List<AdminUser> getPersonListFilter(int page,BigInteger staffId,String username,BigInteger roleId,String start,String end)
	{
		if(page < 1)
			page = 1;
		if(username.equals(""))
			username = null;
		if(start.equals(""))
			start= null;
		if(end.equals(""))
			end= null;

		return authorityService.getAdminUserListFilter(staffId,roleId, username, start, end, page, 10);
	}
	
	/**
	 * 获取所有角色
	 * @return   List<Role>
	 */
	@RequestMapping(value = "/authority/getRoleListAll",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('manage_person')")
	public List<Role> getRoleListAll()
	{
		return authorityService.getRoleListAll();
	}

	/**
	 * 禁用用户
	 * @param id  用户id
	 * @return true or false
	 */

	@RequestMapping(value = "/authority/banAdminUser", method = RequestMethod.POST )
	@ResponseBody
	@PreAuthorize("hasRole('ban_person')")
	@SystemLog(module = "人员管理",action = "禁用用户",details = "id:用户id")
	public boolean banAdminUser(BigInteger id,int isBanned)
	{
		return authorityService.banAdminUser(id,isBanned);
	}

	/**
	 * 修改一个管理员的角色
	 * @param adminUserId 管理员id
	 * @return  true or false
	 */
	@RequestMapping(value = "/authority/editAdminUserRole", method = RequestMethod.POST , produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('edit_person')")
	@SystemLog(module = "人员管理",action = "修改用户角色",details = "adminUserId:管理员id;list:角色id列表")
	public boolean editAdminUserRole(BigInteger adminUserId,String list)
	{
		return authorityService.editAdminUserRole(adminUserId, list);
	}

	/**
	 * 打开角色列表界面
	 * @param model
	 * @return  角色列表界面
	 */
	@RequestMapping("/authority/manageRole")
	@PreAuthorize("hasRole('manage_role')")
	public String roleManage(Model model)
	{
		return "authority/manageRole";
	}

	/**
	 * 获取角色列表数据
	 * @param page  页数
	 * @return List<Role>
	 */
	@RequestMapping(value = "/authority/getRoleList",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('manage_role')")
	public List<Role> getRoleList(int page)
	{
		if(page < 1)
			page = 1;
		return authorityService.getRoleList(page, 10);
	}
	/**
	 * 获取所有权限数据
	 * @return  List<AdminAction>
	 */
	@RequestMapping(value = "/authority/getAdminActionListAll",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('manage_role')")
	public List<AdminAction> getAdminActionListAll()
	{
		return authorityService.getAdminActionListAll();
	}

	/**
	 * 通过角色id获取该角色
	 * @param roleId  角色id
	 * @return role
	 */
	@RequestMapping(value = "/authority/getRoleByRoleId",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('edit_role')")
	public Role getRole(BigInteger roleId)
	{
		return authorityService.getRoleByRoleId(roleId);
	}

	/**
	 * 修改角色的权限
	 * @param roleId  角色id
	 * @param list  List<操作权限id>
	 * @return  是/否
	 */
	@RequestMapping(value = "/authority/editRole", method = RequestMethod.POST , produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('edit_role')")
	@SystemLog(module = "角色管理",action = "修改角色",details = "roleId:角色id;list:操作id列表")
	public boolean editRoleAction(BigInteger roleId,String roleName,String roleDescription,String list)
	{
		
		return authorityService.editRole(roleId, list, roleName , roleDescription);
	}

	/**
	 * 删除某个角色
	 * @param roleId  角色id
	 * @return 提示
	 */
	@RequestMapping(value = "/authority/deleteRole", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	@PreAuthorize("hasRole('delete_role')")
	@SystemLog(module = "角色管理",action = "删除角色",details = "roleId:角色id")
	public String deleteRole(BigInteger roleId)
	{
		List<AdminUser> adminUserList = authorityService.getAdminUserListFilter(new BigInteger("0"),roleId,"","","", 1, 10);

		//先判断角色下面有没有用户
		if(!adminUserList.isEmpty())
			return "还有管理员是该角色，无法删除";
		else if(authorityService.deleteRole(roleId))
			return "删除成功";
		else
			return "删除失败";
	}

	/**
	 * 打开管理员操作记录界面
	 * @return 操作记录页面
	 */
	@RequestMapping("/authority/manageAdminLog")
	@PreAuthorize("hasRole('manage_log')")	
	public String adminLogManage()
	{
		return "authority/manageAdminLog";
	}

	/**
	 * 获取操作记录数据（带filter的）
	 * @param page
	 * @return List<AdminLog>
	 */
	@RequestMapping(value = "/authority/getAdminLogList",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('manage_log')")
	public List<AdminLog> getAdminLogList(int page,String username,String content,String start,String end)
	{ 
		if(page < 1)
			page = 1;
		return authorityService.getAdminLogList(username,content,start,end,page, 10);
	}

	/**
	 * 检查是否存在了该角色名字
	 * @param roleName   欲新增的角色的名字
	 * @return  true or false
	 */
	@RequestMapping(value = "/authority/checkRoleName",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('add_role')")
	public boolean checkRoleName(String roleName)
	{
		return authorityService.checkRoleName(roleName);
	}

	/**
	 * 新增角色
	 * @param roleName  角色code
	 * @param description   描述
	 * @param actionList  权限列表
	 * @return true or false
	 */
	@RequestMapping(value = "/authority/addRole",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('add_role')")
	public boolean addRole(String roleName,String description,String actionList)
	{
		return authorityService.addRole(roleName, description, actionList);
	}

	/**
	 * 跳转到其余设置页面
	 * @return
	 */
	@RequestMapping("/authority/extraSettings")
	@PreAuthorize("hasRole('settings')")
	public String loadExtraSettings()
	{
		return "extraSettings";
	}

	/**
	 * 得到所有的设置
	 * @return List<Settings>
	 */
	@RequestMapping(value = "/authority/getSettings",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('settings')")
	public List<Settings> getSettings()
	{
		return authorityService.selectSettings();
	}

	/**
	 * 修改一个设置
	 * @param id
	 * @param value
	 * @return true or false
	 */
	@RequestMapping(value = "/authority/editSettings",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('settings')")
	public boolean editSettings(String id,String value)
	{
		return authorityService.updateSettings(new BigInteger(id),value);
	}

	/**
	 * 检查旧密码是否正确
	 * @param oldPassword
	 * @return true or false
	 */
	@RequestMapping(value = "/authority/checkOldPassword",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("!isAnonymous()")
	public boolean checkOldPassword(String oldPassword)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users u = (Users) auth.getPrincipal();
		AdminUser adminUser = u.getAdminUser();
		
		return BCrypt.checkpw(oldPassword, adminUser.getPassword());
	}

	/**
	 * 修改密码
	 * @param password  新密码
	 * @return  true or false
	 */
	@RequestMapping(value = "/authority/editPassword",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("!isAnonymous()")
	public boolean editPassword(String password)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users u = (Users) auth.getPrincipal();
		AdminUser adminUser = u.getAdminUser();
		System.out.println("hey" + password + " " + adminUser.getId());
		return authorityService.editPassword(adminUser.getId(), password);
	}

	@RequestMapping(value = "/authority/getSumAdminUser",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('manage_person')")
	public int getSumAdminUser(String username,BigInteger staffId,BigInteger roleId,String start,String end)
	{

		if(username.equals(""))
			username = null;
		if(start.equals(""))
			start= null;
		if(end.equals(""))
			end= null;

		return authorityService.getSumAdminUser(username,staffId,roleId,start,end);
	}

	@RequestMapping(value = "/authority/getSumLogs",produces = "application/json;charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('manage_log')")
	public int getSumLogs(String username,String content,String start,String end)
	{

		if(username.equals(""))
			username = null;
		if(content.equals(""))
			content = null;
		if(start.equals(""))
			start= null;
		if(end.equals(""))
			end= null;

		return authorityService.getSumLogs(username,content,start,end);
	}


}
