package beike.visitorsystem.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"beike.visitorsystem"})
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware
{
	@SuppressWarnings("unused")
	private ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext context) throws BeansException
	{
		this.context=context;
	}
	
//	@Bean
//	public ViewResolver viewResolver() 
//	{
//		InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
//		viewResolver.setPrefix("/WEB-INF/");
//		viewResolver.setSuffix(".jsp");
//		viewResolver.setExposeContextBeansAsAttributes(true);
//	
//		return viewResolver;
//	}
	
	@Bean  
	public ViewResolver contentNegotiatingViewResolver(  
	                    ContentNegotiationManager manager) {  

	            List< ViewResolver > resolvers = new ArrayList< ViewResolver >();  

	            InternalResourceViewResolver r1 = new InternalResourceViewResolver();  
	            r1.setPrefix("/WEB-INF/");  
	            r1.setSuffix(".jsp");  
	            r1.setViewClass(JstlView.class);  
	            resolvers.add(r1);  

	            JsonViewResolver r2 = new JsonViewResolver();  
	            resolvers.add(r2);  

	            ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();  
	            resolver.setViewResolvers(resolvers);  
	            resolver.setContentNegotiationManager(manager);  
	     return resolver;  

	    } 
	
    public class JsonViewResolver implements ViewResolver {  
	      public View resolveViewName(String viewName, Locale locale) throws Exception {  
	              MappingJackson2JsonView view = new MappingJackson2JsonView();  
	              view.setPrettyPrint(true);  
	              return view;  
	        }  
	}  
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) 
	{
		configurer.enable();
	}
	
	@Bean
    public MultipartResolver multipartResolver() {  
		return new StandardServletMultipartResolver();  
  
        
    }  
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // map resource to public folder
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

    }
}