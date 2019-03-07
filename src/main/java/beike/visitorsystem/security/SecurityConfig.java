package beike.visitorsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	AdminUserSecurityService adminUserSecurityService(){ //注册UserDetailsService 的bean
        return new AdminUserSecurityService();
    }

    @Bean("myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new AdminUserAuthenticationProvider(adminUserSecurityService());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(adminUserSecurityService())
//        .passwordEncoder(new BCryptPasswordEncoder());
    	auth.authenticationProvider(authenticationProvider());
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/resources/**","/captcha.form","/checkCaptcha").permitAll()  //csrf会拦截
				.and()
			.exceptionHandling()
				.accessDeniedPage("/403")
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/loginDeal")
				.failureUrl("/login?error=true")
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/xmulogin/logout")
				.permitAll()
				.and()
			.csrf()
                .requireCsrfProtectionMatcher(new RequestMatcher() {//防止csrf拦截
                    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
                    private RegexRequestMatcher unprotectedMatcher = new RegexRequestMatcher("/personalCenter", null);
                    private RegexRequestMatcher unprotectedMatcher1 = new RegexRequestMatcher("/guide", null);
                    @Override
                    public boolean matches(HttpServletRequest request) {
                        if(allowedMethods.matcher(request.getMethod()).matches()){
                            return false;
                        }
                        if(unprotectedMatcher.matches(request)||unprotectedMatcher1.matches(request)){
                            return false;
                        }
                        else {
                            return true;
                        }
                    }
                })
			    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
			.rememberMe()
				.tokenValiditySeconds(5400)   //token有效期为1.5h
				.rememberMeCookieName("workspace");

//		 http.csrf().disable();
	}
}