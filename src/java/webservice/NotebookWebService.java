package webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.dao.NoteDAO;
import model.dao.NotebookDAO;
import model.pojo.Notebook;
import model.pojo.Response;
import util.Constants;

@Path("auth/notebooks")
public class NotebookWebService {

    @Context
    private UriInfo context;

    public NotebookWebService() {
    }

    @DELETE
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("idNotebook") int idNotebook) {
        Response response = new Response();
        boolean isEmpty = NoteDAO.getNotesByNotebook(idNotebook).getNotes().isEmpty();
        if (isEmpty) {
            response = NotebookDAO.delete(idNotebook);
        } else {
            response.setError(true);
            response.setMessage(Constants.INVALID_DATA_MESSAGE);
        }
        return response;
    }

    @GET
    @Path("get/{idUser}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotebooksByUser(@PathParam("idUser") int idUser) {
        Response response = NotebookDAO.getNotebooksByUser(idUser);
        return response;
    }

    @POST
    @Path("log")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response log(Notebook notebook) {
        Response response = new Response();
        boolean isAvailable = NotebookDAO.getNotebookByName(notebook).getNotebook() == null;
        if (isAvailable) {
            response = NotebookDAO.log(notebook);
        } else {
            response.setError(true);
            response.setMessage(Constants.DUPLICATED_INFORMATION_MESSAGE);
        }
        return response;
    }

    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Notebook notebook) {
        Response response = new Response();
        boolean isAvailable = NotebookDAO.getNotebookByName(notebook).getNotebook() == null;
        if (isAvailable) {
            response = NotebookDAO.update(notebook);
        } else {
            response.setError(true);
            response.setMessage(Constants.DUPLICATED_INFORMATION_MESSAGE);
        }
        return response;
    }

}