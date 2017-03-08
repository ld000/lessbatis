package org.ld000.lessbatis;

import org.junit.Test;
import org.ld000.lessbatis.provider.SelectSqlProvider;

/**
 * @author lidong9144@163.com 17-3-8.
 */
public class SelectSqlProviderTest {

    private SelectSqlProvider<Obj> selectSqlProvider = new SelectSqlProvider<>();

    @Test
    public void test() {
        Obj obj = new Obj();
        obj.setId(123L);
        String sql = selectSqlProvider.checkExist(obj);

        System.out.println(sql);
    }

    @Test
    public void test1() {
        Obj obj = new Obj();
        obj.setId(123L);
        obj.setName("df");
        String sql = selectSqlProvider.countSingleTable(obj);

        System.out.println(sql);
    }

    @Test
    public void test2() {
        Obj obj = new Obj();
        obj.setId(123L);
        obj.setName("df");
        obj.setUserName("dfd");

        String sql = selectSqlProvider.querySingleTableWithCamelhumpToUnderline(obj);

        System.out.println(sql);
    }

}
