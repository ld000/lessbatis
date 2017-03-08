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
 * @author lidong9144@163.com 17-3-8.
 */
public interface LessBatisMapper<T> {

    /* ************************************************
     * Select method
     * ************************************************ */

    @SelectProvider(type = SelectSqlProvider.class, method = "querySingleTable")
    T querySingleTable(@Param("condition") T condition, @Param("fields") String... fields);

    @SelectProvider(type = SelectSqlProvider.class, method = "countSingleTable")
    Integer queryCount(T condition);

    @SelectProvider(type = SelectSqlProvider.class, method = "checkExist")
    Integer checkExist(T condition);

    /* ************************************************
     * Insert method
     * ************************************************ */

    @InsertProvider(type = InsertSqlProvider.class, method = "insert")
    Integer insert(T obj);

    @InsertProvider(type = InsertSqlProvider.class, method = "batchInsert")
    Integer batchInsert(@Param("list") List<T> list);

    /* ************************************************
     * Update method
     * ************************************************ */

    /* ************************************************
     * Delete method
     * ************************************************ */

    @DeleteProvider(type = DeleteSqlProvider.class, method = "delete")
    Integer delete(T condition);

}
