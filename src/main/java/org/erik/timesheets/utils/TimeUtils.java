package org.erik.timesheets.utils;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class TimeUtils {

    public static Instant fromUserTime(LocalTime localTime) {
        return Instant.from(ZonedDateTime.now()
                .withHour(localTime.getHour())
                .withMinute(localTime.getMinute())
                .withSecond(0)
                .withNano(0)
        );
    }

    public static LocalTime roundToNearestMinutes(LocalTime localTime, int nearestMinutes) {
        int minute = localTime.getMinute();
        minute = Math.round(minute / (float) nearestMinutes) * nearestMinutes;
        if (minute >= 60) {
            minute -= 60;
            localTime = localTime.plusHours(1);
        }
        return localTime.withMinute(minute);
    }
}
