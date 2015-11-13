package com.ulplanet.trip.bean;

/**
 * Created by makun on 2015/11/9.
 */
public class InfoBo {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String longitude;
    private String latitude;
    private String description;
    private String phoneCol = "-1";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneCol() {
        return phoneCol;
    }

    public void setPhoneCol(String phoneCol) {
        this.phoneCol = phoneCol;
    }
}
