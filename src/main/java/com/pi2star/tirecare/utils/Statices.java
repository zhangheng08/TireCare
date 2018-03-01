package com.pi2star.tirecare.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class Statices {

    public static long getTimeUTC() {

        String zone = ZoneId.systemDefault().toString();
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.now(), ZoneId.of(zone));
        //System.out.println("zone is '" + zone + "' date: " + ldt.getYear() + "-" + ldt.getMonthValue() + "-" + ldt.getDayOfMonth() + " " + ldt.getHour() + ":" + ldt.getMinute() + ":" + ldt.getSecond());
        long timeSecLocal = ldt.toEpochSecond(ZoneOffset.of("+8"));

        LocalDateTime ldt2 = LocalDateTime.ofEpochSecond(timeSecLocal, 0, ZoneOffset.of("Z"));
        ldt = ldt2;
        //System.out.println("to UTC: " + ldt.getYear() + "-" + ldt.getMonthValue() + "-" + ldt.getDayOfMonth() + " " + ldt.getHour() + ":" + ldt.getMinute() + ":" + ldt.getSecond());
        long timeSecUTC = ldt2.toEpochSecond(ZoneOffset.of("+8"));

        //System.out.println("with " + (timeSecLocal - timeSecUTC) / 3600 + " hour");

        return timeSecUTC;

    }

}
