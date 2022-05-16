/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.websocket.Session;
import org.json.JSONArray;

/**
 *
 * @author fred2
 */
public class GameLoad extends GameRound {
    
    private final int nbPlayer;
    private int numLance;
    private final GameOrder nextPhase;

    public GameLoad(int gameId, GameOrder next, Map p, List po) {
        super(gameId);
        players = p;
        playerOrder = po;
        token = 21;
        nbPlayer = po.size();
        turn = 1;
        numLance = 0;
        nextPhase = next;
    }

    @Override
    protected void endTurn() throws Exception {
        List<GamePlayer> rerollPlayer = new ArrayList();
        DiceValue minRoll = null;
        boolean needReroll = false;
        
        for (GamePlayer gp : players.values()) {
            if (minRoll == null) {
                minRoll = gp.getLastRoll();
                rerollPlayer.add(gp);
            } else if (gp.getLastRoll().equals(minRoll)) {
                rerollPlayer.add(gp);
                needReroll = true;
            } else if (minRoll.compare(gp.getLastRoll())) {
                rerollPlayer.clear();
                minRoll = gp.getLastRoll();
                rerollPlayer.add(gp);
                needReroll = false;
            }

        }

        if (needReroll) {
            for (GamePlayer player : rerollPlayer) {
                playerOrder.add(player);
            }
        } else {
            currentPlayer = 0;
            while(playerOrder.size() > nbPlayer) {
                playerOrder.remove(playerOrder.size() - 1);
            }
            deliverToken();
            turn++;
        }
        if (isEnded()) {
            endPhase();
        }
        
    }
    
    @Override
    public void newRoll(Session peer, JSONArray dices) throws Exception {
        if (players.get(peer).equals(playerOrder.get(currentPlayer))) {
            clearTimeout();
            GamePlayer gp = playerOrder.get(currentPlayer);
            gp.rollLoad(dices.getInt(0), dices.getInt(1), dices.getInt(2), numLance);
            sendRoll(dices);
            numLance++;
            endRoll(peer);
        } else {
            sendWrongUser(peer);
        }
    }

    @Override
    public void start() throws Exception { 
        status = Game.GAME_CHARGE;
        sendGame(RequestBuilder.GAME_START);
        setTimeout();
    }

    @Override
    protected void deliverToken() {
        GamePlayer looser = null;
        DiceValue maxRoll = null;
         
        for (GamePlayer gp : players.values()) {
            if (maxRoll == null) {
                looser = gp;
                maxRoll = gp.getLastRoll();
            } else if (gp.getLastRoll().compare(maxRoll)) {
                maxRoll = gp.getLastRoll();
            } else if (looser.getLastRoll().compare(gp.getLastRoll())) {
                looser = gp;
            }
            
            if (gp.getLastRoll().isNenette()) {
                if (token >= 2) {
                    gp.addToken(2);
                    token -= 2;
                } else {
                    gp.addToken(token);
                    token = 0;
                }
            }
        }
        
        if (token >= maxRoll.getToken()) {
            token -= maxRoll.getToken();
            looser.addToken(maxRoll.getToken());
        } else {
            looser.addToken(token);
            token = 0;
        }
    }

    @Override
    protected boolean isEnded() {
        return token == 0;
    }

    @Override
    protected void endPhase() throws Exception {
        for(GamePlayer gp : players.values()) {
            gp.endLoad();
        }
        status = Game.ROUND_ENDED;
        nextPhase.start();
    }

    @Override
    protected void handleNoReply() throws Exception {
        Random rand = new Random();
        JSONArray diceArray = new JSONArray();
        Session peer = null;
        for (Session s : players.keySet()) {
            if (players.get(s).equals(playerOrder.get(currentPlayer))) {
                peer = s;
            }
        }
        for (int i = 0; i < 3; i++) {
            diceArray.put(rand.nextInt(6) + 1); 
        }
        newRoll(peer, diceArray);
    }
    
}
