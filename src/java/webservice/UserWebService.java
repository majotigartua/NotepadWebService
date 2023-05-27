package webservice;

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
        return response;
    }

}