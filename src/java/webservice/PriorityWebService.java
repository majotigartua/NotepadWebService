package webservice;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import model.dao.PriorityDAO;
import model.pojo.Response;
import util.Constants;

@Path("auth/priorities")
public class PriorityWebService {

    @Context
    private UriInfo context;

    public PriorityWebService() {
    }

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPriorities() {
        Response response = PriorityDAO.getPriorities();
        if (!response.isError()) {
            response.setCode(HttpServletResponse.SC_OK);
            response.setMessage(Constants.CORRECT_OPERATION_MESSAGE);
        } else {
            response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }
    
}