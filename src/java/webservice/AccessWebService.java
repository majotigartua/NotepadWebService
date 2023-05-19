package webservice;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.dao.UserDAO;
import model.pojo.Response;
import model.pojo.Session;
import model.pojo.User;
import security.TokenBasedAuthentication;
import util.Constants;
import util.Utilities;

@Path("basic/access")
public class AccessWebService {

    @Context
    private UriInfo context;

    public AccessWebService() {
    }

    @POST
    @Path("activate")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response activate(@FormParam("cellphoneNumber") String cellphoneNumber,
            @FormParam("oneTimePassword") String oneTimePassword) {
        Response response = new Response();
        Date activationDate = Date.valueOf(LocalDate.now());
        User user = new User(cellphoneNumber, oneTimePassword, activationDate);
        response = UserDAO.activate(user);
        if (response.isError()) {
            response.setMessage(Constants.INVALID_DATA_MESSAGE);
        }
        return response;
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("cellphoneNumber") String cellphoneNumber,
            @FormParam("password") String password) {
        Response response = new Response();
        try {
            password = Utilities.computeSHA256Hash(password);
        } catch (NoSuchAlgorithmException exception) {
            response.setError(true);
            response.setMessage(exception.getMessage());
        }
        User user = new User(cellphoneNumber, password);
        response = UserDAO.login(user);
        if (!response.isError()) {
            user = response.getUser();
            Session session = createSession(user);
            String accessToken = session.getAccessToken();
            if (!accessToken.isEmpty()) {
                Date lastAccessDate = Date.valueOf(LocalDate.now());
                user.setLastAccessToken(accessToken);
                user.setLastAccessDate(lastAccessDate);
                if (!UserDAO.update(user).isError()) {
                    response.setSession(session);
                }
            } else {
                response.setError(true);
                response.setMessage(Constants.ACCESS_TOKEN_GENERATION_ERROR);
            }
        } else {
            response.setError(true);
            response.setMessage(Constants.INVALID_DATA_MESSAGE);
        }
        return response;
    }

    public Session createSession(User user) {
        Session session = new Session();
        session.setName(user.getName());
        session.setPaternalSurname(user.getPaternalSurname());
        session.setMaternalSurname(user.getMaternalSurname());
        session = TokenBasedAuthentication.generateAccessToken(session);
        return session;
    }

    @POST
    @Path("signup")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signUp(@FormParam("name") String name,
            @FormParam("paternalSurname") String paternalSurname,
            @FormParam("maternalSurname") String maternalSurname,
            @FormParam("cellphoneNumber") String cellphoneNumber,
            @FormParam("password") String password) {
        Response response = new Response();
        try {
            password = Utilities.computeSHA256Hash(password);
        } catch (NoSuchAlgorithmException exception) {
            response.setError(true);
            response.setMessage(exception.getMessage());
        }
        boolean isAvailable = UserDAO.getUserByCellphoneNumber(cellphoneNumber).getUser() == null;
        if (isAvailable) {
            Date registrationDate = Date.valueOf(LocalDate.now());
            String oneTimePassword = Utilities.generateOneTimePassword();
            User user = new User(name, paternalSurname, maternalSurname, registrationDate, cellphoneNumber, password, oneTimePassword);
            response = UserDAO.signUp(user);
            if (!response.isError()) {
                response = sendShortMessageService(response);
            }
        } else {
            response.setError(true);
            response.setMessage(Constants.DUPLICATED_INFORMATION_MESSAGE);
        }
        return response;
    }

    private Response sendShortMessageService(Response response) {
        User user = response.getUser();
        String cellphoneNumber = user.getCellphoneNumber();
        String oneTimePassword = user.getOneTimePassword();
        String sid = Utilities.sendShortMessageService(cellphoneNumber, oneTimePassword);
        if (sid.isEmpty()) {
            response.setError(true);
            response.setMessage(Constants.NO_SEND_SHORT_MESSAGE_SERVICE_CONNECTION_MESSAGE);
        }
        return response;
    }

}