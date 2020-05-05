package rent.a.car.service;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rent.a.car.IdGenerator;
import rent.a.car.model.Car;
import rent.a.car.model.Reservation;

/**
 * This service allows creation and deletion of a Reservation
 */
public class ReservationService implements Service {

    private List<Reservation> reservationList;
    @Inject @Named("IdGenerator")
    private IdGenerator idGenerator;

    public ReservationService() {
        reservationList = new ArrayList<>();
    }

    void setIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public synchronized Reservation createReservation(Car car, LocalDate theDate, LocalTime theTime, int duration) {
        if (duration <= 0) throw new IllegalArgumentException("Reservation has to be for at least 1 day");
        Reservation reservation = new Reservation(idGenerator.getAnId(), car, theDate, theTime, duration);
        reservationList.add(reservation);
        return reservation;
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservationList);
    }

}
