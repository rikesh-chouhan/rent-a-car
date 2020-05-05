package rent.a.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    int month = 1;
    int year = 2020;
    int day = 1;
    int hour = 8;
    int minutes = 30;
    String formattedTestDate = "0"+month+"-0"+day+"-"+year;
    String formattedTestTime = "0"+hour+":"+minutes+" AM";
    Util underTest;

    @BeforeEach
    void setUp() {
        underTest = new Util();
    }

    @Test
    void formatDate() {
        LocalDate date = LocalDate.of(year, month, day);
        assertEquals(underTest.formatDate(date), formattedTestDate);
    }

    @Test
    void formatTime() {
        LocalTime time = LocalTime.of(hour, minutes);
        assertEquals(underTest.formatTime(time), formattedTestTime);
    }

    @Test
    void parseDate() {
        LocalDate date = LocalDate.of(year, month, day);
        assertEquals(underTest.parseDate(formattedTestDate), date);
    }

    @Test
    void parseTime() {
        LocalTime time = LocalTime.of(hour, minutes);
        assertEquals(underTest.parseTime(formattedTestTime), time);
    }

    @Test
    void getAnId() {
        int anId = underTest.getAnId();
        int anotherId = underTest.getAnId();
        assertNotEquals(anId, anotherId);
    }
}