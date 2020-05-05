package rent.a.car.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * A booking slot for a reservation
 * It is the start date and time and the duration in days
 */
public class BookingSlot {
    private LocalDate startDate;
    private LocalTime startTime;
    private int durationDays;

    public BookingSlot(LocalDate localDate, LocalTime localTime, int durationDays) {
        this.startDate = localDate;
        this.startTime = localTime;
        this.durationDays = durationDays;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getBookingEnd() {
        return LocalDateTime.of(startDate, startTime).plus(durationDays, ChronoUnit.DAYS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingSlot)) return false;
        BookingSlot that = (BookingSlot) o;
        return getDurationDays() == that.getDurationDays() &&
                getStartDate().equals(that.getStartDate()) &&
                getStartTime().equals(that.getStartTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartDate(), getStartTime(), getDurationDays());
    }

    @Override
    public String toString() {
        return "BookingSlot{" +
                "startDate=" + startDate +
                ", startTime=" + startTime +
                ", durationDays=" + durationDays +
                '}';
    }
}
