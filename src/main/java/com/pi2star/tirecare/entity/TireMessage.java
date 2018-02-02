package com.pi2star.tirecare.entity;

import javax.persistence.*;

@Entity
@Table(name="tire_msg")
public class TireMessage {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int _id;
    private int boxId;
    private int characteristic;
    private int idh;
    private int idl;
    private int place;

    private float pressure;

    private float temperature;

    private float accelerate;

    private float voltage;

    private String utc_time_year;
    private String utc_time_day;
    private long timestamp;

    @Override
    public String toString() {

        return "idh:" + idh + " idl:" + idl + " place:" + place +
                " pressure:" +  pressure + "-"  +
                " temperature:" + temperature + "-" +
                " accelerate:" + accelerate + "-" +
                " voltage:" + voltage + "-" +
                " utc_time_year:" + utc_time_year + " utc_time_day:" + utc_time_day;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public int getIdh() {
        return idh;
    }

    public void setIdh(int idh) {
        this.idh = idh;
    }

    public int getIdl() {
        return idl;
    }

    public void setIdl(int idl) {
        this.idl = idl;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getAccelerate() {
        return accelerate;
    }

    public void setAccelerate(float accelerate) {
        this.accelerate = accelerate;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public String getUtc_time_year() {
        return utc_time_year;
    }

    public void setUtc_time_year(String utc_time_year) {
        this.utc_time_year = utc_time_year;
    }

    public String getUtc_time_day() {
        return utc_time_day;
    }

    public void setUtc_time_day(String utc_time_day) {
        this.utc_time_day = utc_time_day;
    }

    public void setTimestamp(long timestamp) {

        this.timestamp = timestamp;

    }

    public long getTimestamp() {

        return timestamp;

    }

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    public int getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(int characteristic) {
        this.characteristic = characteristic;
    }

}
