package rent.a.car;

import com.google.common.annotations.VisibleForTesting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static rent.a.car.Constants.DATE_FORMAT;
import static rent.a.car.Constants.TIME_FORMAT;

public class Util implements DateAndTimeFormatter, IdGenerator, DateAndTimeParser {

    private DateTimeFormatter dateFormatter;
    private DateTimeFormatter timeFormatter;
    Random random;

    @VisibleForTesting
    Util() {
        dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        random = new Random(Integer.MAX_VALUE);
    }

    public String formatDate(LocalDate date) {
        return dateFormatter.format(date);
    }

    public String formatTime(LocalTime time) {
        return timeFormatter.format(time);
    }

    @Override
    public LocalDate parseDate(String inputDate) {
        return LocalDate.parse(inputDate, dateFormatter);
    }

    @Override
    public LocalTime parseTime(String inputTime) {
        return LocalTime.parse(inputTime, timeFormatter);
    }

    @Override
    public Integer getAnId() {
        return random.nextInt();
    }
}
