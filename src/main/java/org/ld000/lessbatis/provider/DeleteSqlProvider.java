package org.ld000.lessbatis.provider;

import org.ld000.lessbatis.annotation.CamelHumpToUnderline;
import org.ld000.lessbatis.utils.*;

import javax.persistence.Transient;
import java.lang.reflect.Field;

/**
 * @author lidong9144@163.com 17-3-7.
 */
public class DeleteSqlProvider<T> {

    public String delete(T condition) {
        return genDeleteSql(condition);
    }

    /* ************************************************
     * private method
     * ************************************************ */

    private String genDeleteSql(T condition) {
        @SuppressWarnings("unchecked")
        final Class<T> clazz = (Class<T>) ClassUtils.getOriginalClass(condition.getClass());
        Boolean camelHumpToUnderline = clazz.isAnnotationPresent(CamelHumpToUnderline.class);

        StringBuilder sql = new StringBuilder("DELETE FROM ").append(ModelUtils.getTableName(clazz));

        if (ReflectionUtils.getDeclaredFields(clazz).length > 0) {
            sql.append(" WHERE ");
        }
        int i = 0;
        for (Field property : ReflectionUtils.getDeclaredFields(clazz)) {
            if (property.isAnnotationPresent(Transient.class)) {
                continue;
            }

            final String propertyName = property.getName();
            final Object inValue = ReflectionUtils.readProperty(condition, propertyName);

            // 只添加非空的字段
            if (ObjectUtils.isEmpty(inValue)) {
                continue;
            }

            if (i++ != 0) {
                sql.append(" AND ");
            }

            sql.append("`").append(camelHumpToUnderline ? StringUtils.toUnderScoreCase(propertyName) : propertyName)
                    .append("` = ")
                    .append("#{").append(propertyName).append("}");
        }

        return sql.toString();
    }

}
