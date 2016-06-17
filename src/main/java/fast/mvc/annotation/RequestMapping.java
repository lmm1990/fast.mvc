package fast.mvc.annotation;

import fast.mvc.model.enumModel.RequestMethod;

import java.lang.annotation.*;

/**
 * Created by lmm on 2016/6/16.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String value() default "";

    RequestMethod[] method() default {};
}