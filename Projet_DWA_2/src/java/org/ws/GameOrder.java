/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.websocket.Session;
import org.json.JSONArray;

/**
 *
 * @author fred2
 */
public class GameOrder extends GameRound {
    
    private final int nbPlayer;
    
    private int firstPlayer;
    
    private int numLance;

    public GameOrder(int gameId, GameDump next, Map p, List po) {
        super(gameId, next);
        players = p;
        playerOrder = po;
        nbPlayer = players.size();
        firstPlayer = -1;
        numLance = 0;
    }

    @Override
    public void start() throws Exception {
        status = Game.GAME_ORDER;
        sendGame();
    }

    @Override
    public void newRoll(Session peer, JSONArray dices) throws Exception {
        System.out.println("rollOrder");
        if (players.get(peer).equals(playerOrder.get(currentPlayer))) {
            GamePlayer gp = playerOrder.get(currentPlayer);
            gp.rollOrder(dices.getInt(0), dices.getInt(1), dices.getInt(2), id, numLance);
            sendRoll(dices);
            numLance++;
            endRoll(peer);
        } else {
            sendWrongUser(peer);
        }
    }

    @Override
    protected void endTurn() throws Exception {
        List<GamePlayer> rerollPlayer = new ArrayList();
        DiceValue maxRoll = null;
        boolean needReroll = false;
        
        for (GamePlayer gp : players.values()) {
            if (maxRoll == null) {
                maxRoll = gp.getLastRoll();
                rerollPlayer.add(gp);
            } else if (gp.getLastRoll().equals(maxRoll)) {
                rerollPlayer.add(gp);
                needReroll = true;
            } else if (gp.getLastRoll().compare(maxRoll)) {
                rerollPlayer.clear();
                maxRoll = gp.getLastRoll();
                rerollPlayer.add(gp);
                needReroll = false;
            }

        }

        if (needReroll) {
            for (GamePlayer player : rerollPlayer) {
                playerOrder.add(player);
            }
        } else {
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
    protected void deliverToken() {
        GamePlayer winner = null;
        DiceValue maxRoll = null;
        
        for (GamePlayer gp : players.values()) {
            if (maxRoll == null) {
                winner = gp;
                maxRoll = gp.getLastRoll();
            } else if (gp.getLastRoll().compare(maxRoll)) {
                maxRoll = gp.getLastRoll();
                winner = gp;
            }
        }
        
        firstPlayer = playerOrder.indexOf(winner);
    }

    @Override
    protected boolean isEnded() {
        return firstPlayer != -1;
    }
    
    protected int getFirstPlayer() {
        return firstPlayer;
    }
    
}
