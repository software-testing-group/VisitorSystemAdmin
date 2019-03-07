package beike.visitorsystem.aspect;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import beike.visitorsystem.aspect.ActionRecordAspect;
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(basePackages="beike.visitorsystem.aspect")
public class AspectConfig {
	
	@Bean
	public ActionRecordAspect record()
	{
		return new ActionRecordAspect();
	}
}  