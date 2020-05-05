package rent.a.car.model;

import java.util.Objects;

/**
 * This type models a car.
 */
public class Car {

    private SizeType sizeType;
    private int id;

    /**
     * @param carSize size of this car
     * @param theId its id
     */
    public Car(SizeType carSize, int theId) {
        id = theId;
        sizeType = carSize;
    }

    /**
     * @return size of car
     */
    public SizeType getSizeType() {
        return sizeType;
    }

    /**
     *
     * @return id of this car
     */
    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return getId() == car.getId() &&
                getSizeType() == car.getSizeType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSizeType(), getId());
    }

    @Override
    public String toString() {
        return "Car{" +
                "sizeType=" + sizeType +
                ", id=" + id +
                '}';
    }
}
