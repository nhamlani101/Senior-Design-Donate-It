package com.noman.donateit.data;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseGeoPoint;

import java.util.List;

/**
 * Created by noman on 4/18/17.
 */

public class SiteModel {

    private String organization;
    private String address;
    private String city;
    private String state;
    private String zip;
    private LatLng location;
    private boolean flagged;
    private List<Integer> categories;

    public SiteModel(String organization, String address, String city, String state, String zip, LatLng location, boolean flagged, List<Integer> categories) {
        this.organization = organization;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.location = location;
        this.flagged = flagged;
        this.categories = categories;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }
}
