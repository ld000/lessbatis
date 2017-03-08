package org.ld000.lessbatis.utils;

import javax.persistence.Table;

/**
 * @author lidong9144@163.com 17-3-7.
 */
public class ModelUtils {

    /**
     * 从 Model　的类获取对应的数据库的表名 </br>
     * 如果有 {@link Table} 注解，并且注明了 {@code name} 的值，表名就为该值 </br>
     * 如果没有，就取类名
     *
     * @param modelClazz　Model 的类
     * @return 对应数据库的表名
     */
    public static final String getTableName(final Class<?> modelClazz) {
        final Class<?> clazz = ClassUtils.getOriginalClass(modelClazz);

        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);

            if (!StringUtils.isBlank(table.name()))
                return table.name();
        }

        return clazz.getSimpleName();
    }

}
