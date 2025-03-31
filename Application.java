/** The Application class offers a text-based interface for handling the management of parking slots and vehicles.
 * @author: Balagopalapracheethaa Senthilkumar
 * @date: 07.09.2024
 * @version 1.0
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Application {
    private CarPark carParkingLot;
    private Scanner appScanner;

    public Application() {
        carParkingLot = new CarPark();
        appScanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Application appInstance = new Application();
        appInstance.runApplication();
    }

    public void runApplication() {
        initializeParkingLot();
        System.out.println("Welcome to the Parking Spot System!");
        System.out.println("Available parking slots:");
        carParkingLot.listAllParkingSlots(); // Show all available parking slots and their status

        int userOption;
        do {
            displayMenu();
            try {
                userOption = Integer.parseInt(appScanner.nextLine());
                handleMenuSelection(userOption);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                userOption = 0;
            }
        } while (userOption != 8);
        System.out.println("Program end!");
    }

    private void initializeParkingLot() {
        System.out.print("Enter number of staff slots: ");
        int staffSlotCount = Integer.parseInt(appScanner.nextLine());
        System.out.print("Enter number of visitor slots: ");
        int visitorSlotCount = Integer.parseInt(appScanner.nextLine());

        for (int i = 1; i <= staffSlotCount; i++) {
            String slotID = String.format("A%02d", i);
            carParkingLot.addParkingSlot(new ParkingSlot(slotID, true));
        }

        for (int i = 1; i <= visitorSlotCount; i++) {
            String slotID = String.format("B%02d", i);
            carParkingLot.addParkingSlot(new ParkingSlot(slotID, false));
        }
        System.out.println("\nParking lot initialized with " + staffSlotCount + " staff slots and " + visitorSlotCount + " visitor slots.");
    }

    private void displayMenu() {
        System.out.println("\n1. Add a parking slot");
        System.out.println("2. Delete a parking slot");
        System.out.println("3. List all parking slots");
        System.out.println("4. Delete all unoccupied slots");
        System.out.println("5. Park a car into a slot");
        System.out.println("6. Find a car by registration number");
        System.out.println("7. Remove a car by registration number");
        System.out.println("8. Exit");
        System.out.print("Select an option: ");
    }

    private void handleMenuSelection(int userOption) {
        switch (userOption) {
            case 1:
                addParkingSlotToLot();
                break;
            case 2:
                deleteParkingSlotFromLot();
                break;
            case 3:
                carParkingLot.listAllParkingSlots();
                break;
            case 4:
                carParkingLot.deleteAllUnoccupiedParkingSlots();
                break;
            case 5:
                parkCarInSlot();
                break;
            case 6:
                findCarInParkingLot();
                break;
            case 7:
                removeCarFromParkingLot();
                break;
            case 8:
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void addParkingSlotToLot() {
        System.out.print("Enter slot ID (format: A01/B01): ");
        String newSlotID = appScanner.nextLine();
        if (!newSlotID.matches("^[A-Z][0-9]{2}$")) {
            System.out.println("Incorrect format, please enter the correct slot format e.g. S01.");
            return;
        }
        System.out.print("Is this slot for staff? (yes/no): ");
        boolean isStaffSlot = appScanner.nextLine().equalsIgnoreCase("yes");

        try {
            carParkingLot.addParkingSlot(new ParkingSlot(newSlotID, isStaffSlot));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteParkingSlotFromLot() {
        System.out.print("Enter slot ID to delete: ");
        String slotIDToDelete = appScanner.nextLine();
        carParkingLot.deleteParkingSlot(slotIDToDelete);
    }
    private void listAllSlots() {
    carParkingLot.listAllParkingSlots();
    }

    private void parkCarInSlot() {
        System.out.print("Enter slot ID: ");
        String slotID = appScanner.nextLine();
        ParkingSlot selectedSlot = carParkingLot.getParkingSlotByID(slotID);

        if (selectedSlot != null) {
            if (selectedSlot.isSlotOccupied()) {
                System.out.println("Error: Slot is already occupied.");
                return;
            }

            System.out.print("Enter car registration number (format: A1234): ");
            String regNumber = appScanner.nextLine();
            if (!regNumber.matches("^[A-Z][0-9]{4}$")) {
                System.out.println("Incorrect format, please enter the correct registration format e.g. A1234.");
                return;
            }

            System.out.print("Enter owner name: ");
            String ownerName = appScanner.nextLine();
            System.out.print("Press 1 if owner is a staff member, or 0 for visitor: ");
            boolean isCarOwnerStaff = appScanner.nextInt() == 1;
            appScanner.nextLine(); // Consume the newline character

            try {
                Car carToPark = new Car(regNumber, ownerName, isCarOwnerStaff);
                if (isCarOwnerStaff != selectedSlot.isSlotForStaff()) {
                    System.out.println("Error: Staff car can only be parked in staff slots, and visitor cars in visitor slots.");
                    return;
                }
                selectedSlot.parkCarInSlot(carToPark);

                // Record and display parking time
                LocalDateTime parkingTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                System.out.println("Car parked successfully on " + parkingTime.format(formatter));

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Slot not found.");
        }
    }

    private void findCarInParkingLot() {
        System.out.print("Enter the car's registration number: ");
        String carRegNumber = appScanner.nextLine();
        ParkingSlot slotWithCar = carParkingLot.findParkingSlotByCar(carRegNumber);

        if (slotWithCar != null) {
            System.out.println("Car found in slot: " + slotWithCar);
        } else {
            System.out.println("Car not found in any slot.");
        }
    }

    private void removeCarFromParkingLot() {
        System.out.print("Enter the car's registration number: ");
        String carRegNumberToRemove = appScanner.nextLine();
        carParkingLot.removeCarFromParkingSlot(carRegNumberToRemove);
    }
}
