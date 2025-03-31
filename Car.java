/**The Car class represents a car with a registration number, owner, and whether
 * the owner is a staff member. This class provides methods to retrieve details about 
 * the car and a formatted string representation of the car's information.
 * @author: Balagopalapracheethaa Senthilkumar
 * @date: 07.09.2024
 * @version 1.0
 */
public class Car {
    private String carRegNumber;
    private String carOwner;
    private boolean isCarOwnedByStaff;

    public Car(String carRegNumber, String carOwner, boolean isCarOwnedByStaff) {
        if (!carRegNumber.matches("^[A-Z][0-9]{4}$")) {
            throw new IllegalArgumentException("Invalid registration number format. Must be an uppercase letter followed by 4 digits.");
        }
        this.carRegNumber = carRegNumber;
        this.carOwner = carOwner;
        this.isCarOwnedByStaff = isCarOwnedByStaff;
    }

    public String getCarRegNumber() {
        return carRegNumber;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public boolean isCarOwnedByStaff() {
        return isCarOwnedByStaff;
    }

    @Override
    public String toString() {
        return "Car[regNumber=" + carRegNumber + ", owner=" + carOwner + ", staff=" + isCarOwnedByStaff + "]";
    }
}
