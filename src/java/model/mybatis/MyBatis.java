package model.mybatis;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatis {
    
    public static String RESOURCE = "model/mybatis/mybatis-config.xml";
    public static String ENVIROMENT = "development";
    
    public static SqlSession getSqlSession() {
        SqlSession sqlSession = null;
        try {
            Reader reader = Resources.getResourceAsReader(RESOURCE);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, ENVIROMENT);
            sqlSession = sqlSessionFactory.openSession();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
        return sqlSession;
    }
    
}