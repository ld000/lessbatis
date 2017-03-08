package org.ld000.lessbatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author lidong9144@163.com 17-3-8.
 */
public class DaoTest {

    private static SqlSessionFactory sqlSessionFactory;
    private SqlSession session;
    private TestMapper mapper;

    @BeforeClass
    public static void beforeClass() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSessionFactory.getConfiguration().addMapper(TestMapper.class);

    }

    @Before
    public void before() {
        session = sqlSessionFactory.openSession();
        mapper = session.getMapper(TestMapper.class);
    }

    @After
    public void after() {
        session.close();
    }

    @Test
    public void queryCountTest() throws IOException {
        Obj obj = new Obj();
        obj.setId(1L);
//            obj.setName("df");

        Integer a = mapper.queryCount(obj);
        System.out.println(a);
    }

    @Test
    public void checkExistTest() {
        Obj obj = new Obj();
        obj.setId(2L);
//            obj.setName("df");

        Integer a = mapper.checkExist(obj);
        System.out.println(a);
    }

    @Test
    public void querySingleTableTest() {
        Obj obj = new Obj();
        obj.setId(1L);
//            obj.setName("df");

        Obj a = mapper.querySingleTable(obj);
        System.out.println(a);
    }


}
