/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.websocket.Session;

/**
 *
 * @author fred2
 */
public class GameLoad extends GameRound {
    
    private final int nbPlayer;

    public GameLoad(int gameId, Map p, List po) {
        super(gameId, Game.GAME_CHARGE);
        players = p;
        playerOrder = po;
        token = 21;
        nbPlayer = po.size();
    }
    
    @Override
    protected void endRoll(Session peer) throws Exception {
        
        if (players.get(peer).equals(playerOrder.get(currentPlayer))) {
            currentPlayer++;
            sendEndRoll();
            if (currentPlayer >= playerOrder.size()) {
                endRound();
            }
            sendGame();
        } else {
            sendWrongUser(peer);
        }
    }

    @Override
    protected void endRound() throws Exception {
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
        }
        
    }

    @Override
    public void start() throws Exception { 
        sendGame(RequestBuilder.GAME_START);
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
                }
            }
        }
        
        if (token >= maxRoll.getToken()) {
            token -= maxRoll.getToken();
            looser.addToken(maxRoll.getToken());
        }
    }

    @Override
    protected boolean isEnded() {
        return token == 0;
    }
    
}
