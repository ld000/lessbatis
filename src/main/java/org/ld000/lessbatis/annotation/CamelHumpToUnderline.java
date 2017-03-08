package org.ld000.lessbatis.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author lidong9144@163.com 17-3-8.
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface CamelHumpToUnderline {

    String value() default "";

}
