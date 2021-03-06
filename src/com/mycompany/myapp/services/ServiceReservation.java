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
    
     public ArrayList<Reservation> reservation;
   
    public static ServiceReservation instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceReservation() {
         req = new ConnectionRequest();
    }

    public static ServiceReservation getInstance() {
        if (instance == null) {
            instance = new ServiceReservation();
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
            reservation =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                 Reservation re = new Reservation();
                
                   float id = Float.parseFloat(obj.get("id_resv").toString());
                   
                   float phone = Float.parseFloat(obj.get("phone_resv").toString());
                   String email = obj.get("Email_resv").toString();
                    
                        re.setId_resv((int)id);
                        re.setPhone_resv((int)phone);
                        re.setEmail_resv(email);
                       
                   
                        
                    String dateDebut = obj.get("date_resv").toString();
                    String dateFin = obj.get("end_resv").toString();
                    
                             re.setDate_resv(dateDebut);
                                re.setEnd_resv(dateFin);

                 
                reservation.add(re);
              //  System.out.println(n);

            }
        } catch (IOException ex) {
            
        }
        return reservation;
    }
 
 
    public ArrayList<Reservation> getAllTable(){
        String url = Statics.BASE_URL+"mobile/AllResv";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reservation = parseTable(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return reservation;
    }

    public boolean deleteReservation(Reservation fi) {
        String url = Statics.BASE_URL + "mobile/supprimerResv/" + fi.getId_resv();
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
    
    //ajout 
    
    public void ajoutReservation(Reservation a) {
        
        String url =Statics.BASE_URL+"mobile/addResvJSON?phone_resv="+a.getPhone_resv()+"&Email_resv="+a.getEmail_resv(); 
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    
    public boolean editReservation(Reservation u) {
        String url = Statics.BASE_URL + "mobile/reserupdate/"+u.getId_resv()+
                "?phone_resv="+u.getPhone_resv()+"&Email_resv="+u.getEmail_resv();
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
}
