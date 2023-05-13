package model.dao;

import model.mybatis.MyBatis;
import model.pojo.Response;
import model.pojo.User;
import org.apache.ibatis.session.SqlSession;
import util.Constants;

public class UserDAO {

    public static Response activate(User user) {
        Response response = new Response();
        SqlSession sqlSession = MyBatis.getSqlSession();
        try {
            sqlSession.update("User.activate", user);
            sqlSession.commit();
            response.setError(false);
            response.setMessage(Constants.MODIFIED_INFORMATION_MESSAGE);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

    public static Response getByCellphoneNumber(String cellphoneNumber) {
        Response response = new Response();
        SqlSession sqlSession = MyBatis.getSqlSession();
        try {
            User user = sqlSession.selectOne("User.getByCellphoneNumber", cellphoneNumber);
            response.setError(false);
            response.setMessage(Constants.CORRECT_OPERATION_MESSAGE);
            response.setUser(user);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

    public static Response login(User user) {
        Response response = new Response();
        SqlSession sqlSession = MyBatis.getSqlSession();
        try {
            user = sqlSession.selectOne("User.login", user);
            response.setError(false);
            response.setMessage(Constants.CORRECT_OPERATION_MESSAGE);
            response.setUser(user);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

    public static Response signUp(User user) {
        Response response = new Response();
        SqlSession sqlSession = MyBatis.getSqlSession();
        try {
            sqlSession.insert("User.signUp", user);
            sqlSession.commit();
            response.setError(false);
            response.setMessage(Constants.REGISTERED_INFORMATION_MESSAGE);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

    public static Response update(User user) {
        Response response = new Response();
        SqlSession sqlSession = MyBatis.getSqlSession();
        try {
            sqlSession.update("User.activate", user);
            sqlSession.commit();
            response.setError(false);
            response.setMessage(Constants.MODIFIED_INFORMATION_MESSAGE);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

}