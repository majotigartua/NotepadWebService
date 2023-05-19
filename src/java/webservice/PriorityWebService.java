package webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import model.dao.PriorityDAO;
import model.pojo.Response;

@Path("auth/priority")
public class PriorityWebService {

    @Context
    private UriInfo context;

    public PriorityWebService() {
    }

    @GET
    @Path("get")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPriorities() {
        Response response = PriorityDAO.getPriorities();
        return response;
    }
    
}