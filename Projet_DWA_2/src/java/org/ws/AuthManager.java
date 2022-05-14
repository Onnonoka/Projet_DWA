/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.websocket.Session;
import org.dao.DAO_Joueur;
import org.dao.DAO_LanceOrdre;
import org.dao.DAO_LancerCharge;
import org.dao.DAO_LancerDecharge;
import org.dao.DAO_Partie;
import org.dao.DAO_Resultat;
import org.dao.DAO_ValDe;
import org.donnees.Joueur;
import org.donnees.LanceOrdre;
import org.donnees.LancerCharge;
import org.donnees.LancerDecharge;
import org.donnees.Partie;
import org.donnees.Resultat;
import org.donnees.ValDe;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author fred2
 */
public class AuthManager {
    
    private final Map<Session, Joueur> loggedPlayer;
    
    public AuthManager() {
        loggedPlayer = new HashMap<>();
    }
    
    public Set<Joueur> getPlayers() {
        Set<Joueur> players = new HashSet<>();
        for (Session key: loggedPlayer.keySet()) {
            players.add(loggedPlayer.get(key));
        }
        return players;
    }
    
    public Set<Session> getSessions() {
        return loggedPlayer.keySet();
    }
    
    public boolean isLogged(String username) {
        for (Session key: loggedPlayer.keySet()) {
            Joueur j = loggedPlayer.get(key);
            if (j != null && j.getPseudo().equals(username)) {
                return true;
            }
        }
        return false;
    }
    
    public Session getSession(String username) {
        for (Session key: loggedPlayer.keySet()) {
            Joueur j = loggedPlayer.get(key);
            if (j != null && j.getPseudo().equals(username)) {
                return key;
            }
        }
        return null;
    }
    
    public Joueur getPlayer(String username) {
        for (Session key: loggedPlayer.keySet()) {
            Joueur j = loggedPlayer.get(key);
            if (j != null && j.getPseudo().equals(username)) {
                return j;
            }
        }
        return null;
    }
    
    public void register(Session peer, RequestBuilder request) throws Exception {
        RequestBuilder reply = new RequestBuilder();
        try {
            DAO_Joueur daoJoueur = new DAO_Joueur();
            Joueur player = new Joueur(request.getData().getString("username"));
            player.setMdp(request.getData().getString("password"));
            player.setSexe(request.getData().getString("sex").charAt(0));
            player.setVille(request.getData().getString("city"));
            player.setAge(request.getData().getInt("age"));
            daoJoueur.create(player);
            reply.setData(RequestBuilder.AUTH_REGISTER);
            System.out.println("NEW USER ADDED");
        } catch(Exception e) {
            reply.setData(RequestBuilder.AUTH_USERNAME_ALREADY_USED);
            System.out.println("ERROR ADDING USER, CODE = " + RequestBuilder.AUTH_USERNAME_ALREADY_USED);
        }
        reply.build();
        peer.getBasicRemote().sendText(reply.getMessage());
    }
    
    public void login(Session peer, RequestBuilder request) throws Exception {
        RequestBuilder reply = new RequestBuilder();
        DAO_Joueur daoJoueur = new DAO_Joueur();
        Joueur player = request.getPlayer();
        Joueur j = daoJoueur.find(player.getPseudo());
        if (j != null && j.getMdp().equals(player.getMdp())) {
            loggedPlayer.put(peer, j);
            reply.setData(RequestBuilder.AUTH_LOGIN, j);
            System.out.println("NEW USER LOGGED");
        } else {
            reply.setData(RequestBuilder.AUTH_WRONG_CREDENTIALS);
            System.out.println("ERROR LOGING USER, CODE = " + RequestBuilder.AUTH_WRONG_CREDENTIALS);
        }
        reply.build();
        peer.getBasicRemote().sendText(reply.getMessage());
    }
    
    public void disconnect(Session peer) {
        loggedPlayer.remove(peer);
        System.out.println("USER DISCONNECTED");
    }
    
    public void getUserInfo(Session peer, RequestBuilder request) throws Exception {
        RequestBuilder reply = new RequestBuilder();
        DAO_Joueur daoJoueur = new DAO_Joueur();
        if (loggedPlayer.containsKey(peer)) {
            Joueur j = daoJoueur.find(request.getUsername());
            if (j != null) {
                reply.setData(RequestBuilder.INFO_GET_PROFILE, j);
            } else {
                reply.setData(RequestBuilder.INFO_WRONG_USERNAME);
                System.out.println("ERROR GETTING INFO, CODE = " + RequestBuilder.INFO_WRONG_USERNAME);
            }
        } else {
            reply.setData(RequestBuilder.USER_DISCONNECTED);
            System.out.println("ERROR GETTING INFO, CODE = " + RequestBuilder.USER_DISCONNECTED);
        }
        reply.build();
        peer.getBasicRemote().sendText(reply.getMessage());
    }
    
    public void update(Session peer, RequestBuilder request) throws Exception {
        RequestBuilder reply = new RequestBuilder();
        DAO_Joueur daoJoueur = new DAO_Joueur();
        JSONObject jsonData = new JSONObject();
        if (loggedPlayer.containsKey(peer)) {
            Joueur j = daoJoueur.find(request.getData().getString("username"));
            if (j != null) {
                Integer age = request.getData().getInt("age");
                Character sex = request.getData().getString("sex").charAt(0);
                String ville = request.getData().getString("city");
                try {
                    if (age != null) {
                        j.setAge(age);
                    }                  
                    if (sex != null) {
                        j.setSexe(sex);
                    }
                    if (ville != null){
                        j.setVille(ville);
                    }
                } catch (Exception ex) {
                    System.out.println("looser t'as pas réussi" + ex);
                }
                
            }
            daoJoueur.update(j);
            jsonData.put("username", j.getPseudo());
            jsonData.put("password", j.getMdp());
            jsonData.put("age", j.getAge());
            jsonData.put("ville", j.getVille());
            jsonData.put("sexe", j.getSexe());
            reply.setData(RequestBuilder.AUTH_UPDATE, jsonData );
            request.build();
            peer.getBasicRemote().sendText(request.getMessage());
        }
    }
    
