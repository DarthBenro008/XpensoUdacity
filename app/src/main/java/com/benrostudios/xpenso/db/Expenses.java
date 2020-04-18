package com.benrostudios.xpenso.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;


@IgnoreExtraProperties
@Entity(tableName = "expenses")

public class Expenses implements Serializable {


    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    @ColumnInfo(name = "person")
    private String person;
    @ColumnInfo(name = "amount")
    private double amount;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "paymentMode")
    private String paymentMode;

    @PrimaryKey
    @ColumnInfo(name = "time")
    private Date time;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @ColumnInfo(name = "desc")
    private String desc;

    public Expenses() {

    }

    public Expenses(String person, Double amount, String type, String paymentMode, Date time, String desc) {
        this.person = person;
        this.amount = amount;
        this.type = type;
        this.paymentMode = paymentMode;
        this.time = time;
        this.desc = desc;
    }

}


