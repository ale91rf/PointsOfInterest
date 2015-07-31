package com.alefernandez.pointsofinterest.models;

import android.provider.Telephony;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by alejandro on 30/07/2015.
 */
public class PointOfInterest {

    public int id;
    public String title;
    public String address;
    public String transport;
    public String email;
    public String geocoordinates;
    public String description;
    public String phone;

    public LatLng geocoordinatesReal;

    public PointOfInterest(){};

    public PointOfInterest(int id, String title, String address, String transport, String email, String geocoordinates, String description, String phone) {
        this.id = id;
        this.title = title;
        this.geocoordinates = geocoordinates;
        this.address = address;
        this.transport = transport;
        this.email = email;
        this.description = description;
        this.phone = phone;

        geocoordinatesReal = createCoord(this.geocoordinates);
    }

    private LatLng createCoord(String stringGeo) {

        String latLonSeparate[] = stringGeo.split(",");
        LatLng mGeoCoor = new LatLng(Double.parseDouble(latLonSeparate[0]), Double.parseDouble(latLonSeparate[1]));

        return mGeoCoor;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getGeocoordinatesReal() {
        return geocoordinatesReal;
    }

    public void setGeocoordinatesReal(LatLng geocoordinatesReal) {
        this.geocoordinatesReal = geocoordinatesReal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGeocoordinates() {
        return geocoordinates;
    }

    public void setGeocoordinates(String geocoordinates) {
        this.geocoordinates = geocoordinates;
        geocoordinatesReal = createCoord(this.geocoordinates);
    }
}
