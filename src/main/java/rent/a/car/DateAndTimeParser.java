package rent.a.car;

import java.time.LocalDate;
import java.time.LocalTime;

interface DateAndTimeParser {

    LocalDate parseDate(String inputDate);

    LocalTime parseTime(String inputTime);
}
