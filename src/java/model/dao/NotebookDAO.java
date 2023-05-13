package model.dao;

import java.util.List;
import model.mybatis.MyBatis;
import model.pojo.Notebook;
import model.pojo.Response;
import org.apache.ibatis.session.SqlSession;
import util.Constants;

public class NotebookDAO {

    public static Response delete(int idNotebook) {
        Response response = new Response();
        SqlSession sqlSession = MyBatis.getSqlSession();
        try {
            sqlSession.delete("Notebook.delete", idNotebook);
            sqlSession.commit();
            response.setError(false);
            response.setMessage(Constants.DELETED_INFORMATION_MESSAGE);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

    public static Response getByUser(int idUser) {
        Response response = new Response();
        SqlSession sqlSession = MyBatis.getSqlSession();
        try {
            List<Notebook> notebooks = sqlSession.selectList("Notebook.getByUser", idUser);
            response.setError(false);
            response.setMessage(Constants.CORRECT_OPERATION_MESSAGE);
            response.setNotebooks(notebooks);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

    public static Response getByName(Notebook notebook) {
        Response response = new Response();
        SqlSession sqlSession = MyBatis.getSqlSession();
        try {
            List<Notebook> notebooks = sqlSession.selectList("Notebook.getByName", notebook);
            response.setError(false);
            response.setMessage(Constants.CORRECT_OPERATION_MESSAGE);
            response.setNotebooks(notebooks);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

    public static Response log(Notebook notebook) {
        Response response = new Response();
        SqlSession sqlSession = MyBatis.getSqlSession();
        try {
            sqlSession.insert("Notebook.log", notebook);
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

    public static Response update(Notebook notebook) {
        Response response = new Response();
        SqlSession sqlSession = MyBatis.getSqlSession();
        try {
            sqlSession.update("Notebook.update", notebook);
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