package rent.a.car.model;

public enum SizeType {
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large");

    private String sizeName;

    private SizeType(String named) {
        sizeName = named;
    }

    public String getSizeName() {
        return sizeName;
    }
}
