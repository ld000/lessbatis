package org.ld000.lessbatis.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 字段在 where 条件中的顺序
 *
 * @author lidong9144@163.com 17-3-8.
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface WhereOrder {

    int value();

}
