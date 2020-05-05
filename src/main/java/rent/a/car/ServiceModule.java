package rent.a.car;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import rent.a.car.service.CarService;
import rent.a.car.service.ReservationService;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CarService.class).annotatedWith(Names.named("CarService"))
                .to(CarService.class);
        bind(ReservationService.class).annotatedWith(Names.named("ReservationService"))
                .to(ReservationService.class);
    }
}
