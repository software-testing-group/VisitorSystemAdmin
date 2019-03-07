package beike.visitorsystem.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import beike.visitorsystem.authority.model.AdminUser;

public class Users implements UserDetails, CredentialsContainer{

	 private final String username;  
	 
	 private String password; 
	 
	 private AdminUser adminUser; //用户的adminUser实例
	  
	 private final Set<GrantedAuthority> authorities;  
	  
	 private final boolean accountNonExpired;  
	  
	 private final boolean accountNonLocked;  
	  
	 private final boolean credentialsNonExpired;  
	  
	 private final boolean enabled;  
	
	private static final long serialVersionUID = -6539308815733882151L;

	public Users(String password, AdminUser adminUser,  
			   final String username,  
			   final Collection<? extends GrantedAuthority> authorities,  
			   final boolean accountNonExpired, final boolean accountNonLocked,  
			   final boolean credentialsNonExpired, final boolean enabled)
	{
		if (username == null || "".equals(username) || password == null)  
			   throw new IllegalArgumentException(  
			     "用户名或密码为空，不能放入构造");  
		this.password = password;  
		this.adminUser = adminUser;
		this.username = username;  
		this.accountNonExpired = accountNonExpired;  
		this.accountNonLocked = accountNonLocked;  
		this.credentialsNonExpired = credentialsNonExpired;  
		this.enabled = enabled;  
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));  
	}
	
	@Override
	public void eraseCredentials() {
		// TODO Auto-generated method stub
		password = null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	public AdminUser getAdminUser(){
		return adminUser;
	}

	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {  
		
		Assert.notNull(authorities, "不能放入空操作集");  
		// Ensure array iteration order is predictable (as per  
		// UserDetails.getAuthorities() contract and SEC-717)  
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());  
			  
		for (GrantedAuthority grantedAuthority : authorities) {  
			Assert.notNull(grantedAuthority,"不能放入空操作集合");  
			  sortedAuthorities.add(grantedAuthority);  
		}  
			  
		return sortedAuthorities;  
    }  
	
	private static class AuthorityComparator implements  
	   Comparator<GrantedAuthority>, Serializable {  

	 public int compare(GrantedAuthority g1, GrantedAuthority g2) {  
	   // Neither should ever be null as each entry is checked before  
	   // adding it to the set.  
	   // If the authority is null, it is a custom authority and should  
	   // precede others.  
	   if (g2.getAuthority() == null) {  
	    return -1;  
	   }  
	  
	   if (g1.getAuthority() == null) {  
	    return 1;  
	   }  
	  
	   return g1.getAuthority().compareTo(g2.getAuthority());  
	  }  
	 }  
}
