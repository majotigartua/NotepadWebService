package model.dao;

import java.util.List;
import model.mybatis.MyBatis;
import model.pojo.Note;
import model.pojo.Response;
import org.apache.ibatis.session.SqlSession;
import util.Constants;

public class NoteDAO {

    public static Response delete(int idNote) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            sqlSession.update("Note.delete", idNote);
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

    public static Response getNoteByTitle(Note note) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            note = sqlSession.selectOne("Note.getByTitle", note);
            response.setError(false);
            response.setMessage(Constants.CORRECT_OPERATION_MESSAGE);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            note = null;
            response.setError(true);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        response.setNote(note);
        return response;
    }

    public static Response getNotesByNotebook(int idNotebook) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            List<Note> notes = sqlSession.selectList("Note.getByNotebook", idNotebook);
            response.setError(false);
            response.setMessage(Constants.CORRECT_OPERATION_MESSAGE);
            response.setNotes(notes);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

    public static Response getNotesByUser(Note note) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            List<Note> notes = sqlSession.selectList("Note.getByUser", note);
            response.setError(false);
            response.setMessage(Constants.CORRECT_OPERATION_MESSAGE);
            response.setNotes(notes);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

    public static Response log(Note note) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            sqlSession.insert("Note.log", note);
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

    public static Response update(Note note) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            sqlSession.update("Note.update", note);
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