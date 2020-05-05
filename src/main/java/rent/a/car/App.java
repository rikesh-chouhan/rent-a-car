/**
 * This is the main driver class for the application
 */
package rent.a.car;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import rent.a.car.model.BookingSlot;
import rent.a.car.service.CarService;
import rent.a.car.service.ServiceFactory;

import static rent.a.car.Constants.DATE_FORMAT;
import static rent.a.car.Constants.TIME_FORMAT;
import static rent.a.car.Constants.YES;

public class App {

    ServiceFactory serviceFactory;
    Util util;

    public App() {
        util = new Util();
        serviceFactory = new ServiceFactory(util);
    }

    public String appStarted() {
        return "Welcome to Rent-A-Car.";
    }

    public static void main(String[] args) {
        InputStream inputStream = App.class.getResourceAsStream("/initialization.properties");
        App app = new App();
        try {
            app.loadConfig(inputStream);
            System.out.println(app.appStarted());
        } catch (IOException e) {
            System.err.println("Exception reading properties file: using defaults");
            e.printStackTrace();
            app.loadDefaults();
        }
        app.serviceFactory.getCarService().print();
    }

    private void reserveCars() {
        boolean keepRunning = true;
        try (Scanner scanner = new Scanner(System.in)) {
            CarService carService = serviceFactory.getCarService();
            while (keepRunning) {
                System.out.print("Would you like to Rent a car?");
                String answer = scanner.nextLine();
                boolean carsAvailable = carService.areAnyCarsAvailable();
                if (answer.trim().equals(YES) && carsAvailable) {
                    int pick = pickCar(carService, scanner);
                    if (pick != -1) {
                        List<BookingSlot> list = getBookingTimes(scanner);
                        if (list.size() > 0) {

                        } else {
                            System.out.println("No bookings provided. Good bye");
                            keepRunning = false;
                        }
                    } else {
                        keepRunning = false;
                    }
                } else if (!carsAvailable && answer.trim().equals(YES)) {
                    System.out.println("Sorry no cars available, try later. Good bye");
                    keepRunning = false;
                } else {
                    keepRunning = false;
                }
            }
            System.out.println("Bye");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<BookingSlot> getBookingTimes(Scanner scanner) {
        List<BookingSlot> bookingSlots = new ArrayList<>();
        System.out.println("Enter booking slots in the following format date time days: "+DATE_FORMAT+" "+TIME_FORMAT+" n");
        String line = "";
        do {
            line = scanner.nextLine();
            if (line.trim().length() > 0) {
                String[] array = line.split(" ");
                if (array.length != 3) {
                    System.out.println("Incorrect booking entry");
                } else {
                    LocalDate date = util.parseDate(array[0]);
                    LocalTime time = util.parseTime(array[1]);
                    int days = Integer.parseInt(array[2]);
                    bookingSlots.add(new BookingSlot(date, time, days));
                }
            }
        } while (line.trim().length() > 0);
        return bookingSlots;
    }

    private int pickCar(CarService carService, Scanner scanner) {
        System.out.println("Pick cars from the following:");
        carService.print();
        System.out.println("Which car type would you like? 1 - 3");
        int pick = scanner.nextInt();
        if (pick < 1 || pick > 3) {
            System.out.println("Invalid pick: "+pick);
            System.out.println("Would you like to pick again?");
            if (YES.equalsIgnoreCase(scanner.nextLine())) {
                return pickCar(carService, scanner);
            } else {
                return -1;
            }
        }
        return pick;
    }
    private void loadConfig(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        serviceFactory.initServices(properties);
    }

    private void loadDefaults() {
        serviceFactory.initDefaults();
    }

}
