package com.example.demo.util;

import java.time.LocalDate;

public class DateRangeUtil {

    public static boolean overlaps(
            LocalDate start1, LocalDate end1,
            LocalDate start2, LocalDate end2) {

        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }
}
