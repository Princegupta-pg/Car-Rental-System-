import java.util.*;

class Car {
    private String carId;
    private String model;
    private String brand;
    private double pricePerDay;
    private boolean isAvailable;

    public Car(String carId, String model, String brand, double pricePerDay) {
        this.carId = carId;
        this.model = model;
        this.brand = brand;
        this.pricePerDay = pricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public double calculatePrice(int rentalDays) {
        return pricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String customerName;

    public Customer(String customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
            System.out.println("Car rented successfully!");
        } else {
            System.out.println("Sorry! Car is not available.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();

        Rental rentalToRemove = null;

        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }

        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("Car returned successfully!");
        } else {
            System.out.println("Rental record not found.");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== CAR RENTAL SYSTEM =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {

                System.out.print("Enter your name: ");
                String name = sc.nextLine();

                System.out.println("\nAvailable Cars:");

                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(
                                car.getCarId() + " | "
                                        + car.getBrand() + " | "
                                        + car.getModel());
                    }
                }

                System.out.print("\nEnter Car ID: ");
                String carId = sc.nextLine();

                System.out.print("Enter Rental Days: ");
                int days = sc.nextInt();
                sc.nextLine();

                Customer customer =
                        new Customer("CUS" + (customers.size() + 1), name);

                addCustomer(customer);

                Car selectedCar = null;

                for (Car car : cars) {
                    if (car.getCarId().equalsIgnoreCase(carId)
                            && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {

                    double totalPrice =
                            selectedCar.calculatePrice(days);

                    System.out.println("\n----- Rental Details -----");
                    System.out.println("Customer ID : "
                            + customer.getCustomerId());
                    System.out.println("Customer Name : "
                            + customer.getCustomerName());
                    System.out.println("Car : "
                            + selectedCar.getBrand()
                            + " "
                            + selectedCar.getModel());
                    System.out.println("Rental Days : " + days);

                    System.out.printf("Total Price : ₹%.2f%n",
                            totalPrice);

                    System.out.print("Confirm Rental (Y/N): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, customer, days);
                    } else {
                        System.out.println("Rental cancelled.");
                    }

                } else {
                    System.out.println("Invalid Car ID.");
                }

            } else if (choice == 2) {

                System.out.print("Enter Car ID to return: ");
                String carId = sc.nextLine();

                Car carToReturn = null;

                for (Car car : cars) {
                    if (car.getCarId().equalsIgnoreCase(carId)
                            && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    returnCar(carToReturn);
                } else {
                    System.out.println("Car not found or not rented.");
                }

            } else if (choice == 3) {

                System.out.println("Thank you for using Car Rental System!");
                break;

            } else {
                System.out.println("Invalid Choice!");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {

        CarRentalSystem carRental = new CarRentalSystem();

        Car car1 = new Car("C001", "Camry", "Toyota", 60.0);
        Car car2 = new Car("C002", "Seltos", "Kia", 70.0);
        Car car3 = new Car("C003", "Climber", "Suzuki", 50.0);

        carRental.addCar(car1);
        carRental.addCar(car2);
        carRental.addCar(car3);

        carRental.menu();
    }
}