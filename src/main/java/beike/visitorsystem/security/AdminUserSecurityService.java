package beike.visitorsystem.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import beike.visitorsystem.authority.mapper.AdminUserMapper;
import beike.visitorsystem.authority.model.AdminAction;
import beike.visitorsystem.authority.model.AdminUser;
import beike.visitorsystem.authority.model.Role;

@Service
public class AdminUserSecurityService implements UserDetailsService{
	
	@Autowired
	AdminUserMapper adminUserMapper;
	
	@Transactional
	public UserDetails loadUserByStaffId(String staffId) throws UsernameNotFoundException {
	
		AdminUser adminUser = adminUserMapper.getAdminUserByStaffId(staffId);
		if(adminUser == null){
	         return null;
	    }else 
	    {
		    	// -- mini-web示例中无以下属性, 暂时全部设为true. --//  
		    	boolean enabled = true;  
		    	boolean accountNonExpired = true;  
		    	boolean credentialsNonExpired = true;  
		    	boolean accountNonLocked = true; 
		    	
				//List<SimpleGrantedAuthority> authorities = new ArrayList<>();

				Set<GrantedAuthority> authSet = new HashSet();
				
				if(adminUser.getRoleList() != null)
		        for(Role role:adminUser.getRoleList())
		        {
		        	for(AdminAction action:role.getActionList())
		        	{
		        		authSet.add(new SimpleGrantedAuthority("ROLE_" + action.getActionCode())); 
		        		//authorities.add(new SimpleGrantedAuthority("ROLE_" + action.getActionCode()));
		        	}
		        }
//		        return new User(adminUser.getUsername(),
//		                adminUser.getPassword(), authorities);
			
		        return new Users(adminUser.getPassword(), adminUser, adminUser.getUsername(), authSet,  
		        	    accountNonExpired, accountNonLocked, credentialsNonExpired,  
		        	    enabled);
		   
	    }
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		AdminUser adminUser = adminUserMapper.getAdminUser(username);
		if(adminUser == null){
	         return null;
	    }else 
	    {   	
		    	// -- mini-web示例中无以下属性, 暂时全部设为true. --//  
		    	boolean enabled = true;  
		    	boolean accountNonExpired = true;  
		    	boolean credentialsNonExpired = true;  
		    	boolean accountNonLocked = true; 
		    	
				//List<SimpleGrantedAuthority> authorities = new ArrayList<>();

				Set<GrantedAuthority> authSet = new HashSet();
				
				if(adminUser.getRoleList() != null)
		        for(Role role:adminUser.getRoleList())
		        {
		        	for(AdminAction action:role.getActionList())
		        	{
		        		authSet.add(new SimpleGrantedAuthority("ROLE_" + action.getActionCode())); 
		        		//authorities.add(new SimpleGrantedAuthority("ROLE_" + action.getActionCode()));
		        	}
		        }
//		        return new User(adminUser.getUsername(),
//		                adminUser.getPassword(), authorities);
			
		        return new Users(adminUser.getPassword(), adminUser, adminUser.getUsername(), authSet,  
		        	    accountNonExpired, accountNonLocked, credentialsNonExpired,  
		        	    enabled);
	    }
	}
	
	/**
	 * signature About
	 * @param str 用户id,用户名,密码,学工号,角色列表,是否禁用
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