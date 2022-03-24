/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author Zeineb Hamdi
 */
public class Commande {   
    private int id_com;
    private int id_user;
    private Date datecommande;
    private float prixtotal;
     Statu etatcommande;

    public Commande() {
    }

    public Commande(int id_com, int id_user, Date datecommande, Statu etatcommande) {
        this.id_com = id_com;
        this.id_user = id_user;
        this.datecommande = datecommande;
        this.etatcommande = etatcommande;
    }

    public Commande(int id_com, Date datecommande, float prixtotal, Statu etatcommande) {
        this.id_com = id_com;
        this.datecommande = datecommande;
        this.prixtotal = prixtotal;
        this.etatcommande = etatcommande;
    }

    public Commande(Date datecommande, float prixtotal, Statu etatcommande) {
        this.datecommande = datecommande;
        this.prixtotal = prixtotal;
        this.etatcommande = etatcommande;
    }

    public int getId_com() {
        return id_com;
    }

    public void setId_com(int id_com) {
        this.id_com = id_com;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Date getDatecommande() {
        return datecommande;
    }

    public void setDatecommande(Date datecommande) {
        this.datecommande = datecommande;
    }

    public float getPrixtotal() {
        return prixtotal;
    }

    public void setPrixtotal(float prixtotal) {
        this.prixtotal = prixtotal;
    }

    public Statu getEtatcommande() {
        return etatcommande;
    }

    public void setEtatcommande(Statu etatcommande) {
        this.etatcommande = etatcommande;
    }

    @Override
    public String toString() {
        return "Commande{" + "id_com=" + id_com + ", id_user=" + id_user + ", datecommande=" + datecommande + ", prixtotal=" + prixtotal + ", etatcommande=" + etatcommande + '}';
    }
    
    
        public enum Statu{
        EnCours,
        Valider,
        EnAttente
    }
    
}
