package rent.a.car.service;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

import rent.a.car.IdGenerator;
import rent.a.car.model.Car;
import rent.a.car.model.SizeType;

public class CarService implements Service {

    private int maxSmallCars;
    private int maxMediumCars;
    private int maxLargeCars;
    private AtomicInteger availableSmallCars;
    private AtomicInteger availableMediumCars;
    private AtomicInteger availableLargeCars;
    @Inject
    @Named("IdGenerator")
    private IdGenerator idGenerator;

    public CarService() {

    }

    void setIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    void setCars(SizeType type, int number) {
        if (number < 0)
            throw new IllegalArgumentException("Number of cars cannot be less than 0 for any car type");
        switch (type) {
            case SMALL:
                maxSmallCars = number;
                availableSmallCars = new AtomicInteger(number);
                break;
            case MEDIUM:
                maxMediumCars = number;
                availableMediumCars = new AtomicInteger(number);
                break;
            case LARGE:
                maxLargeCars = number;
                availableLargeCars = new AtomicInteger(number);
                break;
            default:
                System.err.println("Incorrect SizeType: " + type);
                break;
        }
    }

    public synchronized Car getCar(SizeType type) throws Exception {
        AtomicInteger counter = getCounterForType(type);
        int maxNumberForType = getMaxNumberForType(type);

        if (counter != null && counter.get() > 0) {
            counter.decrementAndGet();
            return new Car(type, idGenerator.getAnId());
        }

        throw new IllegalStateException("Could not find any cars for size: " + type);
    }

    public void print() {
        System.out.println("CarService has following available cars");
        getAvailableCars().entrySet().stream().forEach(entry ->
                System.out.println("Car size: " + entry.getKey().getSizeName() +
                        " has " + (entry.getValue() != null ? entry.getValue() : 0) + " available cars")
        );
    }

    public Map<SizeType, Integer> getAvailableCars() {
        Map<SizeType, Integer> availableCars = new TreeMap();
        for (SizeType sizeType : SizeType.values()) {
            AtomicInteger counter = getCounterForType(sizeType);
            availableCars.put(sizeType, counter.get());
        }
        return availableCars;
    }

    public boolean areAnyCarsAvailable() {
        boolean anyAvailability = false;
        for (SizeType sizeType : SizeType.values()) {
            AtomicInteger counter = getCounterForType(sizeType);
            anyAvailability = anyAvailability || (counter != null && counter.get() > 0);
        }
        return  anyAvailability;
    }

    @Nullable
    private AtomicInteger getCounterForType(SizeType type) {
        AtomicInteger counter = null;
        switch (type) {
            case SMALL:
                counter = availableSmallCars;
                break;
            case MEDIUM:
                counter = availableMediumCars;
                break;
            case LARGE:
                counter = availableLargeCars;
                break;
            default:
                System.err.println("Incorrect SizeType: " + type);
                break;
        }
        return counter;
    }

    private int getMaxNumberForType(SizeType type) {
        int number = -1;
        switch (type) {
            case SMALL:
                number = maxSmallCars;
                break;
            case MEDIUM:
                number = maxMediumCars;
                break;
            case LARGE:
                number = maxLargeCars;
                break;
            default:
                System.err.println("Incorrect SizeType: " + type);
                break;
        }
        return number;
    }

}
