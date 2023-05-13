package model.dao;

import java.util.List;
import model.mybatis.MyBatis;
import model.pojo.Note;
import model.pojo.Response;
import org.apache.ibatis.session.SqlSession;
import util.Constants;

public class NoteDAO {

    public static Response getByNotebook(int idNotebook) {
        Response response = new Response();
        SqlSession sqlSession = MyBatis.getSqlSession();
        try {
            List<Note> notes = sqlSession.selectList("Note.getByNotebook", idNotebook);
            sqlSession.commit();
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

}