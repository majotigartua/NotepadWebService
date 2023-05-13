package security;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.xml.bind.DatatypeConverter;

public class BasicAuthentication {

    public static boolean authenticate(String credentialsInBase64) throws UnsupportedEncodingException {
        if (!credentialsInBase64.isEmpty()) {
            byte[] bytes = DatatypeConverter.parseBase64Binary(credentialsInBase64);
            String credentials = new String(bytes, "UTF-8");
            final StringTokenizer stringTokenizer = new StringTokenizer(credentials, ":");
            final String username = stringTokenizer.nextToken();
            final String password = stringTokenizer.nextToken();
            ResourceBundle resourceBundle = ResourceBundle.getBundle("security.credentials");
            String validUsername = resourceBundle.getString("USERNAME");
            String validPassword = resourceBundle.getString("PASSWORD");
            return username.equals(validUsername) && password.equals(validPassword);
        } else {
            return false;
        }
    }

}