package com.larn3x.utils;

public class TimeUtils {

    public static int convertTimeLineToTotalSeconds(String time) {
        String[] parts = time.split(":");

        if (parts.length == 2) {
            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);
            return minutes * 60 + seconds;
        } else if (parts.length == 3) {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            int seconds = Integer.parseInt(parts[2]);
            return hours * 3600 + minutes * 60 + seconds;
        } else {
            throw new IllegalArgumentException("Неверный формат времени: " + time);
        }
    }
}