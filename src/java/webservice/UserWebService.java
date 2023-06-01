package webservice;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import model.dao.UserDAO;
import model.pojo.Response;
import model.pojo.User;
import util.Constants;

@Path("auth/users")
public class UserWebService {

    @Context
    private UriInfo context;

    public UserWebService() {
    }

    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(User user) {
        Response response = new Response();
        response = UserDAO.update(user);
        if (!response.isError()) {
            response.setCode(HttpServletResponse.SC_OK);
            response.setMessage(Constants.MODIFIED_INFORMATION_MESSAGE);
        } else {
            response.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(Constants.NO_DATABASE_CONNECTION_MESSAGE);
        }
        return response;
    }

}