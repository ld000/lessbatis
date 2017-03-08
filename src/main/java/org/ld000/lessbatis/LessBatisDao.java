package org.ld000.lessbatis;

import org.apache.ibatis.annotations.SelectProvider;
import org.ld000.lessbatis.provider.SelectSqlProvider;

/**
 * @author lidong9144@163.com 17-3-8.
 */
public interface LessBatisDao<T> {

    T select();

    @SelectProvider(type = SelectSqlProvider.class, method = "countSingleTableWithCamelhumpToUnderline")
    int selectCount(T condition);

}
