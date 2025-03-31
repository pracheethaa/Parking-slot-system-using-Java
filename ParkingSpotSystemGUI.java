/** ParkingSpotSystemGUI has the graphical interface how the system will perform
 * @author: Balagopalapracheethaa Senthilkumar
 * @date: 19.10.2024
 * @version 1.0
 */import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ParkingSpotSystemGUI {
    private CarPark carPark;
    private JPanel slotsPanel; // Panel to display the parking slots
    private ArrayList<JLabel> slotLabels; // Labels to represent parking slots
    
    public ParkingSpotSystemGUI() {
        carPark = new CarPark();
        slotLabels = new ArrayList<>();
        initializeGUI();
    }

    public static void main(String[] args) {
        ToolTipManager.sharedInstance().setInitialDelay(0);
        new ParkingSpotSystemGUI();
    }

    private void initializeGUI() {
        JFrame frame = new JFrame("Parking Spot System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500); // Increased the size for better visibility

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1));  // Modified to have 7 buttons (new buttons added)

        JButton addSlotButton = new JButton("Add Parking Slot");
        JButton parkCarButton = new JButton("Park Car");
        JButton removeCarButton = new JButton("Remove Car from Slot");
        JButton findCarButton = new JButton("Find Car by Registration");
        JButton removeByRegButton = new JButton("Remove Car by Registration");
        JButton deleteUnoccupiedSlotsButton = new JButton("Delete Unoccupied Slots");
        JButton deleteSlotButton = new JButton("Delete Parking Slot");
        JButton exitButton = new JButton("Exit"); // Exit button
        
        // Adding buttons to the panel
        panel.add(addSlotButton);
        panel.add(parkCarButton);
        panel.add(removeCarButton);
        panel.add(findCarButton); // New Button to Find Car
        panel.add(removeByRegButton); // New Button to Remove Car by Registration
        panel.add(deleteUnoccupiedSlotsButton);
        panel.add(deleteSlotButton);
        panel.add(exitButton); 

        // Add action listeners to buttons
        addSlotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addParkingSlot();
            }
        });

        parkCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parkCarInSlot();
            }
        });

        removeCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCarFromSlot();
            }
        });
                findCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findCarByRegNumber();
            }
        });

        removeByRegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCarByRegNumber();
            }
        });

        deleteUnoccupiedSlotsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carPark.deleteAllUnoccupiedParkingSlots();
                updateSlotsDisplay(); // Update display after deletion
                JOptionPane.showMessageDialog(frame, "Unoccupied slots deleted.");
            }
            
        
        });
        
        // Add action listener for the new delete slot button
deleteSlotButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        deleteParkingSlot();
    }
});

 // Action Listener for Exit button
    exitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, "Program End!");  // Show exit message
            System.exit(0); // Exit the program
        }
    });
        
        // Legend for slot colors
    JPanel legendPanel = new JPanel();
    legendPanel.setLayout(new GridLayout(1, 2));

    // Staff slot legend
    JLabel staffLegend = new JLabel("Staff Slot");
    staffLegend.setOpaque(true);
    staffLegend.setBackground(new Color(173, 216, 230)); // Mild Blue
    staffLegend.setHorizontalAlignment(JLabel.CENTER);

    // Visitor slot legend
    JLabel visitorLegend = new JLabel("Visitor Slot");
    visitorLegend.setOpaque(true);
    visitorLegend.setBackground(new Color(255, 255, 224)); // Mild Yellow
    visitorLegend.setHorizontalAlignment(JLabel.CENTER);

    legendPanel.add(staffLegend);
    legendPanel.add(visitorLegend);

        // Panel to display parking slots as rectangles
        slotsPanel = new JPanel();
        slotsPanel.setLayout(new GridLayout(0, 5)); // Grid layout for displaying slots

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.WEST); // Button panel on the left
        frame.add(slotsPanel, BorderLayout.CENTER); // Slot panel in the center

        frame.setVisible(true);

        // Ask for initial number of staff and visitor slots
        initializeParkingLot();
        updateSlotsDisplay();
        // Use a Swing Timer to update the display every second
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateSlotsDisplay(); // Refresh the display every second
        }
    });
    timer.start(); // Start the timer
    }

    // Initialize parking lot with input from user
    private void initializeParkingLot() {
        int staffSlotCount = Integer.parseInt(JOptionPane.showInputDialog("Enter number of staff slots:"));
        int visitorSlotCount = Integer.parseInt(JOptionPane.showInputDialog("Enter number of visitor slots:"));

        for (int i = 1; i <= staffSlotCount; i++) {
            String slotID = String.format("A%02d", i);
            carPark.addParkingSlot(new ParkingSlot(slotID, true));
        }

        for (int i = 1; i <= visitorSlotCount; i++) {
            String slotID = String.format("B%02d", i);
            carPark.addParkingSlot(new ParkingSlot(slotID, false));
        }
    }
       
     // Method to add a parking slot
    private void addParkingSlot() {
        String slotID = JOptionPane.showInputDialog("Enter Slot ID (format: A01/B01):A-Staff,B-Visitor");
        if (slotID != null && slotID.matches("^[A-Z][0-9]{2}$")) {
             // Check if slot already exists new
        if (carPark.doesSlotExist(slotID)) {
            JOptionPane.showMessageDialog(null, "Parking slot with ID " + slotID + " already exists!");
        } else { //new
            boolean isStaffSlot = JOptionPane.showConfirmDialog(null, "Is this slot for staff?", "Slot Type", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
            ParkingSlot newSlot = new ParkingSlot(slotID, isStaffSlot);
            carPark.addParkingSlot(newSlot);
            updateSlotsDisplay();
            JOptionPane.showMessageDialog(null, "Parking slot added successfully.");
        } // new
    } else {
            JOptionPane.showMessageDialog(null, "Invalid slot format! Please try again.");
        }
    }

    // Method to park a car
    private void parkCarInSlot() {
        String slotID = JOptionPane.showInputDialog("Enter Slot ID:");
        ParkingSlot selectedSlot = carPark.getParkingSlotByID(slotID);

        if (selectedSlot != null) {
            if (!selectedSlot.isSlotOccupied()) {
                String regNumber = JOptionPane.showInputDialog("Enter Car Registration Number (format: A1234):");
                String ownerName = JOptionPane.showInputDialog("Enter Owner's Name:");
                boolean isStaffCar = JOptionPane.showConfirmDialog(null, "Is this car owned by staff?", "Car Type", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                try {
                    // Check if the car is already parked in another slot
                ParkingSlot existingSlot = carPark.findParkingSlotByCar(regNumber);
                if (existingSlot != null) {
                    JOptionPane.showMessageDialog(null, "Car with registration " + regNumber + " is already parked in slot " + existingSlot.getSlotIdentifier() + ".");
                } else {
                    Car carToPark = new Car(regNumber, ownerName, isStaffCar);
                    if (isStaffCar == selectedSlot.isSlotForStaff()) {
                        selectedSlot.parkCarInSlot(carToPark);
                        updateSlotsDisplay();
                        JOptionPane.showMessageDialog(null, "Car parked successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Slot type mismatch! Cannot park the car.");
                    }
                }
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "The slot is already occupied.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Slot not found.");
        }
    }

    // Method to remove a car from a slot
    private void removeCarFromSlot() {
        String carRegNumber = JOptionPane.showInputDialog("Enter Car Registration Number:");
        if (carRegNumber != null) {
            carPark.removeCarFromParkingSlot(carRegNumber);
            updateSlotsDisplay();
            JOptionPane.showMessageDialog(null, "Car removed from slot.");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input.");
        }
    }
       // Method to find a car by registration number and show parking details
    private void findCarByRegNumber() {
        String carRegNumber = JOptionPane.showInputDialog("Enter Car Registration Number:");
        if (carRegNumber != null) {
            ParkingSlot slot = carPark.findParkingSlotByCar(carRegNumber);
            if (slot != null) {
                Car car = slot.getParkedCarInSlot();
                String details = "Car found in Slot: " + slot.getSlotIdentifier() +
                        "\nOwner: " + car.getCarOwner() +
                        "\nTime Parked: " + slot.getParkingDuration() +
                        "\nCurrent Fare: $" + slot.calculateParkingFee();
                JOptionPane.showMessageDialog(null, details);
            } else {
                JOptionPane.showMessageDialog(null, "Car not found.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input.");
        }
    }
    

    // Method to remove a car by registration number
    private void removeCarByRegNumber() {
        String carRegNumber = JOptionPane.showInputDialog("Enter Car Registration Number:");
        if (carRegNumber != null) {
            ParkingSlot slot = carPark.findParkingSlotByCar(carRegNumber);
            if (slot != null) {
                carPark.removeCarFromParkingSlot(carRegNumber);
                updateSlotsDisplay();
                JOptionPane.showMessageDialog(null, "Car with registration " + carRegNumber + " removed from Slot: " + slot.getSlotIdentifier());
            } else {
                JOptionPane.showMessageDialog(null, "Car not found.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input.");
        }
    }
    
    // Method to delete a parking slot if unoccupied
private void deleteParkingSlot() {
    String slotID = JOptionPane.showInputDialog("Enter Slot ID to delete:");
    
    if (slotID != null && !slotID.isEmpty()) {
        ParkingSlot selectedSlot = carPark.getParkingSlotByID(slotID);
        
        if (selectedSlot != null) {
            if (!selectedSlot.isSlotOccupied()) {
                carPark.deleteParkingSlot(slotID);
                updateSlotsDisplay();
                JOptionPane.showMessageDialog(null, "Slot " + slotID + " deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Slot " + slotID + " is currently in use. Cannot delete.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Slot " + slotID + " not found.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid slot ID.");
    }
}


    // Method to update the display of parking slots
    private void updateSlotsDisplay() {
        slotsPanel.removeAll();
        slotLabels.clear();
        
         // Loop through parking slots and display them
        for (ParkingSlot slot : carPark.getParkingSlots()) {
            JLabel slotLabel = new JLabel(slot.getSlotIdentifier(), JLabel.CENTER);
            slotLabel.setOpaque(true);

            // Assign color based on staff or visitor slot
            if (slot.isSlotForStaff()) {
                slotLabel.setBackground(new Color(173, 216, 230)); // Staff slot (blue)
            } else {
                slotLabel.setBackground(new Color(230, 230, 250)); // Visitor slot (yellow)
            }
            if (slot.isSlotOccupied()) {
    slotLabel.setBackground(Color.RED); // Occupied slot (red)
    slotLabel.setText("<html>" + slot.getSlotIdentifier() + " (Occupied)<br/>" +
    "Time Parked: " + slot.getParkingDuration() + "<br/>" +
            "Fare: $" + slot.calculateParkingFee() + "</html>");
}
// Set tooltip with details about the slot
            if (slot.isSlotOccupied()) {
                // Tooltip for occupied slots
                String tooltipText = "<html>Slot: " + slot.getSlotIdentifier() + "<br/>" +
                        "Occupied by: " + slot.getParkedCarInSlot().getCarRegNumber() + "<br/>" +
                        "Owner: " + slot.getParkedCarInSlot().getCarOwner() + "<br/>" +
                        "Time Parked: " + slot.getParkingDuration() + "<br/>" +
                        "Current Fare: $" + slot.calculateParkingFee() + "</html>";
                slotLabel.setToolTipText(tooltipText);
            } else {
                // Tooltip for empty slots
                slotLabel.setToolTipText("Slot: " + slot.getSlotIdentifier() + " (Empty)");
            }
            
            
        // Add mouse listener for interaction
        slotLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Show options when a slot is clicked
                String[] options = {"Park a Car", "Remove Car"};
                int choice = JOptionPane.showOptionDialog(null, 
                        "Choose an action for slot " + slot.getSlotIdentifier(), 
                        "Slot Action", 
                        JOptionPane.DEFAULT_OPTION, 
                        JOptionPane.INFORMATION_MESSAGE, 
                        null, options, options[0]);

                // Handle the user's choice
                if (choice == 0) { // "Park a Car"
                    parkCarInSlot();
                } else if (choice == 1) { // "Remove Car"
                    removeCarFromSlot();
                }
            }
        });
            slotLabels.add(slotLabel);
            slotsPanel.add(slotLabel);
        }

        slotsPanel.revalidate();
        slotsPanel.repaint();
    }
}

