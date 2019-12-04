package com.strayorange.cafxx.tokyobuswidget;

import java.time.LocalTime;
import java.util.stream.Stream;

class Timetable {
    private final static Stream<String> depRH = Stream.of(
            "08:15",
            "08:24",
            "08:34",
            "08:45",
            "08:56",
            "09:08",
            "09:17",
            "09:29",
            "09:35",
            "09:44",
            "09:55",
            "10:05",
            "10:11",
            "10:20",
            "10:29",
            "10:46",
            "11:00",
            "11:10",
            "11:19",
            "11:34",
            "11:48",
            "12:04",
            "12:22",
            "12:32",
            "12:46",
            "13:04",
            "13:22",
            "13:39",
            "13:57",
            "14:15",
            "14:29",
            "14:40",
            "14:51",
            "15:08",
            "15:24",
            "15:40",
            "15:56",
            "16:12",
            "16:28",
            "16:44",
            "17:00",
            "17:12",
            "17:24",
            "17:32",
            "17:40",
            "17:48",
            "17:56",
            "18:04",
            "18:12",
            "18:21",
            "18:29",
            "18:37",
            "18:45",
            "18:53",
            "19:04",
            "19:18",
            "19:30",
            "19:46",
            "19:59",
            "20:14",
            "20:34",
            "20:51",
            "21:11",
            "21:37",
            "22:00",
            "22:25",
            "22:50"
    );

    private final static Stream<String> depSh = Stream.of(
            "08:00",
            ""
    );

    public enum loc {
        Shibuya,
        Roppongi
    }

    static String getNext(loc l) {
        return get(l).findFirst().orElse("¯\\_(ツ)_/¯");
    }

    static Stream<String> get(loc l) {
        Stream<String> c = (l == loc.Shibuya ? depSh : depRH);
        String now = LocalTime.now().toString();
        return c.filter(e -> e.compareTo(now) >= 0);
    }
}
