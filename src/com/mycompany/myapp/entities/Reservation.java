/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author eyaam
 */
public class Reservation {
    
    private int id_resv , phone_resv;
    private String Email_resv ;
    private String end_resv ,date_resv;

    public Reservation() {
    }

    public Reservation(int id_resv, int phone_resv, String Email_resv, String end_resv, String date_resv) {
        this.id_resv = id_resv;
        this.phone_resv = phone_resv;
        this.Email_resv = Email_resv;
        this.end_resv = end_resv;
        this.date_resv = date_resv;
    }

    public Reservation(int phone_resv, String Email_resv, String end_resv, String date_resv) {
        this.phone_resv = phone_resv;
        this.Email_resv = Email_resv;
        this.end_resv = end_resv;
        this.date_resv = date_resv;
    }

    public Reservation(int phone_resv, String Email_resv) {
        this.phone_resv = phone_resv;
        this.Email_resv = Email_resv;
    }

    public int getId_resv() {
        return id_resv;
    }

    public void setId_resv(int id_resv) {
        this.id_resv = id_resv;
    }

    public int getPhone_resv() {
        return phone_resv;
    }

    public void setPhone_resv(int phone_resv) {
        this.phone_resv = phone_resv;
    }

    public String getEmail_resv() {
        return Email_resv;
    }

    public void setEmail_resv(String Email_resv) {
        this.Email_resv = Email_resv;
    }

    public String getEnd_resv() {
        return end_resv;
    }

    public void setEnd_resv(String end_resv) {
        this.end_resv = end_resv;
    }

    public String getDate_resv() {
        return date_resv;
    }

    public void setDate_resv(String date_resv) {
        this.date_resv = date_resv;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id_resv=" + id_resv + ", phone_resv=" + phone_resv + ", Email_resv=" + Email_resv + ", end_resv=" + end_resv + ", date_resv=" + date_resv + '}';
    }
    
    

  
}
