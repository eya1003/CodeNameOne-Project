/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author eyaam
 */
public class Emplacement {
     private int id_emplacement;
     private String type_emplacement, Description;

    public Emplacement() {
    }

    public Emplacement(int id_emplacement, String type_emplacement, String Description) {
        this.id_emplacement = id_emplacement;
        this.type_emplacement = type_emplacement;
        this.Description = Description;
    }

    public Emplacement(String type_emplacement, String Description) {
        this.type_emplacement = type_emplacement;
        this.Description = Description;
    }

    public int getId_emplacement() {
        return id_emplacement;
    }

    public void setId_emplacement(int id_emplacement) {
        this.id_emplacement = id_emplacement;
    }

    public String getType_emplacement() {
        return type_emplacement;
    }

    public void setType_emplacement(String type_emplacement) {
        this.type_emplacement = type_emplacement;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public String toString() {
        return "Emplacement{" + "id_emplacement=" + id_emplacement + ", type_emplacement=" + type_emplacement + ", Description=" + Description + '}';
    }

  
     
}
