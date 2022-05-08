/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;
import org.dao.DAO_Joueur;
import org.donnees.Joueur;

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
            Joueur player = request.getPlayer();
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
    
    private void update() {
        
    }
  
}
