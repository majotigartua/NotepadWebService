package util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class Utilities {

    private final static int RANDOM_NUMBER = 999999;

    public static java.sql.Date convertToSqlDate(Date date) throws ParseException  {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.parse(date.toString());
        return new java.sql.Date(date.getDate());
    }

    public static String generateOneTimePassword() {
        Random random = new Random();
        int oneTimePassword = random.nextInt(RANDOM_NUMBER);
        return String.valueOf(oneTimePassword);
    }

    public static String sendShortMessageService(String cellphoneNumber, String oneTimePassword) {
        cellphoneNumber = "+52" + cellphoneNumber;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("security.credentials");
        String accountSID = resourceBundle.getString("ACCOUNT_SID");
        String authToken = resourceBundle.getString("AUTH_TOKEN");
        String fromCellphoneNumber = resourceBundle.getString("CELLPHONE_NUMBER");
        Twilio.init(accountSID, authToken);
        Message message = Message.creator(new PhoneNumber(cellphoneNumber),
                new PhoneNumber(fromCellphoneNumber),
                "This is your 6-digit code: " + oneTimePassword).create();
        return message.getSid();
    }

}