package org.ld000.lessbatis;

import org.apache.ibatis.annotations.SelectProvider;
import org.ld000.lessbatis.provider.SelectSqlProvider;

/**
 * @author lidong9144@163.com 17-3-8.
 */
public interface LessBatisMapper<T> {

    /* ************************************************
     * Select method
     * ************************************************ */

    @SelectProvider(type = SelectSqlProvider.class, method = "querySingleTable")
    T querySingleTable(T condition);

    @SelectProvider(type = SelectSqlProvider.class, method = "countSingleTable")
    Integer queryCount(T condition);

    @SelectProvider(type = SelectSqlProvider.class, method = "checkExist")
    Integer checkExist(T condition);

    /* ************************************************
     * Insert method
     * ************************************************ */

    /* ************************************************
     * Update method
     * ************************************************ */

    /* ************************************************
     * Delete method
     * ************************************************ */

}
