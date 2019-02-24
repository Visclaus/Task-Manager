package project.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final String DATE_PATTERN = "dd.MM.yyyy HH:mm";
    private static final DateFormat DATE_FORMATT = new SimpleDateFormat(DATE_PATTERN);

    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATT.format(date);
    }

    public static Date parse(String dateString) {
        try {
            return DATE_FORMATT.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
}
