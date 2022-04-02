/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import com.codename1.ui.TextField;

/**
 *
 * @author eyaam
 */
public class Table {
    private int id_tab, stock_tab,nb_chaise_tab;
    private String emp;

    public Table() {
    }

    public Table(int id_tab, int stock_tab, int nb_chaise_tab, String emp) {
        this.id_tab = id_tab;
        this.stock_tab = stock_tab;
        this.nb_chaise_tab = nb_chaise_tab;
        this.emp = emp;
    }
      public Table( String emp, int nb_chaise_tab,int stock_tab) {
        this.emp = emp;
        this.nb_chaise_tab = nb_chaise_tab;
          this.stock_tab = stock_tab;
        
        
    }

    public Table(int stock_tab, int nb_chaise_tab, String emp) {
        this.stock_tab = stock_tab;
        this.nb_chaise_tab = nb_chaise_tab;
        this.emp = emp;
    }

    public Table(int stock_tab, int nb_chaise_tab) {
        this.stock_tab = stock_tab;
        this.nb_chaise_tab = nb_chaise_tab;
    }
  
    public int getId_tab() {
        return id_tab;
    }

    public void setId_tab(int id_tab) {
        this.id_tab = id_tab;
    }

    public int getStock_tab() {
        return stock_tab;
    }

    public void setStock_tab(int stock_tab) {
        this.stock_tab = stock_tab;
    }

    public int getNb_chaise_tab() {
        return nb_chaise_tab;
    }

    public void setNb_chaise_tab(int nb_chaise_tab) {
        this.nb_chaise_tab = nb_chaise_tab;
    }

    public String getEmp() {
        return emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    @Override
    public String toString() {
        return "Table{" + "id_tab=" + id_tab + ", stock_tab=" + stock_tab + ", nb_chaise_tab=" + nb_chaise_tab + ", emp=" + emp + '}';
    }

   
   
    
}