    public void getPlayerHistory(Session peer, RequestBuilder request) throws Exception {
        String username = request.getData().getString("username");
        System.out.println("USERNAME = " + username);
        DAO_Partie daoPartie = new DAO_Partie();
        DAO_Resultat daoResultat = new DAO_Resultat();
        List<Resultat> resultats = daoResultat.getJoueurResultats(username);
        JSONObject jsonData = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        RequestBuilder reply = new RequestBuilder();
        
        System.out.println(resultats.size());
        for (Resultat res : resultats) {
            JSONObject singleData = new JSONObject();
            System.out.println(res.getResultatPK().getCodePartieINT());
            Partie p = daoPartie.find(res.getResultatPK().getCodePartie());
            singleData.put("id", p.getCodePartieINT());
            singleData.put("date", p.getDateDeb());
            singleData.put("nbJetonCharge", res.getNbJetonChargeINT());
            singleData.put("nbJetonDecharge", res.getNbJetonDechargeINT());
            singleData.put("win", res.getNbJetonDechargeINT() == 0);
            jsonArray.put(singleData);
        }
        jsonData.put("username", username);
        jsonData.put("history", jsonArray);
        reply.setData(RequestBuilder.INFO_GET_HISTORY, jsonData);
        reply.build();
        peer.getBasicRemote().sendText(reply.getMessage());
    }
    
    public void getReplay(Session peer, RequestBuilder request) throws Exception {
        DAO_Partie daoPartie = new DAO_Partie();
        DAO_LancerCharge daoLancerCharge = new DAO_LancerCharge();
        DAO_LancerDecharge daoLancerDecharge = new DAO_LancerDecharge();
        DAO_LanceOrdre daoLanceOrdre = new DAO_LanceOrdre();
        DAO_Joueur daoJoueur = new DAO_Joueur();
        DAO_ValDe daoValDe = new DAO_ValDe();
        
        JSONObject jsonData = new JSONObject();
        JSONArray playerList = new JSONArray();
        JSONArray jsonArray = new JSONArray();
        
        RequestBuilder reply = new RequestBuilder();
        
        int id = request.getData().getInt("id");
        List<Joueur> joueurs = daoPartie.getPartieJoueur(id);
        List<LancerCharge> lancerCharge = daoLancerCharge.getPartieLancerCharge(id);
        List<LanceOrdre> lanceOrdre = daoLanceOrdre.getPartieLanceOrdre(id);
        List<LancerDecharge> lancerDecharge = daoLancerDecharge.getPartieLancerDecharge(id);
        
        jsonData.put("id", id);
        for (Joueur j : joueurs) {
            playerList.put(j.getPseudo());
        }
        jsonData.put("Players", playerList);
        
        for(LancerCharge lc : lancerCharge) {
            JSONObject jsonRoll = new JSONObject();
            JSONArray jsonDices = new JSONArray();
            Joueur j = daoJoueur.find(lc.getLancerChargePK().getPseudo());
            ValDe dices = daoValDe.find(lc.getCodeDe().getCodeDe());
            int numLancer = lc.getLancerChargePK().getNumLanceINT();
            
            jsonDices.put(dices.getVal1INT());
            jsonDices.put(dices.getVal2INT());
            jsonDices.put(dices.getVal3INT());
            
            jsonRoll.put("username", j.getPseudo());
            jsonRoll.put("dices", jsonDices);
            jsonRoll.put("number", numLancer);
            
            jsonArray.put(jsonRoll);
        }
        
        for(LanceOrdre lo : lanceOrdre) {
            JSONObject jsonRoll = new JSONObject();
            JSONArray jsonDices = new JSONArray();
            Joueur j = daoJoueur.find(lo.getLanceOrdrePK().getPseudo());
            ValDe dices = daoValDe.find(lo.getCodeDe().getCodeDe());
            //int numLancer = lo.getLanceOrdrePK().getNumLanceINT();
            
            jsonDices.put(dices.getVal1INT());
            jsonDices.put(dices.getVal2INT());
            jsonDices.put(dices.getVal3INT());
            
            jsonRoll.put("username", j.getPseudo());
            jsonRoll.put("dices", jsonDices);
            //jsonRoll.put("number", numLancer);
            
            jsonArray.put(jsonRoll);
        }
        
        for(LancerDecharge ld : lancerDecharge) {
            JSONObject jsonRoll = new JSONObject();
            JSONArray jsonDices = new JSONArray();
            Joueur j = daoJoueur.find(ld.getLancerDechargePK().getPseudo());
            ValDe dices = daoValDe.find(ld.getCodeDe().getCodeDe());
            int numLancer = ld.getLancerDechargePK().getNumLanceINT();
            
            jsonDices.put(dices.getVal1INT());
            jsonDices.put(dices.getVal2INT());
            jsonDices.put(dices.getVal3INT());
            
            jsonRoll.put("username", j.getPseudo());
            jsonRoll.put("dices", jsonDices);
            jsonRoll.put("number", numLancer);
            
            jsonArray.put(jsonRoll);
        }
        jsonData.put("rolls", jsonArray);
        
        reply.setData(RequestBuilder.REPLAY_DATA, jsonData);
        reply.build();
        peer.getBasicRemote().sendText(reply.getMessage());
        
    }
  
}
