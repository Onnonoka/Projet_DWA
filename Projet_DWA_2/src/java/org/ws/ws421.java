/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.donnees.Joueur;

/**
 *
 * @author fred2
 */
@ServerEndpoint("/ws421")
public class ws421 {
    private static final AuthManager authManager = new AuthManager();
    private static final GamesManager gamesManager = new GamesManager(authManager);

    @OnMessage
    public String onMessage(Session peer, String message) {
        System.out.println("MESSAGE = " + message);
        RequestBuilder request = new RequestBuilder(message);
        try {
            switch(request.getCode()) {
                case RequestBuilder.AUTH_LOGIN :
                    authManager.login(peer, request);
                    sendLoggedUsers();
                    break;
                case RequestBuilder.AUTH_REGISTER :
                    authManager.register(peer, request);
                    break;
                case RequestBuilder.INFO_GET_PROFILE:
                    authManager.getUserInfo(peer, request);
                    break;
                case RequestBuilder.INFO_GET_HISTORY : 
                    
                    break;
                case RequestBuilder.GAME_NEW_GAME :
                    gamesManager.createNewGame(peer, request);
                    break;
                case RequestBuilder.GAME_READY :
                    gamesManager.playerReady(peer, request);
                    break;
                case RequestBuilder.GAME_NOT_READY :
                    gamesManager.playerRefuse(peer, request);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    @OnOpen
    public void onOpen(Session peer) {
        System.out.println("New connexion");
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("ERROR");
        System.out.println(t.getMessage());
    }

    @OnClose
    public void onClose(Session peer) {
        try {
            authManager.disconnect(peer);
            sendLoggedUsers();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void sendLoggedUsers() throws Exception {
        Set<Joueur> players = authManager.getPlayers();
        Set<Session> peers = authManager.getSessions();
        Set<String> usernames = new HashSet();
        RequestBuilder rb = new RequestBuilder();
        
        for (Joueur player : players) {
            usernames.add(player.getPseudo());
        }
        rb.setData(RequestBuilder.INFO_PLAYER_LIST, usernames);
        rb.build();
        
        for (Session peer : peers) {
            try {
                peer.getBasicRemote().sendText(rb.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(ws421.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
