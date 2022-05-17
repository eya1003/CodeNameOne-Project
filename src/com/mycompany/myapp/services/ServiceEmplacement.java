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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Emplacement;

import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author SIHEM
 */
public class ServiceEmplacement {

    public ArrayList<Emplacement> emp;
   
    public static ServiceEmplacement instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceEmplacement() {
         req = new ConnectionRequest();
    }

    public static ServiceEmplacement getInstance() {
        if (instance == null) {
            instance = new ServiceEmplacement();
        }
        return instance;
    }
   


 public ArrayList<Emplacement> parsePanier(String jsonText){
                try {
                    char firstChar = jsonText.charAt(0);
                    if(firstChar != '[')
                    {
                    jsonText="["+jsonText+"]";
                    }
                    System.out.println(jsonText);
            emp =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Emplacement a = new Emplacement();
                float id = Float.parseFloat(obj.get("id_emplacement").toString());
                a.setId_emplacement((int)id);
                a.setType_emplacement(obj.get("type_emplacement").toString());
         a.setDescription(obj.get("Description").toString());
                emp.add(a);

            }
        } catch (IOException ex) {
            
        }
        return emp;
    }
 
 
    public ArrayList<Emplacement> getAllEmplacement(){
        String url = Statics.BASE_URL+"mobile/api/afficheEmp";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                emp = parsePanier(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return emp;
    }

//ajout 
    public void ajoutEmplacement(Emplacement a) {
        
        String url =Statics.BASE_URL+"mobile/addEmpJSON?type_emplacement="+a.getType_emplacement()+"&Description="+a.getDescription(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    

        public boolean editPanier(Emplacement u) {
        String url = Statics.BASE_URL + "mobile/emplacement/updateEmplacement/"+u.getId_emplacement()+
                "?type_emplacement="+u.getType_emplacement() +"&Description="+u.getDescription();
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

    public boolean deletePanier(Emplacement fi) {
        String url = Statics.BASE_URL + "mobile/supprimerEmp/" + fi.getId_emplacement();
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

}
