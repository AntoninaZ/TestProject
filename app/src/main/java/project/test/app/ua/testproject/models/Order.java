package project.test.app.ua.testproject.models;

/**
 * Created by antonina on 28.07.15.
 */
public class Order {
    private Address departureAddress;
    private Address destinationAddress;

    public Address getDepartureAddress() {
        return this.departureAddress;
    }

    public void setDepartureAddress(Address departureAddress) {
        this.departureAddress = departureAddress;
    }

    public Address getDestinationAddress() {
        return this.destinationAddress;
    }

    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
}
