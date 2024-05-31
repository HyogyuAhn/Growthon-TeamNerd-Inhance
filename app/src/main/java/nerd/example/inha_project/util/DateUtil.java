package nerd.example.inha_project.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateUtil {

    public static List<String> getWeekDatesForHome() {
        List<String> weekDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat("dd");

        for (int i = -3; i <= 3; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, i);
            String formattedDate = dateFormatter.format(calendar.getTime());
            weekDates.add(formattedDate);
            calendar.add(Calendar.DAY_OF_MONTH, -i);
        }
        return weekDates;
    }

    public static List<String> getWeekDaysForHome() {
        List<String> weekDays = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dayFormatter = new SimpleDateFormat("E");

        for (int i = -3; i <= 3; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, i);
            String formattedDay = dayFormatter.format(calendar.getTime());
            weekDays.add(formattedDay);
            calendar.add(Calendar.DAY_OF_MONTH, -i);
        }

        return weekDays;
    }

}
