package com.pi2star.tirecare.entity;

import javax.persistence.*;

@Entity
public class BytesInHex {

    @Id
    private int characteristic;
    @Column(columnDefinition="TEXT", nullable=true)
    private String hexString;


/*    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }*/

    public String getHexString() {
        return hexString;
    }

    public void setHexString(String hexString) {
        this.hexString = hexString;
    }

    public int getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(int characteristic) {
        this.characteristic = characteristic;
    }
}
