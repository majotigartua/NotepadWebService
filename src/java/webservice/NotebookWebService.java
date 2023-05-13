package webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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

@Path("auth/notebook")
public class NotebookWebService {

    @Context
    private UriInfo context;

    public NotebookWebService() {
    }

    @GET
    @Path("delete")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("idNotebook") int idNotebook) {
        Response response = new Response();
        if (!String.valueOf(idNotebook).isEmpty()) {
            if (NoteDAO.getByNotebook(idNotebook).getNotes().isEmpty()) {
                response = NotebookDAO.delete(idNotebook);
            } else {
                response.setError(true);
                response.setMessage(Constants.INVALID_DATA_MESSAGE);
            }
        } else {
            response.setError(true);
            response.setMessage(Constants.EMPTY_FIELDS_MESSAGE);
        }
        return response;
    }

    @GET
    @Path("get/{idUser}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public void getByUser(@PathParam("idUser") int idUser) {
        Response response = new Response();
        if (!String.valueOf(idUser).isEmpty()) {
            response = NotebookDAO.getByUser(idUser);
        } else {
            response.setError(true);
            response.setMessage(Constants.EMPTY_FIELDS_MESSAGE);
        }
    }

    @POST
    @Path("log")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response log(@FormParam("name") String name,
            @FormParam("hexadecimalColor") String hexadecimalColor,
            @FormParam("idUser") int idUser) {
        Response response = new Response();
        Notebook notebook = new Notebook();
        notebook.setName(name);
        notebook.setHexadecimalColor(hexadecimalColor);
        notebook.setIdUser(idUser);
        if (!checkEmptyFields(notebook)) {
            if (NotebookDAO.getByName(notebook).getNotebooks().isEmpty()) {
                response = NotebookDAO.log(notebook);
            } else {
                response.setError(true);
                response.setMessage(Constants.DUPLICATED_INFORMATION_MESSAGE);
            }
        } else {
            response.setError(true);
            response.setMessage(Constants.EMPTY_FIELDS_MESSAGE);
        }
        return response;
    }

    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public void update(@FormParam("name") String name,
            @FormParam("hexadecimalColor") String hexadecimalColor,
            @FormParam("idNotebook") int idNotebook) {
        Response response = new Response();
        Notebook notebook = new Notebook();
        notebook.setName(name);
        notebook.setHexadecimalColor(hexadecimalColor);
        notebook.setIdNotebook(idNotebook);
        if (!checkEmptyFields(notebook)) {
            if (NotebookDAO.getByName(notebook).getNotebooks().isEmpty()) {
                response = NotebookDAO.update(notebook);
            } else {
                response.setError(true);
                response.setMessage(Constants.DUPLICATED_INFORMATION_MESSAGE);
            }
        } else {
            response.setError(true);
            response.setMessage(Constants.EMPTY_FIELDS_MESSAGE);
        }
    }

    public boolean checkEmptyFields(Notebook notebook) {
        return notebook.getName().isEmpty()
                || notebook.getHexadecimalColor().isEmpty()
                || String.valueOf(notebook.getIdUser()).isEmpty();
    }

}