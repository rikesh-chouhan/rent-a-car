package rent.a.car.service;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Properties;

import rent.a.car.Util;
import rent.a.car.model.SizeType;

import static rent.a.car.Constants.DEFAULT_MAX_CARS;
import static rent.a.car.Constants.LARGE_CARS_KEY;
import static rent.a.car.Constants.MEDIUM_CARS_KEY;
import static rent.a.car.Constants.SMALL_CARS_KEY;

public class ServicesInitializer {

    @Inject @Named("ReservationService")
    private ReservationService reservationService;
    @Inject @Named("CarService")
    private CarService carService;
    private boolean servicesInitialized = false;

    public ServicesInitializer() {
    }

    public synchronized void initServices(Properties initProperties) {
        if (servicesInitialized) return;

        for (SizeType one: SizeType.values()) {
            String value = getCarsNumberValue(one, initProperties);
            int numberOfCars = 0;
            if (value == null || value.length() == 0) {
                System.err.printf("Properties file value for %s cars is not present, using default value: %s\n",
                        value, DEFAULT_MAX_CARS);
                numberOfCars = DEFAULT_MAX_CARS;
            } else {
                try {
                    numberOfCars = Integer.parseInt(value);
                } catch (Exception e) {
                    numberOfCars = DEFAULT_MAX_CARS;
                }
            }
            carService.setCars(one, numberOfCars);
        }

        servicesInitialized = true;
    }

    public synchronized void initDefaults() {
        if (servicesInitialized) return;
        for (SizeType one: SizeType.values()) {
            carService.setCars(one, DEFAULT_MAX_CARS);
        }
        servicesInitialized = true;
    }

    public synchronized ReservationService getReservationService() {
        checkInit();
        return reservationService;
    }

    public CarService getCarService() {
        checkInit();
        return carService;
    }

    private void checkInit() {
        if (!servicesInitialized) {
            throw new IllegalStateException("Services have not been initialized");
        }
    }

    private String getCarsNumberValue(SizeType size, Properties properties) {

        String carValue = null;
        switch (size) {
            case SMALL:
                carValue = properties.getProperty(SMALL_CARS_KEY);
                break;
            case MEDIUM:
                carValue = properties.getProperty(MEDIUM_CARS_KEY);
                break;
            case LARGE:
                carValue = properties.getProperty(LARGE_CARS_KEY);
                break;
            default:
                carValue = "-1";
                break;
        }
        return carValue;
    }

}
