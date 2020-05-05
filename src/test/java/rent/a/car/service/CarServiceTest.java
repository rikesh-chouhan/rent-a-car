package rent.a.car.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import rent.a.car.Util;
import rent.a.car.model.Car;
import rent.a.car.model.SizeType;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

    CarService underTest;
    int[] carsRestricted = { 1, 1, 1};
    int[] manyCars = { 3, 3, 3};
    int theId = 100;

    @BeforeEach
    void setUp() {
        underTest = new CarService();
        underTest.setIdGenerator(() -> theId);
        //underTest = new CarService(() -> theId);
    }

    @Test
    void setCars() {
        Arrays.stream(SizeType.values())
                .forEach(sizeType -> underTest.setCars(sizeType, carsRestricted[sizeType.ordinal()]));
        int sum = Arrays.stream(carsRestricted).boxed().collect(Collectors.summingInt(Integer::intValue));
        int sumValue = underTest.getAvailableCars().values().stream().collect(Collectors.summingInt(Integer::intValue));
        assertTrue(sum == sumValue);
    }

    @Test
    void getCar() {
        Arrays.stream(SizeType.values())
                .forEach(sizeType -> {
                    underTest.setCars(sizeType, manyCars[sizeType.ordinal()]);
                });
        List<Car> cars = Arrays.stream(SizeType.values())
                .map(sizeType -> {
                    try {
                        return underTest.getCar(sizeType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(item -> item != null)
                .collect(Collectors.toList());
        assertTrue(cars.size() == 3);
    }

    @Test
    void getCarThrowsErrorOnRequestingMoreThanAvailable() {
        Arrays.stream(SizeType.values())
                .forEach(sizeType -> {
                    underTest.setCars(sizeType, carsRestricted[sizeType.ordinal()]);
                });
        int counter = 0;
        assertDoesNotThrow(() -> underTest.getCar(SizeType.SMALL));
        assertThrows(IllegalStateException.class, () -> underTest.getCar(SizeType.SMALL));
    }

    @Test
    void getAvailableCars() {
        Arrays.stream(SizeType.values())
                .forEach(sizeType -> underTest.setCars(sizeType, manyCars[sizeType.ordinal()]));
        assertTrue(underTest.getAvailableCars().size() > 0);
    }

    @Test
    void areAnyCarsAvailable() {
        Arrays.stream(SizeType.values())
                .forEach(sizeType -> underTest.setCars(sizeType, carsRestricted[sizeType.ordinal()]));
        assertTrue(underTest.areAnyCarsAvailable());
    }

    @Test
    void areAnyCarsAvailableReturnsFalse() {
        Arrays.stream(SizeType.values())
                .forEach(sizeType -> underTest.setCars(sizeType, carsRestricted[sizeType.ordinal()]));
        assertDoesNotThrow(() -> underTest.getCar(SizeType.SMALL));
        assertDoesNotThrow(() -> underTest.getCar(SizeType.MEDIUM));
        assertDoesNotThrow(() -> underTest.getCar(SizeType.LARGE));
        assertFalse(underTest.areAnyCarsAvailable());
    }
}