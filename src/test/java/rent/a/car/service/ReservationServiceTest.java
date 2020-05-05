package rent.a.car.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import rent.a.car.model.Car;
import rent.a.car.model.Reservation;
import rent.a.car.model.SizeType;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {
    ReservationService underTest;
    int theId = 100;

    @BeforeEach
    public void setup() {
        underTest = new ReservationService(() -> theId);
    }

    @Test
    void createReservation() {
        Reservation reservation = underTest.createReservation(
                new Car(SizeType.LARGE, theId+1), LocalDate.now(), LocalTime.now(), 1);
        assertNotNull(reservation);
        assertTrue(reservation.getBookingSlotList().size() > 0);
    }

    @Test
    void createReservationFailsForNegativeDuration() {
        assertThrows(IllegalArgumentException.class, () ->
        underTest.createReservation(
                new Car(SizeType.LARGE, theId+1), LocalDate.now(), LocalTime.now(), -1)
        );
    }
}