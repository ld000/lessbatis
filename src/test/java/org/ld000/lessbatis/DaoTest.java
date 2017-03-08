package org.ld000.lessbatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author lidong9144@163.com 17-3-8.
 */
public class DaoTest {

    @Test
    public void test() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSessionFactory.getConfiguration().addMapper(TestMapper.class);

        SqlSession session = sqlSessionFactory.openSession();
        try {
            TestMapper mapper = session.getMapper(TestMapper.class);

            Obj obj = new Obj();
            obj.setId(123L);
            obj.setName("df");

            int a = mapper.selectCount(obj);
            System.out.println(a);
        } finally {
            session.close();
        }
    }

}
