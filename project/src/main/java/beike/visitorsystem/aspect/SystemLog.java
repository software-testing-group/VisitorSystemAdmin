package beike.visitorsystem.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})   
@Documented 
public @interface SystemLog {
	public String module();
	public String action();
	public String details() default "";
}
