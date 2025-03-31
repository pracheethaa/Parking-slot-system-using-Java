/** The CarPark class manages the collection of parking slot for a parking lot. 
 * It gives access to add, delete, find the slot and park in it
 * @author: Balagopalapracheethaa Senthilkumar
 * @date: 07.09.2024
 * @version 1.0
 */
import java.util.ArrayList;

public class CarPark {
    private ArrayList<ParkingSlot> parkingSlots;

    public CarPark() {
        parkingSlots = new ArrayList<>();
    }
    
 // Getter method to retrieve parking slots
    public ArrayList<ParkingSlot> getParkingSlots() {
        return parkingSlots;
    }
    public ParkingSlot getParkingSlotByID(String slotIdentifier) {
        return findParkingSlot(slotIdentifier);
    }

    private ParkingSlot findParkingSlot(String slotIdentifier) {
        for (ParkingSlot parkingSlot : parkingSlots) {
            if (parkingSlot.getSlotIdentifier().equals(slotIdentifier)) {
                return parkingSlot;
            }
        }
        return null;
    }
    // new addition
    public boolean doesSlotExist(String slotID) {
        for (ParkingSlot slot : parkingSlots) {
            if (slot.getSlotIdentifier().equals(slotID)) {
                return true;  // Slot exists
            }
        }
        return false;  // Slot does not exist
    } //

    

    public void addParkingSlot(ParkingSlot parkingSlot) {
        if (findParkingSlot(parkingSlot.getSlotIdentifier()) != null) {
            System.out.println("Slot ID must be unique. Parking slot not added.");
            return;
        }
        parkingSlots.add(parkingSlot);
        //System.out.println("Parking slot added successfully.");
    }

    public void deleteParkingSlot(String slotIdentifier) {
        ParkingSlot parkingSlot = findParkingSlot(slotIdentifier);
        if (parkingSlot != null && !parkingSlot.isSlotOccupied()) {
            parkingSlots.remove(parkingSlot);
            System.out.println("Parking slot deleted successfully.");
        } else {
            System.out.println("Parking slot is either occupied or doesn't exist.");
        }
    }

    public void listAllParkingSlots() {
        for (ParkingSlot parkingSlot : parkingSlots) {
            System.out.println(parkingSlot);
        }
    }

    public ParkingSlot findParkingSlotByCar(String carRegNumber) {
        for (ParkingSlot parkingSlot : parkingSlots) {
            if (parkingSlot.isSlotOccupied() && parkingSlot.getParkedCarInSlot().getCarRegNumber().equals(carRegNumber)) {
                return parkingSlot;
            }
        }
        return null;
    }

    public void removeCarFromParkingSlot(String carRegNumber) {
        ParkingSlot parkingSlot = findParkingSlotByCar(carRegNumber);
        if (parkingSlot != null) {
            parkingSlot.removeCarFromSlot();
        } else {
            System.out.println("Car not found in any parking slot.");
        }
    }

    public void deleteAllUnoccupiedParkingSlots() {
     long initialSize = parkingSlots.size();  // Save the initial size
    parkingSlots.removeIf(parkingSlot -> !parkingSlot.isSlotOccupied());  // Remove unoccupied slots
    long finalSize = parkingSlots.size();  // Check size after removal

    if (initialSize == finalSize) {
        System.out.println("No unoccupied slots to delete.");
    } else {
        System.out.println("All unoccupied parking slots deleted successfully. " 
            + (initialSize - finalSize) + " slots removed.");
    }
  }
}

