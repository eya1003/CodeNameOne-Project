/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Reservation;
import com.mycompany.myapp.services.ServiceReservation;
import java.util.ArrayList;

/**
 *
 * @author eyaam
 */
public class ListReservation extends BaseForm{
     Form current;
     
       
    public ListReservation(Resources res) {

         super("Newsfeed", BoxLayout.y());
        //search
             Toolbar.setGlobalToolbar(true);
             add(new InfiniteProgress());
             
             
                Display.getInstance().scheduleBackgroundTask(()-> {
                    // this will take a while...
                    Display.getInstance().callSerially(() -> {
                    removeAll();
                    ArrayList <Reservation> paniers = new ArrayList();
                        ServiceReservation sa =new ServiceReservation();
                    paniers=sa.getAllTable();
                    
                       
                             for (Reservation fi : paniers) {
                            MultiButton m = new MultiButton();
                            
                           m.setTextLine1(" Phone: "+String.valueOf(fi.getPhone_resv()));
                          m.setTextLine2("Email :"+String.valueOf(fi.getEmail_resv()));
                          m.setTextLine3(" Date : "+String.valueOf(fi.getDate_resv()));
                        
                            m.addLongPressListener(new ActionListener() {
                                            @Override
            public void actionPerformed(ActionEvent evt) {    
                
                  if (Dialog.show("Confirmation", "Voulez vous Ajouter une RESERVATION?", "Oui", "Non")) {
                                   new ReservationForm(res,current,fi).show();
                                       }
                  else {
                     if (Dialog.show("Confirmation", "Voulez vous Modifier cette RESERVATION?", "Oui", "Non")) {
                                   new ModifierReservation(res,current,fi).show();
                                       }

                     else {
                          if (Dialog.show("Confirmation", "Voulez vous Supprimer cette RESERVATION ?", "Supprimer", "Annuler")) {
                        if( ServiceReservation.getInstance().deleteReservation(fi)){
                            {
                                   Dialog.show("Success","supprimer",new Command("OK"));
                                   new ListReservation(res).show();
                            }
                   
                }
                      
                          }
                }
            }
            }
        });

                            add(m);
                             }
                     revalidate();
                       
                    });
                   
                });
               
    getToolbar().addSearchCommand( e -> {
    String text = (String)e.getSource();
    if(text == null || text.length() == 0) {
        // clear search
        for(Component cmp : getContentPane()) {
            cmp.setHidden(false);
            cmp.setVisible(true);
        }
        getContentPane().animateLayout(150);
    } else {
        text = text.toLowerCase();
        for(Component cmp : getContentPane()) {
            MultiButton mb = (MultiButton)cmp;
            String line1 = mb.getTextLine1();
            String line2 = mb.getTextLine2();
            String line3 = mb.getTextLine3();
            boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
            line2 != null && line2.toLowerCase().indexOf(text) > -1 ||
            line3 != null && line3.toLowerCase().indexOf(text) > -1 ;
            mb.setHidden(!show);
            mb.setVisible(show);
        }
        getContentPane().animateLayout(150);
    }
}, 4);

   
        getToolbar().addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("Menu", FontImage.MATERIAL_UPDATE, e -> new ListEmpForm(res).show());
      //  getToolbar().addMaterialCommandToSideMenu("Panier", FontImage.MATERIAL_SHOPPING_CART, e -> new MonPanier(res).show());
      getToolbar().addMaterialCommandToSideMenu("Panier", FontImage.MATERIAL_UPDATE, e -> new NewsfeedForm(res).show());
       getToolbar().addMaterialCommandToSideMenu("Evenement", FontImage.MATERIAL_UPDATE, e -> new NewsfeedForm(res).show());
    
     getToolbar().addMaterialCommandToSideMenu("Emplacement", FontImage.MATERIAL_UPDATE, e -> new ListEmpForm(res));
    
      getToolbar().addMaterialCommandToSideMenu("Table", FontImage.MATERIAL_UPDATE, e -> new ListTable(res).show());
     getToolbar().addMaterialCommandToSideMenu("Reservation", FontImage.MATERIAL_UPDATE, e -> new ListReservation(res).show());
     //getToolbar().addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> new WalkthruForm(res).show())
    
    }
}
