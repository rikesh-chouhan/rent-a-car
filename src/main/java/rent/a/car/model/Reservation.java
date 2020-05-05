package rent.a.car.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import rent.a.car.DateAndTimeFormatter;

/**
 * This type represents a Reservation
 */
public class Reservation {

    private int id;
    private Car car;

    List<BookingSlot> bookingSlotList;

    public Reservation(int theId, Car theCar, int duration) {
        this(theId, theCar, LocalDate.now(), LocalTime.now(), duration);
    }


    public Reservation(int theId, Car theCar, LocalDate startingDate, LocalTime startingTime, int duration) {
        id = theId;
        car = theCar;
        bookingSlotList = new ArrayList();
        addABooking(startingDate, startingTime, duration);
    }

    public int getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public List<BookingSlot> getBookingSlotList() {
        return bookingSlotList;
    }

    public void addABooking(LocalDate anotherDate, LocalTime anotherTime, int howManyDays) {
        if (howManyDays <= 0) throw new IllegalArgumentException("Duration of booking must be 0 or more days");

        LocalDate endDate = anotherDate.plusDays(howManyDays);
        List<LocalDateTime> pastTime = bookingSlotList.stream().map(BookingSlot::getBookingEnd).filter(theDateTime ->
                (theDateTime.toLocalDate().isAfter(endDate) ||
                        (theDateTime.toLocalDate().isEqual(endDate) && theDateTime.toLocalTime().isAfter(anotherTime)))
        ).collect(Collectors.toList());

        if (pastTime.size() > 0) {
            throw new IllegalArgumentException("Provided: reservation datetime is before end of previous reservation");
        }

        bookingSlotList.add(new BookingSlot(anotherDate, anotherTime, howManyDays));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return getId() == that.getId() &&
                getCar().equals(that.getCar()) &&
                getBookingSlotList().equals(that.getBookingSlotList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCar(), getBookingSlotList());
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", car=" + car +
                ", bookingSlotList=" + bookingSlotList +
                '}';
    }
}
