package model.dao;

import java.util.List;
import model.mybatis.MyBatis;
import model.pojo.Priority;
import model.pojo.Response;
import org.apache.ibatis.session.SqlSession;
import util.Constants;

public class PriorityDAO {
    
    public static Response getPriorities() {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            List<Priority> priorities = sqlSession.selectList("Priority.get");
            sqlSession.commit();
            response.setError(false);
            response.setMessage(Constants.CORRECT_OPERATION_MESSAGE);
            response.setPriorities(priorities);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }
    
}