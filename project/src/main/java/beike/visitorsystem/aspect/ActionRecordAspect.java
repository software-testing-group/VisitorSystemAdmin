package beike.visitorsystem.aspect;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import beike.visitorsystem.authority.model.AdminLog;
import beike.visitorsystem.authority.model.AdminUser;
import beike.visitorsystem.authority.service.AuthorityService;
import beike.visitorsystem.security.Users;
import beike.visitorsystem.utils.GenerateID;

@Aspect
public class ActionRecordAspect {

	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private GenerateID generateID;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
	
	/**
	 * ��¼�û���½ʱ��
	 */
	@AfterReturning("execution(* beike.visitorsystem.authority.controller.AuthorityController.loginDeal(..))")
	public void loginRecord()
	{
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null)
		{
			WebAuthenticationDetails wauth =   (WebAuthenticationDetails) auth.getDetails();
			
			String ip = wauth.getRemoteAddress();
			Users u = (Users) auth.getPrincipal();
			AdminUser adminUser = u.getAdminUser();
			
			authorityService.dealLogin(adminUser.getId(),ip);
		}
	}
	
	/**
	 * ��¼�û�����
	 */
	@AfterReturning(returning="status",pointcut="@annotation(SystemLog)")
	public void monitor(JoinPoint joinPoint,Object status)
	{
		//��ȡ�û���Ϣ
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		WebAuthenticationDetails wauth =   (WebAuthenticationDetails) auth.getDetails();
		
		String ip = wauth.getRemoteAddress();
		Users u = (Users) auth.getPrincipal();
		AdminUser adminUser = u.getAdminUser();
		
		//��ȡ������Ϣ
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		
		SystemLog sysLog = method.getAnnotation(SystemLog.class);
		
		StringBuilder details = new StringBuilder("");
		try {
			//��ȡ�������ƺ�ֵ
			String classType = joinPoint.getTarget().getClass().getName();    
	        Class<?> clazz = Class.forName(classType);    
	        String clazzName = clazz.getName();    
	        String methodName = joinPoint.getSignature().getName(); //��ȡ��������   
	        Object[] args = joinPoint.getArgs();//����  
	           
	        Map<String,Object > nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName,args); 
	        
	        String[] records = sysLog.details().split(";");
	        
	        for(String temp:records)
	        {
	        	String[] values = temp.split(":");
	        	details.append(values[1] + ":" + nameAndArgs.get(values[0]) + ";");
	        }
	        
	        details.deleteCharAt(details.length() - 1);
	        
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
		AdminLog adminLog = new AdminLog();
		adminLog.setAdminUserId(adminUser.getId());
		adminLog.setAdminUsername(adminUser.getUsername());
		adminLog.setCreateTime(df.format(new Date()).toString());
		adminLog.setIp(ip);
		adminLog.setId(generateID.getID());
		
		adminLog.setDescription(sysLog.module() + "|" + sysLog.action() + "|" + details.toString());
		
		authorityService.addAdminLog(adminLog);
	}
	
	private Map<String,Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {   
        Map<String,Object > map=new HashMap<String,Object>();  
          
        ClassPool pool = ClassPool.getDefault();    
        //ClassClassPath classPath = new ClassClassPath(this.getClass());    
        ClassClassPath classPath = new ClassClassPath(cls);    
        pool.insertClassPath(classPath);    
            
        CtClass cc = pool.get(clazzName);    
        CtMethod cm = cc.getDeclaredMethod(methodName);    
        MethodInfo methodInfo = cm.getMethodInfo();  
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();    
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);    
        if (attr == null) {    
            // exception    
        }    
       // String[] paramNames = new String[cm.getParameterTypes().length];    
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;    
        for (int i = 0; i < cm.getParameterTypes().length; i++){    
            map.put( attr.variableName(i + pos),args[i]);//paramNames��������    
        }    
          
        //Map<>  
        return map;    
    }    
}
