package com.pi2star.tirecare.entity;


import javax.persistence.*;

@Entity
@Table(name="gps_msg")
public class GPSMessage {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int _id;
    private int boxId;                 //数据盒子ID                       8B
    private int characteristic;
    private char gpssta;	                // 定位状态 A 有效定位   V 无效定位  1B

    //@Column(columnDefinition="FLOAT(9,6)", nullable=true)
    private float latitude;			//纬度                            4B
    private int nshemi;					//北纬南纬                         1B

    //@Column(columnDefinition="FLOAT(9,6)", nullable=true)
    private float longitude;			//经度			                  4B
    private int ewhemi;					//东经西经                         1B
    private int speed;	                //地面速率                         2B
    private String utc_time_day;           // UTC_TIME_DAY 时间 day          3B
    private String utc_time_year;          //UTC_TIME_YEAR时间 year          3B
    private long timestamp;

    @Override
    public String toString() {

        return "gpssta:" + gpssta + " lat:" + latitude + " nshemi:" + nshemi + " long:" + longitude + " ewhemi:" + ewhemi + " speed:" + speed +
                " utc_time_year:" + utc_time_year +
                " utc_time_day:" + utc_time_day;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUtc_time_day() {
        return utc_time_day;
    }

    public void setUtc_time_day(String utc_time_day) {
        this.utc_time_day = utc_time_day;
    }

    public char getGpssta() {
        return gpssta;
    }

    public void setGpssta(char gpssta) {
        this.gpssta = gpssta;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public int getNshemi() {
        return nshemi;
    }

    public void setNshemi(int nshemi) {
        this.nshemi = nshemi;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getEwhemi() {
        return ewhemi;
    }

    public void setEwhemi(int ewhemi) {
        this.ewhemi = ewhemi;
    }

    public String getUtc_time_year() {
        return utc_time_year;
    }

    public void setUtc_time_year(String utc_time_year) {
        this.utc_time_year = utc_time_year;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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
