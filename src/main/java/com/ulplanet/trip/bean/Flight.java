package com.ulplanet.trip.bean;



/**
 * Created by Administrator on 2015/10/28.
 */
public class Flight {
    private String id;
    private String city;
    private String flightNo;
    private String company;
    private String departureTerminal;
    private String arrivalTerminal;
    private String departureTime;
    private String arrivalTime;
    private String departureCity;
    private String arrivalCity;
    private String common;
    private String departure;
    private String arrival;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartureTerminal() {
        return departureTerminal;
    }

    public void setDepartureTerminal(String departureTerminal) {
        this.departureTerminal = departureTerminal;
    }

    public String getArrivalTerminal() {
        return arrivalTerminal;
    }

    public void setArrivalTerminal(String arrivalTerminal) {
        this.arrivalTerminal = arrivalTerminal;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {

        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("航班起飞时间："+this.departureTime);
        sb.append(",航班起飞地点："+this.departureTerminal+":"+this.departureCity);
        sb.append(",航班到达时间："+this.arrivalTime);
        sb.append(",航班到达地点："+this.arrivalTerminal+":"+this.arrivalCity);
        return sb.toString();
    }
}
