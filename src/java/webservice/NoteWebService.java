package webservice;

import java.sql.Date;
import java.time.LocalDate;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.dao.NoteDAO;
import model.pojo.Note;
import model.pojo.Response;
import util.Constants;

@Path("auth/notes")
public class NoteWebService {

    @Context
    private UriInfo context;

    public NoteWebService() {
    }

    @DELETE
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(Note note) {
        int idNote = note.getIdNote();
        Response response = NoteDAO.delete(idNote);
        if (!response.isError()) {
            response.setCode(HttpServletResponse.SC_OK);
            response.setMessage(Constants.DELETED_INFORMATION_MESSAGE);
        } else {
            response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

    @GET
    @Path("get/{idUser}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotesByUser(@PathParam("idUser") int idUser) {
        Note note = new Note(idUser);
        Response response = NoteDAO.getNotesByUser(note);
        if (!response.isError()) {
            response.setCode(HttpServletResponse.SC_OK);
            response.setMessage(Constants.CORRECT_OPERATION_MESSAGE);
        } else {
            response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

    @GET
    @Path("get/{idUser}/{idNotebook}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotesByUser(@PathParam("idUser") int idUser,
            @PathParam("idNotebook") int idNotebook) {
        Note note = new Note(idUser, idNotebook);
        Response response = NoteDAO.getNotesByUser(note);
        if (!response.isError()) {
            response.setCode(HttpServletResponse.SC_OK);
            response.setMessage(Constants.CORRECT_OPERATION_MESSAGE);
        } else {
            response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

    @GET
    @Path("get/{idUser}/{idNotebook}/{idPriority}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotesByUser(@PathParam("idUser") int idUser,
            @PathParam("idNotebook") int idNotebook,
            @PathParam("idPriority") int idPriority) {
        Note note = new Note(idNotebook, idUser, idPriority);
        Response response = NoteDAO.getNotesByUser(note);
        if (!response.isError()) {
            response.setCode(HttpServletResponse.SC_OK);
            response.setMessage(Constants.CORRECT_OPERATION_MESSAGE);
        } else {
            response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

    @POST
    @Path("log")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response log(Note note) {
        Response response = new Response();
        boolean isAvailable = NoteDAO.getNoteByTitle(note).getNote() == null;
        if (isAvailable) {
            Date creationDate = Date.valueOf(LocalDate.now());
            note.setCreationDate(creationDate);
            response = NoteDAO.log(note);
            if (!response.isError()) {
                response.setCode(HttpServletResponse.SC_OK);
                response.setMessage(Constants.REGISTERED_INFORMATION_MESSAGE);
            } else {
                response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
            }
        } else {
            note = NoteDAO.getNoteByTitle(note).getNote();
            boolean isDeleted = note.isDeleted();
            if (isDeleted) {
                note.setDeleted(false);
                response = NoteDAO.update(note);
                if (!response.isError()) {
                    response.setCode(HttpServletResponse.SC_OK);
                    response.setMessage(Constants.MODIFIED_INFORMATION_MESSAGE);
                } else {
                    response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
                }
            } else {
                response.setError(true);
                response.setCode(HttpServletResponse.SC_BAD_REQUEST);
                response.setMessage(Constants.DUPLICATED_INFORMATION_MESSAGE);
            }
        }
        return response;
    }

    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Note note) {
        Response response = new Response();
        boolean isAvailable = NoteDAO.getNoteByTitle(note).getNote() == null;
        if (isAvailable) {
            response = NoteDAO.update(note);
            if (!response.isError()) {
                response.setCode(HttpServletResponse.SC_OK);
                response.setMessage(Constants.MODIFIED_INFORMATION_MESSAGE);
            } else {
                response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
            }
        } else {
            boolean isDeleted = NoteDAO.getNoteByTitle(note).getNote().isDeleted();
            if (isDeleted) {
                note.setDeleted(false);
                response = NoteDAO.update(note);
                if (!response.isError()) {
                    response.setCode(HttpServletResponse.SC_OK);
                    response.setMessage(Constants.MODIFIED_INFORMATION_MESSAGE);
                } else {
                    response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
                }
            } else {
                response.setError(true);
                response.setCode(HttpServletResponse.SC_BAD_REQUEST);
                response.setMessage(Constants.DUPLICATED_INFORMATION_MESSAGE);
            }
        }
        return response;
    }

}