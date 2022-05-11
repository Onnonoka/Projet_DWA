/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.websocket.Session;
import org.json.JSONArray;

/**
 *
 * @author fred2
 */
public class GameDump extends GameRound {
    
    private int numLance;
    private int turnReroll;
    private boolean firstPlayerTurn;
    private int nbPlayer;
    private int nbPlayerPlayTurn;
    
    public GameDump(int gameId, Map p, List po) {
        super(gameId, Game.GAME_DECHARGE);
        players = p;
        playerOrder = po;
        token = 0;
        nbPlayer = po.size();
        turn = 1;
        numLance = 0;
        turnReroll = 0;
        firstPlayerTurn = true;
        nbPlayerPlayTurn = 0;
    }

    @Override
    public void start() throws Exception {
        sendGame();
    }

    @Override
    public void newRoll(Session peer, JSONArray dices) throws Exception {
        if (players.get(peer).equals(playerOrder.get(currentPlayer))) {
            GamePlayer gp = playerOrder.get(currentPlayer);
            gp.rollDump(dices.getInt(0), dices.getInt(1), dices.getInt(2), id, numLance);
            sendRoll(dices);
            numLance++;
            if (firstPlayerTurn) {
                turnReroll++;
            }
        } else {
            sendWrongUser(peer);
        }
    }

    @Override
    protected void endRoll(Session peer) throws Exception {
        if (players.get(peer).equals(playerOrder.get(currentPlayer))) {
            currentPlayer++;
            firstPlayerTurn = false;
            nbPlayerPlayTurn++;
            sendEndRoll();
            if (currentPlayer >= playerOrder.size()) {
                currentPlayer = 0;
            }
            if (nbPlayerPlayTurn >= nbPlayer) {
                endTurn();
            }
            if (!isEnded()) {
                sendGame();
            }
        } else {
            sendWrongUser(peer);
        }
    }

    @Override
    protected void endTurn() throws Exception {
        GamePlayer looser = null;
        
        for (GamePlayer gp : players.values()) {
            if (looser == null) {
                looser = gp;
            } else if (looser.getLastRoll().compare(gp.getLastRoll())) {
                looser = gp;
            }
        }

        currentPlayer = playerOrder.indexOf(looser);
        nbPlayerPlayTurn = 0;
        deliverToken();
        turn++;
    }

    @Override
    protected void deliverToken() {
        GamePlayer looser = null;
        GamePlayer winner = null;
        
        for (GamePlayer gp : players.values()) {
            if (winner == null) {
                looser = gp;
                winner = gp;
            } else if (gp.getLastRoll().compare(winner.getLastRoll())) {
                winner = gp;
            } else if (looser.getLastRoll().compare(gp.getLastRoll())) {
                looser = gp;
            }
        }
        
        if (winner.getToken() >= winner.getLastRoll().getToken()) {
            winner.removeToken(winner.getLastRoll().getToken());
            looser.addToken(winner.getLastRoll().getToken());
        }
    }

    @Override
    protected boolean isEnded() {
        List<GamePlayer> p = (List<GamePlayer>) players.values();
        Optional<GamePlayer> optional = p.stream()
                                   .filter(x -> x.getToken() == 0)
                                   .findFirst();
        return optional.isPresent();
    }
    
    public void setFirstPlayer(int first) {
        currentPlayer = first;
    }
    
    
    
}
