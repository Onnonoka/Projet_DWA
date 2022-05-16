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
import java.util.Random;
import javax.websocket.Session;
import org.json.JSONArray;

/**
 *
 * @author fred2
 */
public class GameDump extends GameRound {
    
    private int numLance;
    private int turnReroll;
    private int currentReroll;
    private boolean firstPlayerTurn;
    private int nbPlayer;
    private int nbPlayerPlayTurn;
    
    public GameDump(int gameId, Map p, List po) {
        super(gameId);
        players = p;
        playerOrder = po;
        token = 0;
        nbPlayer = po.size();
        turn = 1;
        numLance = 0;
        turnReroll = 0;
        currentReroll = 0;
        firstPlayerTurn = true;
        nbPlayerPlayTurn = 0;
    }

    @Override
    public void start() throws Exception {
        status = Game.GAME_DECHARGE;
        sendGame();
        setTimeout();
    }

    @Override
    public void newRoll(Session peer, JSONArray dices) throws Exception {
        if (players.get(peer).equals(playerOrder.get(currentPlayer))) {
            clearTimeout();
            GamePlayer gp = playerOrder.get(currentPlayer);
            gp.rollDump(dices.getInt(0), dices.getInt(1), dices.getInt(2), numLance);
            sendRoll(dices);
            numLance++;
            currentReroll++;
            if ((currentReroll >= 3 && firstPlayerTurn) || (currentReroll >= turnReroll && !firstPlayerTurn) ) {
                endRoll(peer);
            }
        } else {
            sendWrongUser(peer);
        }
    }

    @Override
    protected void endRoll(Session peer) throws Exception {
        if (players.get(peer).equals(playerOrder.get(currentPlayer))) {
            clearTimeout();
            currentPlayer++;
            if (firstPlayerTurn) {
                turnReroll = currentReroll;
                firstPlayerTurn = false;
            }
            currentReroll = 0;
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
                setTimeout();
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
        turnReroll = 0;
        deliverToken();
        turn++;
        firstPlayerTurn = true;
        currentReroll = 0;
        if (isEnded()) {
            endPhase();
        }
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
        } else {
            looser.addToken(winner.getToken());
            winner.removeToken(winner.getToken());
        }
    }

    @Override
    protected boolean isEnded() {
        Optional<GamePlayer> optional = players.values().stream()
                                   .filter(x -> x.getToken() == 0)
                                   .findFirst();
        return optional.isPresent();
    }
    
    public void setFirstPlayer(int first) {
        currentPlayer = first;
    }

    @Override
    protected void endPhase() throws Exception {
        for (GamePlayer gp : players.values()) {
            gp.endDump();
        }
        status = Game.ROUND_ENDED;
        sendGame(RequestBuilder.GAME_END);
    }
    
    public GamePlayer getWinner() {
        GamePlayer winner = null;
        for (GamePlayer gp : players.values()) {
            if (winner.getToken() == 0) {
                winner = gp;
            }
        }
        return winner;
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

        if (firstPlayerTurn) {
            for (int i = 0; i < 3; i++) {
                for (int J = 0; J < 3; J++) {
                    diceArray.put(rand.nextInt(6) + 1); 
                }
                newRoll(peer, diceArray);
            } 
        } else {
            for (int i = 0; i < turnReroll; i++) {
                for (int J = 0; J < 3; J++) {
                    diceArray.put(rand.nextInt(6) + 1); 
                }
                newRoll(peer, diceArray);
            }        
        }
    }
}
