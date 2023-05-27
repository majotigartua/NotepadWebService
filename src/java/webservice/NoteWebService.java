package webservice;

import java.sql.Date;
import java.time.LocalDate;
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
        return response;
    }

    @GET
    @Path("get/{idUser}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotesByUser(@PathParam("idUser") int idUser) {
        Note note = new Note(idUser);
        Response response = NoteDAO.getNotesByUser(note);
        return response;
    }

    @GET
    @Path("get/{idUser}/{idNotebook}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotesByUser(@PathParam("idUser") int idUser,
            @PathParam("idNotebook") int idNotebook) {
        Note note = new Note(idUser, idNotebook);
        Response response = NoteDAO.getNotesByUser(note);
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
        } else {
            note = NoteDAO.getNoteByTitle(note).getNote();
            boolean isDeleted = note.isDeleted();
            if (isDeleted) {
                note.setDeleted(false);
                response = NoteDAO.update(note);
            } else {
                response.setError(true);
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
        } else {
            boolean isDeleted = NoteDAO.getNoteByTitle(note).getNote().isDeleted();
            if (isDeleted) {
                note.setDeleted(false);
                response = NoteDAO.update(note);
            } else {
                response.setError(true);
                response.setMessage(Constants.DUPLICATED_INFORMATION_MESSAGE);
            }
        }
        return response;
    }

}