package webservice;

import java.security.NoSuchAlgorithmException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import model.dao.UserDAO;
import model.pojo.Response;
import model.pojo.User;
import util.Constants;
import util.Utilities;

@Path("auth/user")
public class UserWebService {

    @Context
    private UriInfo context;

    public UserWebService() {
    }

    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@FormParam("idUser") int idUser,
            @FormParam("name") String name,
            @FormParam("paternalSurname") String paternalSurname,
            @FormParam("maternalSurname") String maternalSurname,
            @FormParam("password") String password) {
        Response response = new Response();
        try {
            password = Utilities.computeSHA256Hash(password);
            User user = new User();
            user.setIdUser(idUser);
            user.setName(name);
            user.setPaternalSurname(paternalSurname);
            user.setMaternalSurname(maternalSurname);
            user.setPassword(password);
            if (!checkEmptyFields(user)) {
                response = UserDAO.update(user);
            } else {
                response.setError(true);
                response.setMessage(Constants.EMPTY_FIELDS_MESSAGE);
            }
        } catch (NoSuchAlgorithmException exception) {
            response.setError(true);
            response.setMessage(exception.getMessage());
        }
        return response;
    }

    public boolean checkEmptyFields(User user) {
        return user.getName().isEmpty()
                || user.getPaternalSurname().isEmpty()
                || user.getMaternalSurname().isEmpty()
                || user.getPassword().isEmpty();
    }

}