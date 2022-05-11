/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
    
    public static final int GAME_ABORT = -2;
    public static final int GAME_CREATING = -1;
    public static final int GAME_CHECKING = 0;
    public static final int GAME_CHARGE = 1;
    public static final int GAME_ORDER = 2;
    public static final int GAME_DECHARGE = 3;
    public static final int GAME_END = 4;

    private final int gameId;
    private int gameStatus;
    
    private final Map<Session, GamePlayer> players;
    
    private GameLoad loadPhase;
    private GameOrder orderPhase;
    private GameDump dumpPhase;
    
    private final List<GamePlayer> playerOrder;
    
    public Game(int id) {
        gameStatus = GAME_CREATING;
        gameId = id;
        players = new HashMap<>();
        playerOrder = new ArrayList();
    }
    
    public void addPlayer(Session s, Joueur j) {
        GamePlayer gp = new GamePlayer(j);
        players.put(s, gp);
        playerOrder.add(gp);
    }
    
    public void startGame() {
        if (gameStatus == GAME_CREATING) {
            try {
                gameStatus = GAME_CHECKING;
                sendPlayerStatus(RequestBuilder.GAME_NEW_GAME);
                System.out.println("NEW GAME STARTED ID : " + gameId);
                startTimeOutChecking();
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
    
    public void abort() throws Exception {
        gameStatus = GAME_ABORT;
        RequestBuilder request = new RequestBuilder();
        JSONObject jsonData = new JSONObject();
        Set<Session> peers = players.keySet();
        
        jsonData.put("id", gameId);
        request.setData(RequestBuilder.GAME_CANCEL, jsonData);
        request.build();
        
        for (Session peer : peers) {
            peer.getBasicRemote().sendText(request.getMessage());
        }
        System.out.println("GAME ABORT ID : " + gameId);
    }
    
    public void setPlayerStatus(Session peer, int status) {
        try {
            players.get(peer).setStatus(status);
            sendPlayerStatus();
        } catch (Exception ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void startTimeOutChecking() {
        final Duration timeout = Duration.ofSeconds(15);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        
        executor.schedule(() -> {
            boolean canContinue = true;
            Set<Session> peers = players.keySet();
            
            for (Session peer : peers) {
                if (players.get(peer).getStatus() != GamePlayer.PLAYER_READY) {
                    canContinue = false;
                }
            }
            try {
                if (canContinue) {
                    lunchGame();
                } else {
                    abort();
                }
            } catch (Exception ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
        }, timeout.toMillis(), TimeUnit.MILLISECONDS);
    }
    
    private void lunchGame() throws Exception {
        gameStatus = GAME_CHARGE;

        loadPhase = new GameLoad(gameId, players, playerOrder);
        orderPhase = new GameOrder(gameId, players, playerOrder);
        dumpPhase = new GameDump(gameId, players, playerOrder);
        loadPhase.start();
    }
    
    public void newRoll(Session peer, JSONArray dices) throws Exception {        
        switch(gameStatus) {
            case GAME_CHARGE:
                loadPhase.newRoll(peer, dices);
                break;
            case GAME_ORDER:
                orderPhase.newRoll(peer, dices);
                break;
        }
    }

    public void endTurn(Session peer) throws Exception {
        switch(gameStatus) {
            case GAME_CHARGE:
                loadPhase.endRoll(peer);
                if (loadPhase.isEnded()) {
                    gameStatus = GAME_ORDER;
                    orderPhase.start();
                }
                break;
            case GAME_ORDER:
                orderPhase.endRoll(peer);
                if (orderPhase.isEnded()) {
                    gameStatus = GAME_DECHARGE;
                    dumpPhase.start();
                }
                break;
        }
    }
    
}
