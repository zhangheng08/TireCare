package com.pi2star.tirecare.entity;

import javax.persistence.*;

@Entity
@Table(name="mpu_msg")
public class MPUMessage {

/*
    U8 ID_OBJ;   //数据盒子的ID
    U8 NUM; //序列号 1~6:表示MPU6500
    //HTTP端使用该序列号，区分MPU6500和胎压的数据包
    U8 year;
    U8 month;
    U8 day;
    U8 hour;
    U8 minute;
    U8 second;
    U8 DATA[960];//包含了80个数据80 * 12B = 960B  */

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int _id;
    private int boxId;
    private int characteristic;
    private String utc_time_day;
    private String utc_time_year;


    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition="BLOB", nullable=true)
    private Float[] acX;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition="BLOB", nullable=true)
    private Float[] acY;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition="BLOB", nullable=true)
    private Float[] acZ;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition="BLOB", nullable=true)
    private Float[] gyX;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition="BLOB", nullable=true)
    private Float[] gyY;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition="BLOB", nullable=true)
    private Float[] gyZ;


    private long timestamp;



    @Override
    public String toString() {

        return "boxId: " + getBoxId() + " characteristic:" + characteristic + " utc_time_day:" + utc_time_day + " utc_time_year:" + utc_time_year;

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

    public String getUtc_time_year() {
        return utc_time_year;
    }

    public void setUtc_time_year(String utc_time_year) {
        this.utc_time_year = utc_time_year;
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

    public Float[] getAcX() {
        return acX;
    }

    public void setAcX(Float[] acX) {
        this.acX = acX;
    }

    public Float[] getAcY() {
        return acY;
    }

    public void setAcY(Float[] acY) {
        this.acY = acY;
    }

    public Float[] getAcZ() {
        return acZ;
    }

    public void setAcZ(Float[] acZ) {
        this.acZ = acZ;
    }

    public Float[] getGyX() {
        return gyX;
    }

    public void setGyX(Float[] gyX) {
        this.gyX = gyX;
    }

    public Float[] getGyY() {
        return gyY;
    }

    public void setGyY(Float[] gyY) {
        this.gyY = gyY;
    }

    public Float[] getGyZ() {
        return gyZ;
    }

    public void setGyZ(Float[] gyZ) {
        this.gyZ = gyZ;
    }
}
