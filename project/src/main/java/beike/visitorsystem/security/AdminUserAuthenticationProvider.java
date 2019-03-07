package beike.visitorsystem.security;

import java.security.MessageDigest;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import beike.visitorsystem.authority.model.AdminUser;
import beike.visitorsystem.authority.model.Role;
import beike.visitorsystem.utils.BCrypt;

public class AdminUserAuthenticationProvider implements AuthenticationProvider{

	private final AdminUserSecurityService userDetailsService;  
    public AdminUserAuthenticationProvider(AdminUserSecurityService userDetailsService) {  
        this.userDetailsService = userDetailsService;  
    } 
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		
		    UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;  
	        String username = token.getName();  
	        //�����ݿ��ҵ����û�  
	        Users userDetails = null;  
	        if(username != null) {  
	            userDetails = (Users) userDetailsService.loadUserByUsername(username);  
	        }  
	        //Messages  
	        if(userDetails == null) {  
	            throw new UsernameNotFoundException("�û���/������Ч"); 
	        }
	        else
	        {
	        	AdminUser adminUser = userDetails.getAdminUser();
	        	
	        	if(adminUser.getIsBanned() == 1)
	        		throw new DisabledException("�û��ѱ�����");    	
	        	
	        	if(!md5(adminUser.getId() + adminUser.getUsername() + adminUser.getPassword() + adminUser.getStaffId() + listToString(adminUser.getRoleList(),',') + adminUser.getIsBanned()).equals(adminUser.getSignature()))
			    	throw new LockedException("�û����۸�");
	        
	        	if(userDetails.getAuthorities().isEmpty()){
		        	throw new LockedException("���û�û���κ�Ȩ��");
		        }   
	        }
	        
	        //���ݿ��û�������  
	        String password = userDetails.getPassword();  
	        //��authentication�����credentials��Ƚ�  
	        if(!BCrypt.checkpw(token.getCredentials().toString(), password)) {  
	            throw new BadCredentialsException("�û���/������Ч");  
	        }  
	        //��Ȩ  
	        return new UsernamePasswordAuthenticationToken(userDetails, password,userDetails.getAuthorities());  
	}

	public Authentication xmuAuthenticate(String staffId) throws AuthenticationException {
		
		//�����ݿ��ҵ����û�  
        Users userDetails = null;  
        userDetails =  (Users) userDetailsService.loadUserByStaffId(staffId);
        
        //Messages  
        if(userDetails == null) {  
            throw new UsernameNotFoundException("��ѧ���Ų��ڱ�ϵͳ��"); 
        }else
        {
        	if(userDetails.getAuthorities().isEmpty()){
	        	throw new LockedException("���û�û���κ�Ȩ��");
	        } 
        	
        	AdminUser adminUser = userDetails.getAdminUser();
        	//��Ȩ  
	        return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
        }
		
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		//����true��Ż�ִ�������authenticate����,�ⲽ��ȷ��authentication����ȷת������  
        return UsernamePasswordAuthenticationToken.class.equals(authentication); 
	}

	/**
	 * signature About
	 * @param str �û�id,�û���,����,ѧ����,��ɫ�б�,�Ƿ����
	 * @return
	 */
	private String md5(String str){
		
		str = str + "yigexigua";
		try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytes = md.digest(str.getBytes("utf-8"));
	        return toHex(bytes);
	    }
	    catch (Exception e) {
	    	System.out.println(e);
	        throw new RuntimeException(e);
	    }
    }
	private String toHex(byte[] bytes) {

	    final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
	    StringBuilder ret = new StringBuilder(bytes.length * 2);
	    for (int i=0; i<bytes.length; i++) {
	        ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
	        ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
	    }
	    
	    return ret.toString();
	}
	private String listToString(List<Role> list, char separator) {
		
		if(list == null)
			return "";
		
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i).getId());
            } else {
                sb.append(list.get(i).getId());
                sb.append(separator);
            }
        }
        return sb.toString();
    }
}
