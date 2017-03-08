package org.ld000.lessbatis.provider;

import org.ld000.lessbatis.annotation.CamelHumpToUnderline;
import org.ld000.lessbatis.utils.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author lidong 17-3-7.
 */
public class InsertSqlProvider<T> {

    /**
     * 生成单条 insert sql 语句
     *
     * @param obj
     * @return
     */
    public String insert(T obj) {
        return genInsertSql(obj, obj.getClass().isAnnotationPresent(CamelHumpToUnderline.class));
    }

    /**
     * 生成批量 insert sql 语句
     *
     * @param para
     * @return
     */
    public String insertMultiRow(Map<String, Object> para) {
        return genBatchInsertSql(para);
    }

    /* ************************************************
     * private method
     * ************************************************ */

    /**
     * 生成单条 insert sql 语句
     *
     * @param obj
     * @param camelhumpToUnderline 是否驼峰命名转下划线命名
     * @return
     */
    private String genInsertSql(final T obj, final boolean camelhumpToUnderline) {
        @SuppressWarnings("unchecked")
        final Class<T> clazz = (Class<T>) ClassUtils.getOriginalClass(obj.getClass());

        StringBuilder sql = new StringBuilder("INSERT INTO ").append(ModelUtils.getTableName(clazz))
                .append(" (%) VALUES (");

        StringBuilder fieldList = new StringBuilder();

        int i = 0;
        for (Field property : ReflectionUtils.getDeclaredFields(clazz)) {
            if (property.isAnnotationPresent(Transient.class) || property.isAnnotationPresent(GeneratedValue.class))
                continue;

            final String propertyName = property.getName();
            final Object inValue = ReflectionUtils.readProperty(obj, propertyName);

            if (ObjectUtils.isEmpty(inValue))
                continue;   // 只添加非空字段

            String fieldName = camelhumpToUnderline ? StringUtils.toUnderScoreCase(propertyName) : propertyName;

            if (i++ > 0) {
                sql.append(", ");
                fieldList.append(", ");
            }

            fieldList.append(fieldName);
            sql.append("#{").append(propertyName).append("}");
        }
        sql.append(")");

        return sql.toString().replace("%", fieldList);
    }

    /**
     * 生成批量 insert 语句
     *
     * @param para
     * @return
     */
    @SuppressWarnings("unchecked")
    private String genBatchInsertSql(final Map<String, Object> para) {
        if (para.get("list") == null)
            return "SELECT 1 FROM dual";

        final List<T> list = (List<T>) para.get("list");

        if (list.size() == 0) {
            return "SELECT 1 FROM dual";
        }

        final Object firstObj = list.get(0);
        final Class<T> clazz = ClassUtils.getOriginalClass((Class<T>) firstObj.getClass());
        Boolean camelHumpToUnderline = firstObj.getClass().isAnnotationPresent(CamelHumpToUnderline.class);

        StringBuilder sql = new StringBuilder("INSERT INTO ").append(ModelUtils.getTableName(clazz)).append(" (");
        StringBuilder valueSql = new StringBuilder();

        int i = 0;
        for (Field property : clazz.getDeclaredFields()) {
            if (property.isAnnotationPresent(Transient.class) || property.isAnnotationPresent(GeneratedValue.class))
                continue;

            final String propertyName = property.getName();
            final Object inValue = ReflectionUtils.readProperty(firstObj, propertyName);

            if (ObjectUtils.isEmpty(inValue))
                continue;    // 只添加非空字段

            if (i++ > 0) {
                sql.append(", ");
                valueSql.append(", ");
            }

            sql.append("`").append(camelHumpToUnderline ? StringUtils.toUnderScoreCase(propertyName) :
                    propertyName).append("`");

            valueSql.append("#{list[?].").append(propertyName).append("}");
        }

        sql.append(") VALUES ");

        for (int j = 0; j < list.size(); j++) {
            if (j > 0)
                sql.append(",");

            sql.append("(");
            String vSql = StringUtils.replaceChars(valueSql.toString(), "?", String.valueOf(j));
            sql.append(vSql);
            sql.append(")");
        }

        return sql.toString();
    }

}
