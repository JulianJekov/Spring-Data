import P01_VehicleHierarchy.entities.Vehicle;

//@MappedSuperclass
public abstract class PassengerVehicle extends Vehicle {
    private int numberOfPassengers;

    public PassengerVehicle() {
    }

    public PassengerVehicle(String type, int numberOfPassengers) {
        super(type);
        this.numberOfPassengers = numberOfPassengers;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}
