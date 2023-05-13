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
        if (!cellphoneNumber.isEmpty() && !oneTimePassword.isEmpty()) {
            User user = new User();
            user.setCellphoneNumber(cellphoneNumber);
            user.setOneTimePassword(oneTimePassword);
            response = UserDAO.activate(user);
            if (response.isError()) {
                response.setMessage(Constants.INVALID_DATA_MESSAGE);
            }
        } else {
            response.setError(true);
            response.setMessage(Constants.EMPTY_FIELDS_MESSAGE);
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
        if (!cellphoneNumber.isEmpty() && !password.isEmpty()) {
            try {
                User user = new User();
                password = Utilities.computeSHA256Hash(password);
                user.setCellphoneNumber(cellphoneNumber);
                user.setPassword(password);
                response = UserDAO.login(user);
                if (!response.isError()) {
                    Session session = new Session();
                    session = TokenBasedAuthentication.generateAccessToken(session);
                    String accessToken = session.getAccessToken();
                    if (!accessToken.isEmpty()) {
                        user = response.getUser();
                        user.setLastAccessToken(accessToken);
                        user.setLastAccessDate(Date.valueOf(LocalDate.now()));
                        if (!UserDAO.update(user).isError()) {
                            session.setUser(user);
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
            } catch (NoSuchAlgorithmException exception) {
                response.setError(true);
                response.setMessage(exception.getMessage());
            }
        } else {
            response.setError(true);
            response.setMessage(Constants.EMPTY_FIELDS_MESSAGE);
        }
        return response;
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
        User user = new User();
        user.setName(name);
        user.setPaternalSurname(paternalSurname);
        user.setMaternalSurname(maternalSurname);
        user.setCellphoneNumber(cellphoneNumber);
        try {
            password = Utilities.computeSHA256Hash(password);
            String oneTimePassword = Utilities.generateOneTimePassword();
            user.setRegistrationDate(Date.valueOf(LocalDate.now()));
            user.setPassword(password);
            user.setOneTimePassword(oneTimePassword);
            if (!checkEmptyFields(user)) {
                if (UserDAO.getByCellphoneNumber(cellphoneNumber).getUser() == null) {
                    response = UserDAO.signUp(user);
                    if (!response.isError()) {
                        String sid = Utilities.sendShortMessageService(cellphoneNumber, oneTimePassword);
                        if (sid.isEmpty()) {
                            response.setError(true);
                            response.setMessage(Constants.NO_SEND_SHORT_MESSAGE_SERVICE_CONNECTION_MESSAGE);
                        }
                    }
                } else {
                    response.setError(true);
                    response.setMessage(Constants.DUPLICATED_INFORMATION_MESSAGE);
                }
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
                || user.getCellphoneNumber().isEmpty()
                || user.getPassword().isEmpty();
    }

}