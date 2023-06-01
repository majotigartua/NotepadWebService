package model.dao;

import java.util.List;
import model.mybatis.MyBatis;
import model.pojo.Note;
import model.pojo.Response;
import org.apache.ibatis.session.SqlSession;

public class NoteDAO {

    public static Response delete(int idNote) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            sqlSession.update("Note.delete", idNote);
            sqlSession.commit();
            response.setError(false);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
        }
        return response;
    }

    public static Response getNoteByTitle(Note note) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            note = sqlSession.selectOne("Note.getByTitle", note);
            response.setError(false);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            note = null;
            response.setError(true);
        }
        response.setNote(note);
        return response;
    }

    public static Response getNotesByNotebook(int idNotebook) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            List<Note> notes = sqlSession.selectList("Note.getByNotebook", idNotebook);
            response.setError(false);
            response.setNotes(notes);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
        }
        return response;
    }

    public static Response getNotesByUser(Note note) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            List<Note> notes = sqlSession.selectList("Note.getByUser", note);
            response.setError(false);
            response.setNotes(notes);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
        }
        return response;
    }

    public static Response log(Note note) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            sqlSession.insert("Note.log", note);
            sqlSession.commit();
            response.setError(false);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
        }
        return response;
    }

    public static Response update(Note note) {
        Response response = new Response();
        try (SqlSession sqlSession = MyBatis.getSqlSession()) {
            sqlSession.update("Note.update", note);
            sqlSession.commit();
            response.setError(false);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            response.setError(true);
        }
        return response;
    }

}