/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.websocket.Session;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author fred2
 */
public abstract class GameRound {
    protected final int id;
    protected final int status;
    
    protected Map<Session, GamePlayer> players;
    protected List<GamePlayer> playerOrder;
    protected int currentPlayer;
    protected int token;
    protected int turn;

    
    public GameRound(int gameId, int gameStatus) {
        id = gameId;
        status = gameStatus;
        token = 0;
        turn = 0;
    }
    
    public abstract void start() throws Exception;
    
    protected void sendGame() throws Exception {
        sendGame(RequestBuilder.GAME_DATA);
    }
    
    protected void sendGame(int code) throws Exception {
        RequestBuilder request = new RequestBuilder();
        JSONObject jsonData = new JSONObject();
        JSONArray playerArray = new JSONArray();
        Set<Session> peers = players.keySet();
        
        jsonData.put("id", id);
        jsonData.put("remainToken", token);
        jsonData.put("currentPlayer", playerOrder.get(currentPlayer).get().getPseudo());
        jsonData.put("status", status);
        jsonData.put("turn", turn);

        for (Session peer : peers) {
            JSONObject singlePlayer = new JSONObject();
            singlePlayer.put("username", players.get(peer).get().getPseudo());
            singlePlayer.put("token", players.get(peer).getToken());
            playerArray.put(singlePlayer);
        }
        jsonData.put("players", playerArray);

        request.setData(code, jsonData);        
        request.build();
        for (Session peer : peers) {
            peer.getBasicRemote().sendText(request.getMessage());
        }
    }
    
    protected void sendWrongUser(Session peer) throws Exception {
        RequestBuilder rb = new RequestBuilder();
        rb.setData(RequestBuilder.GAME_WRONG_PLAYER);
        rb.build();
        peer.getBasicRemote().sendText(rb.getMessage());
        sendGame();
    }
    
    public abstract void newRoll(Session peer, JSONArray dices) throws Exception;
    
    protected void sendRoll(JSONArray dices) throws Exception {
        RequestBuilder request = new RequestBuilder();
        JSONObject jsonData = new JSONObject();
        Set<Session> peers = players.keySet();
        
        jsonData.put("id", id);
        jsonData.put("dices", dices);
        
        request.setData(RequestBuilder.GAME_LUNCH_DICE, jsonData);        
        request.build();
        for (Session peer : peers) {
            peer.getBasicRemote().sendText(request.getMessage());
        }
    }
        
    protected void sendEndRoll() throws Exception {
        RequestBuilder rb = new RequestBuilder();
        JSONObject jsonData = new JSONObject();
        Set<Session> peers = players.keySet();
        
        jsonData.put("id", id);
        
        rb.setData(RequestBuilder.GAME_END_ROLL, jsonData);
        rb.build();
        for (Session peer : peers) {
            peer.getBasicRemote().sendText(rb.getMessage());
        }
    }
    
    protected void endRoll(Session peer)  throws Exception {
        if (players.get(peer).equals(playerOrder.get(currentPlayer))) {
            currentPlayer++;
            sendEndRoll();
            if (currentPlayer >= playerOrder.size()) {
                endTurn();
            }
            if (!isEnded()) {
                sendGame();
            }
        } else {
            sendWrongUser(peer);
        }
    }
    
    protected abstract void endTurn()  throws Exception;
    
    protected abstract void deliverToken();
    
    protected abstract boolean isEnded();
    
}
