package com.strayorange.cafxx.tokyobuswidget;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class Timetable {
    private final static List<String> depRH = Arrays.asList(
            "08:15", "08:24", "08:34", "08:45", "08:56",
            "09:08", "09:17", "09:29", "09:35", "09:44", "09:55",
            "10:05", "10:11", "10:20", "10:29", "10:46",
            "11:00", "11:10", "11:19", "11:34", "11:48",
            "12:04", "12:22", "12:32", "12:46",
            "13:04", "13:22", "13:39", "13:57",
            "14:15", "14:29", "14:40", "14:51",
            "15:08", "15:24", "15:40", "15:56",
            "16:12", "16:28", "16:44",
            "17:00", "17:12", "17:24", "17:32", "17:40", "17:48", "17:56",
            "18:04", "18:12", "18:21", "18:29", "18:37", "18:45", "18:53",
            "19:04", "19:18", "19:30", "19:46", "19:59",
            "20:14", "20:34", "20:51",
            "21:11", "21:37",
            "22:00", "22:25", "22:50"
    );

    private final static List<String> depSh = Arrays.asList(
            "08:00", "08:07", "08:18", "08:27", "08:39", "08:47",
            "09:00", "09:05", "09:15", "09:24", "09:34", "09:44", "09:52",
            "10:01", "10:12", "10:28", "10:43", "10:54",
            "11:03", "11:16", "11:31", "11:41",
            "12:05", "12:15", "12:29", "12:47",
            "13:05", "13:22", "13:38", "13:56",
            "14:12", "14:19", "14:34", "14:51",
            "15:07", "15:23", "15:39", "15:55",
            "16:11", "16:27", "16:42", "16:51",
            "17:06", "17:15", "17:23", "17:31", "17:39", "17:47", "17:55",
            "18:03", "18:11", "18:19", "18:27", "18:35", "18:47",
            "19:02", "19:12", "19:31", "19:42", "19:56",
            "20:15", "20:37", "20:57",
            "21:20", "21:42",
            "22:08", "22:31"
    );

    static Stream<String> get(loc l) {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        if ((dayOfWeek == DayOfWeek.SATURDAY) || (dayOfWeek == DayOfWeek.SUNDAY)) {
            return Stream.empty();
        }
        List<String> c = (l == loc.Shibuya ? depSh : depRH);
        String now = LocalTime.now().toString();
        return c.stream().filter(e -> e.compareTo(now) >= 0).sorted();
    }

    public enum loc {
        Shibuya,
        Roppongi
    }
}
