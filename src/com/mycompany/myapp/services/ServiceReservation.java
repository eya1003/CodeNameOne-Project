/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Reservation;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author eyaam
 */
public class ServiceReservation {
      /*
    public ArrayList<Reservation> table;
   
    public static ServiceTable instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceReservation() {
         req = new ConnectionRequest();
    }

    public static ServiceTable getInstance() {
        if (instance == null) {
            instance = new ServiceTable();
        }
        return instance;
    }
   


 public ArrayList<Reservation> parseTable(String jsonText){
                try {
                    char firstChar = jsonText.charAt(0);
                    if(firstChar != '[')
                    {
                    jsonText="["+jsonText+"]";
                    }
                    System.out.println(jsonText);
            table =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Reservation a = new Reservation();
                float id = Float.parseFloat(obj.get("id_tab").toString());
                a.setId_resv((int)id);
                float nbChaise = Float.parseFloat(obj.get("nb_chaise_tab").toString());
                a.setNb_chaise_tab((int)nbChaise);
                
                String test = obj.get("emp").toString();
                System.out.println(test);
               a.setEmp(test.substring((test).indexOf("type_emplacement=")+17 ,(test).indexOf(", Description=")));
              
               
                float stock = Float.parseFloat(obj.get("stock_tab").toString());
                a.setStock_tab((int)stock);
                table.add(a);
              //  System.out.println(n);

            }
        } catch (IOException ex) {
            
        }
        return table;
    }
 
 
    public ArrayList<Table> getAllTable(){
        String url = Statics.BASE_URL+"mobile/affTable";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                table = parseTable(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return table;
    }

/*

    public boolean addPanier(Panier u) {
        String url = Statics.BASE_URL + "paniermobile/new?id_produit="+u.getId_prod(); //création de l'URL
               req.setUrl(url);
               System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }*/
/*
        public boolean editTable(Table u) {
        String url = Statics.BASE_URL + "table/updateTable/"+u.getId_tab()+"?nb_chaise_tab="+u.getNb_chaise_tab() +" &stock_tab="+u.getStock_tab();
               req.setUrl(url);
               System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean deleteTable(Table fi) {
        String url = Statics.BASE_URL + "table/supprimer/" + fi.getId_tab();
               req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    */
}
