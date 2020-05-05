package rent.a.car;

import java.time.LocalDate;
import java.time.LocalTime;

public interface DateAndTimeFormatter {
    String formatDate(LocalDate localDate);
    String formatTime(LocalTime localTime);
}
