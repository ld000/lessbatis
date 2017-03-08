package org.ld000.lessbatis.provider;

import org.ld000.lessbatis.annotation.CamelHumpToUnderline;
import org.ld000.lessbatis.utils.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.reflect.Field;

/**
 * @author lidong 17-3-7.
 */
public class UpdateSqlProvider<T> {

    /* ************************************************
     * private method
     * ************************************************ */

    private String gemUpdateSql(T obj, T condition) {
        @SuppressWarnings("unchecked")
        final Class<T> clazz = (Class<T>) ClassUtils.getOriginalClass(obj.getClass());
        Boolean camelHumpToUnderline = clazz.isAnnotationPresent(CamelHumpToUnderline.class);

        StringBuilder sql = new StringBuilder("UPDATE ")
                .append(ModelUtils.getTableName(clazz))
                .append(" SET ");

        int i = 0;
        for (Field property : ReflectionUtils.getDeclaredFields(clazz)) {
            if (property.isAnnotationPresent(Transient.class)
                    || (property.isAnnotationPresent(GeneratedValue.class) && property.isAnnotationPresent(Id.class))) {
                continue;
            }

            final String propertyName = property.getName();
            final Object inValue = ReflectionUtils.readProperty(condition, propertyName);

            // 只添加非空的字段
            if (ObjectUtils.isEmpty(inValue)) {
                continue;
            }

            if (i++ != 0) {
                sql.append(", ");
            }

            sql.append("`").append(camelHumpToUnderline ? StringUtils.toUnderScoreCase(propertyName) : propertyName)
                    .append("` = ")
                    .append("#{obj.").append(propertyName).append("}");
        }

        return sql.toString();
    }

}
