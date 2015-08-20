package project.test.app.ua.testproject.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by antonina on 28.07.15.
 */
public class Address {

    private String country;
    private String zipCode;
    private String city;
    private String countryCode;
    private String street;
    private String houseNumber;
    private LatLng location;

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return this.houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public LatLng getLocation() {
        return this.location;
    }

    public void setLocation(LatLng loc) {
        this.location = loc;
    }

}
