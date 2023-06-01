package model.dao;

import java.util.List;
import model.mybatis.MyBatis;
import model.pojo.Notebook;
import model.pojo.Response;
import org.apache.ibatis.session.SqlSession;

public class NotebookDAO {

    public static Response delete(int idNotebook) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            sqlSession.delete("Notebook.delete", idNotebook);
            sqlSession.commit();
            response.setError(false);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
        }
        return response;
    }

    public static Response getNotebookByName(Notebook notebook) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            notebook = sqlSession.selectOne("Notebook.getByName", notebook);
            response.setError(false);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            notebook = null;
            response.setError(true);
        }
        response.setNotebook(notebook);
        return response;
    }

    public static Response getNotebooksByUser(int idUser) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            List<Notebook> notebooks = sqlSession.selectList("Notebook.getByUser", idUser);
            response.setError(false);
            response.setNotebooks(notebooks);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
        }
        return response;
    }

    public static Response log(Notebook notebook) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            sqlSession.insert("Notebook.log", notebook);
            sqlSession.commit();
            response.setError(false);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
        }
        return response;
    }

    public static Response update(Notebook notebook) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            sqlSession.update("Notebook.update", notebook);
            sqlSession.commit();
            response.setError(false);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
        }
        return response;
    }

}