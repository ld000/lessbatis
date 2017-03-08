package org.ld000.lessbatis;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.ld000.lessbatis.provider.DeleteSqlProvider;
import org.ld000.lessbatis.provider.InsertSqlProvider;
import org.ld000.lessbatis.provider.SelectSqlProvider;

import java.util.List;

/**
 * 单表操作，自动生成 sql 语句
 *
 * @author lidong9144@163.com 17-3-8.
 */
public interface LessBatisMapper<T> {

    /* ************************************************
     * Select method
     * ************************************************ */

    /**
     * 单表查询, 单个对象
     *
     * @param condition 查询条件
     * @param fields 要查询的字段，不传默认查全表字段
     * @return {@link T} 结果
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "querySingleTable")
    T queryOne(@Param("condition") T condition, @Param("fields") String... fields);

    /**
     * 单表查询, 列表
     *
     * @param condition 查询条件
     * @param fields 要查询的字段，不传默认查全表字段
     * @return {@link T} 结果
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "querySingleTable")
    List<T> queryList(@Param("condition") T condition, @Param("fields") String... fields);

    /**
     * 按条件查询结果数量
     *
     * @param condition 查询条件
     * @return {@link Integer} 数量
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "countSingleTable")
    Integer queryCount(T condition);

    /**
     * 检查指定条件结果是否存在
     *
     * @param condition 条件
     * @return {@link Integer} 是否存在 1-有 null-无
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "checkExist")
    Integer checkExist(T condition);

    /* ************************************************
     * Insert method
     * ************************************************ */

    /**
     * 插入一条数据
     *
     * @param obj 要插入的对象
     * @return 插入条数
     */
    @InsertProvider(type = InsertSqlProvider.class, method = "insert")
    Integer insert(T obj);

    /**
     * 使用 mysql 语法批量插入
     *
     * @param list 要插入的对象
     * @return 插入条数
     */
    @InsertProvider(type = InsertSqlProvider.class, method = "insertMultiRow")
    Integer insertMultiRow(@Param("list") List<T> list);

    /* ************************************************
     * Update method
     * ************************************************ */

    /* ************************************************
     * Delete method
     * ************************************************ */

    /**
     * 按条件删除数据
     *
     * @param condition 条件
     * @return 删除条数
     */
    @DeleteProvider(type = DeleteSqlProvider.class, method = "delete")
    Integer delete(T condition);

}
