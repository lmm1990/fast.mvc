package fast.mvc.annotation;

import java.lang.annotation.*;

/**
 * Created by lmm on 2016/6/16.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}