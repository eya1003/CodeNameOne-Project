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
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Emplacement;
import com.mycompany.myapp.services.ServiceEmplacement;
import java.util.ArrayList;
/**
 *
 * @author eyaam
 */
public class ListEmpForm extends BaseForm{
     Form current;
    public ListEmpForm(Resources res) {
                 super("Newsfeed", BoxLayout.y());
           //search
              Toolbar.setGlobalToolbar(true);
             add(new InfiniteProgress());
             
             
                Display.getInstance().scheduleBackgroundTask(()-> {
                    // this will take a while...
                    Display.getInstance().callSerially(() -> {
                    removeAll();
                    
                    ArrayList <Emplacement> paniers = new ArrayList();
                        ServiceEmplacement sa =new ServiceEmplacement();
                    paniers=sa.getAllEmplacement();
                     /*   Button btnadd = new Button("Ajouter");
                     add(btnadd);
                     btnadd.addActionListener((evt) -> {
                         new AjouterEmplacement(res).show();
                    });*/
                             for (Emplacement fi : paniers) {
                            MultiButton m = new MultiButton();
                            m.setTextLine1("Vue : "+String.valueOf(fi.getType_emplacement()));
                            m.setTextLine2("Description :"+String.valueOf(fi.getDescription()));
                           
                            m.addLongPressListener(new ActionListener() {
                                            @Override
            public void actionPerformed(ActionEvent evt) {              
               if (Dialog.show("Confirmation", "Voulez vous Ajouter une Emplacement?", "Oui", "Non")) {
                                   new AjouterEmplacement(res,current,fi).show();
                                       }
               
                     if (Dialog.show("Confirmation", "Voulez vous Modifier cette Emplacement?", "Oui", "Non")) {
                                   new ModifierEmp(res,current,fi).show();
                                       }

                     else {
                          if (Dialog.show("Confirmation", "Voulez vous Supprimer cette Emplacement ?", "Supprimer", "Annuler")) {
                        if( ServiceEmplacement.getInstance().deletePanier(fi)){
                            {
                                   Dialog.show("Success","supprimer",new Command("OK"));
                                   new ListEmpForm(res).show();
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
            boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
            line2 != null && line2.toLowerCase().indexOf(text) > -1 ;
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