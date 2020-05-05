package rent.a.car.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    Reservation reservation;
    int theId = 100;

    @BeforeEach
    public void setup() {
        reservation = new Reservation(theId+1, new Car(SizeType.MEDIUM, theId), 1);
    }

    @Test
    void addABooking() {
        reservation.addABooking(LocalDate.now().plusDays(2), LocalTime.now(), 1);
        assertTrue(reservation.getBookingSlotList().size() == 2);
    }

    @Test
    void addABookingThrowsExceptionForOverlappingDays() {
        reservation.addABooking(LocalDate.now().plusDays(2), LocalTime.now(), 5);
        assertThrows(IllegalArgumentException.class, () ->
                reservation.addABooking(LocalDate.now().plusDays(4), LocalTime.now(), 1));
    }
}