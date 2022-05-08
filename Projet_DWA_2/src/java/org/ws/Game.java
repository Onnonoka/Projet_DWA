/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;
import org.donnees.Joueur;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author fred2
 */
public class Game {
    
    private static final int GAME_ABORT = -2;
    private static final int GAME_CREATING = -1;
    private static final int GAME_CHECKING = 0;
    private static final int GAME_CHARGE = 1;
    private static final int GAME_ORDER = 2;
    private static final int GAME_DECHARGE = 3;
    private static final int GAME_END = 4;

    private final int gameId;
    private int gameStatus;
    
    private Map<Session, GamePlayer> players;
    
    public Game(int id) {
        gameStatus = GAME_CREATING;
        gameId = id;
        players = new HashMap<>();
    }
    
    public void addPlayer(Session s, Joueur j) {
        players.put(s, new GamePlayer(j));
    }
    
    public void startGame() {
        if (gameStatus == GAME_CREATING) {
            try {
                gameStatus = GAME_CHECKING;
                sendPlayerStatus(RequestBuilder.GAME_NEW_GAME);
                System.out.println("NEW GAME STARTED ID : " + gameId);
            } catch (Exception ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("NEW GAME ERROR ID : " + gameId);
            }
        }
    }
    
    public void sendPlayerStatus(int code) throws Exception {
        RequestBuilder request = new RequestBuilder();
        JSONObject jsonData = new JSONObject();
        JSONArray playerArray = new JSONArray();
        jsonData.put("id", gameId);
        Set<Session> peers = players.keySet();
        for (Session peer : peers) {
            JSONObject singlePlayer = new JSONObject();
            singlePlayer.put("username", players.get(peer).get().getPseudo());
            singlePlayer.put("status", players.get(peer).getStatus());
            playerArray.put(singlePlayer);
        }
        jsonData.put("player", playerArray);
        request.setData(code, jsonData);
        request.build();
        for (Session peer : peers) {
            peer.getBasicRemote().sendText(request.getMessage());
        }
    }
    
    public void sendPlayerStatus() throws Exception {
        sendPlayerStatus(RequestBuilder.GAME_STARTING);
    }
    
    public void abort() {
        gameStatus = GAME_ABORT;
    }
    
    public void setPlayerStatus(Session peer, int status) {
        try {
            players.get(peer).setStatus(status);
            sendPlayerStatus();
        } catch (Exception ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
