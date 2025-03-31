/** ParkingSlot class has the details about the car parked. It handles parking,
 * removing cars and calculating parking duration and fee.
 * @author: Balagopalapracheethaa Senthilkumar
 * @date: 07.09.2024
 * @version 1.0
 */import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParkingSlot {
    private String slotIdentifier;
    private boolean isSlotForStaff;
    private Car parkedCarInSlot;
    private LocalDateTime parkingStartTime; 

    public ParkingSlot(String slotIdentifier, boolean isSlotForStaff) {
        if (!slotIdentifier.matches("^[A-Z][0-9]{2}$")) {
            throw new IllegalArgumentException("Invalid slot ID format. Must be an uppercase letter followed by 2 digits.");
        }
        this.slotIdentifier = slotIdentifier;
        this.isSlotForStaff = isSlotForStaff;
        this.parkedCarInSlot = null;
         this.parkingStartTime = null; 
    }

    public String getSlotIdentifier() {
        return slotIdentifier;
    }

    public boolean isSlotForStaff() {
        return isSlotForStaff;
    }

    public boolean isSlotOccupied() {
        return parkedCarInSlot != null;
    }

    public void parkCarInSlot(Car carToPark) {
        if (isSlotOccupied()) {
            System.out.println("This parking slot is already occupied.");
            return;
        }
        if (carToPark.isCarOwnedByStaff() != isSlotForStaff) {
            System.out.println("The car type doesn't match the slot type.");
            return;
        }
        this.parkedCarInSlot = carToPark;
         this.parkingStartTime = LocalDateTime.now(); 
        System.out.println("Car successfully parked.");
    }

    public void removeCarFromSlot() {
        if (isSlotOccupied()) {
            this.parkedCarInSlot = null;
            this.parkingStartTime = null;
            System.out.println("Car successfully removed.");
        } else {
            System.out.println("No car is currently parked in this slot.");
        }
    }

    public Car getParkedCarInSlot() {
        return parkedCarInSlot;
    }
    public String getParkingDuration() {
        if (!isSlotOccupied() || parkingStartTime == null) {
            return "No car parked.";
        }
        Duration duration = Duration.between(parkingStartTime, LocalDateTime.now());
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%d hours %d minutes %d seconds", hours, minutes, seconds);
    }
        public double calculateParkingFee() {
        if (!isSlotOccupied() || parkingStartTime == null) {
            return 0.0;
        }
        Duration duration = Duration.between(parkingStartTime, LocalDateTime.now());
        long hours = duration.toHours();
        if (duration.toMinutes() % 60 > 0) {
            hours++;  // Round up if there's any remaining time less than an hour
        }
        return hours * 5;  // $5 per hour
    }

    @Override
    public String toString() {
     String status = isSlotOccupied() ? "Occupied by " + parkedCarInSlot + 
            ", Parking Duration: " + getParkingDuration() + 
            ", Fee: $" + calculateParkingFee() : "Empty";
        return "Slot[ID=" + slotIdentifier + ", StaffSlot=" + isSlotForStaff + ", Status=" + status + "]";
    }    
}
