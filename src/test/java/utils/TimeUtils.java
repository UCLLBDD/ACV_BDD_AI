package utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtils {

    public static boolean withinSeconds(int paidTime, int seconds, int paidTimeInSeconds) {
        return ((paidTime>paidTimeInSeconds) ? ((paidTime-paidTimeInSeconds) <= seconds) : ((paidTimeInSeconds-paidTime) <= seconds ));
    }

    public static boolean withinTime(String sExpectedTime, String sActualTime, int timeMargin)
    {
        String[] wordsExpected = sExpectedTime.split("\\s");
        String[] wordsActual = sActualTime.split("\\s");

        LocalTime tActualTime = LocalTime.parse(wordsActual[1]);
        LocalTime tExpectedTime = LocalTime.parse(wordsExpected[1]);
        LocalTime tUpperLimit =  tExpectedTime.plus(timeMargin, ChronoUnit.MINUTES);
        LocalTime tLowerLimit = tExpectedTime.minus(timeMargin, ChronoUnit.MINUTES);

        if(tActualTime.isAfter(tLowerLimit)&&tActualTime.isBefore(tUpperLimit))
        {
            System.out.println("Actual time is between the limits.");
            return true;
        }
        else
        {
            System.out.println("Actual time is not between the limits.");
            return false;
        }
    }

    public static String convertTimeInSecondsToStringFormat(long time) {
        // *1000 for secondsToMiliseconds
        // +639 for our timezone
        Date date = new Date(time*1000+639);
        Timestamp timestamp = new Timestamp(time*1000+639);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(timestamp);
    }

    public static int calculateTimeInSeconds(String timeString) {
        String[] timeArray = timeString.split(":");
        int paidTimeInSeconds = 0;
        if (Integer.parseInt(timeArray[0]) > 0) {
            paidTimeInSeconds += Integer.parseInt(timeArray[0])*60*60;
        }
        if (Integer.parseInt(timeArray[1]) > 0) {
            paidTimeInSeconds += Integer.parseInt(timeArray[1])*60;
        }
        if (Integer.parseInt(timeArray[2]) > 0) {
            paidTimeInSeconds += Integer.parseInt(timeArray[2]);
        }
        return paidTimeInSeconds;
    }

}

