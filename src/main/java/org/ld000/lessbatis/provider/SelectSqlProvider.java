package org.ld000.lessbatis.provider;

import org.ld000.lessbatis.annotation.CamelHumpToUnderline;
import org.ld000.lessbatis.utils.*;

import javax.persistence.Transient;
import java.lang.reflect.Field;

/**
 * @author lidong 17-3-7.
 */
public class SelectSqlProvider<T> {

    /**
     * 生成单条 select sql 语句, 只查询当前类包含的字段，不查询父类字段
     *
     * @param condition query condition
     * @return sql
     */
    public String querySingleTable(T condition) {
        return genQuerySql(condition, condition.getClass().isAnnotationPresent(CamelHumpToUnderline.class));
    }

    /**
     * 生成单表统计指定条件数据数量 sql
     *
     * @param condition query condition
     * @return sql
     */
    public String countSingleTable(T condition) {
        return genCountSql(condition, condition.getClass().isAnnotationPresent(CamelHumpToUnderline.class));
    }

    /**
     * 生成单表查询指定条件数据是否存在 sql
     *
     * @param condition query condition
     * @return sql
     */
    public String checkExist(T condition) {
        return genCheckExistSql(condition, condition.getClass().isAnnotationPresent(CamelHumpToUnderline.class));
    }

    /* ************************************************
     * 内部方法
     * ************************************************ */

    /**
     * 生成单表 select sql 语句
     *
     * @param condition 查询条件
     * @param camelHumpToUnderline 是否驼峰命名转下划线命名
     * @return sql
     */
    private String genQuerySql(final T condition, final boolean camelHumpToUnderline) {
        return genQuerySingleTableSql(condition, false, false, camelHumpToUnderline);
    }

    /**
     * 生成单表统计指定条件数据数量 sql 语句
     *
     * @param condition 查询条件
     * @param camelHumpToUnderline 是否驼峰命名转下划线命名
     * @return sql
     */
    private String genCountSql(final T condition, final boolean camelHumpToUnderline) {
        return genQuerySingleTableSql(condition, true, false, camelHumpToUnderline);
    }

    /**
     * 生成单表查询指定条件数据是否存在 sql 语句
     *
     * @param condition 查询条件
     * @param camelHumpToUnderline 是否驼峰命名转下划线命名
     * @return sql
     */
    private String genCheckExistSql(final T condition, final boolean camelHumpToUnderline) {
        return genQuerySingleTableSql(condition, false, true, camelHumpToUnderline);
    }

    /**
     * 生成通用 select sql, 只查询当前类包含的字段，不查询父类字段
     *
     * @param condition 查询条件
     * @param count 是否 count sql
     * @param checkExist 是否 checkExist sql
     * @param camelHumpToUnderline 是否驼峰命名转下划线命名
     * @return sql
     */
    private String genQuerySingleTableSql(final T condition,
                                          final boolean count, final boolean checkExist, final boolean camelHumpToUnderline) {
        @SuppressWarnings("unchecked")
        final Class<T> clazz = (Class<T>) ClassUtils.getOriginalClass(condition.getClass());

        StringBuilder sql = new StringBuilder("SELECT ");

        // 根据不同查询类型添加不同的 SELECT 语句
        if (count) {
            sql.append("COUNT(*)");
        } else if (checkExist) {
            sql.append("1");
        } else {
            int i = 0;
            for (Field property : ReflectionUtils.getDeclaredFields(clazz)) {
                if (property.isAnnotationPresent(Transient.class)) {
                    continue;
                }

                if (i++ != 0) {
                    sql.append(", ");
                }

                final String propertyName = property.getName();

                sql.append("`").append(camelHumpToUnderline ? StringUtils.toUnderScoreCase(propertyName) : propertyName)
                        .append("` AS \"").append(propertyName).append("\"");
            }
        }

        sql.append(" FROM ").append(ModelUtils.getTableName(clazz));

        // 添加 WHERE 条件
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

            sql.append("`").append(camelHumpToUnderline ? StringUtils.toUnderScoreCase(propertyName) :
                    propertyName).append("` = #{").append(propertyName).append("}");
        }

        if (checkExist)
            return sql.toString() + " LIMIT 1";

        return sql.toString();
    }

}
