/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;
import org.dao.DAO_Partie;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author fred2
 */
public class GamesManager {
    
    private final Map<Integer, Game> games;
    
    private final AuthManager authManager;
    
    private int nextAvailableId;
    
    public GamesManager(AuthManager am) {
        DAO_Partie daoPartie = new DAO_Partie();
        nextAvailableId = daoPartie.findAll().size();               // A refaire!! Ne prend pas en compte les parties Annulé
                                                                                       // Il faut récup l'indice le plus élevé et non la taille du tableau
        games = new HashMap();
        authManager = am;
    }
    
    public void createNewGame(Session peer, RequestBuilder request) throws Exception {
        int id = nextAvailableId;
        nextAvailableId++;
        JSONObject data = request.getData();
        int nbPlayer = data.getInt("nbPlayer");
        JSONArray usernamesJSON = data.getJSONArray("playersOrder");
        Game game = new Game(id);
        
        games.put(id, game);
        
        if (usernamesJSON != null) {
            for (int i = 0; i < nbPlayer; i++) {
                String username = usernamesJSON.get(i).toString();
                if (authManager.isLogged(username)) {
                    game.addPlayer(authManager.getSession(username), authManager.getPlayer(username));
                } else {
                    game.abort();
                }
            }
            game.startGame();
        }
        
    }
    
    public void playerReady(Session peer, RequestBuilder request) {
        int id = request.getData().getInt("id");
        games.get(id).setPlayerStatus(peer, GamePlayer.PLAYER_READY);
    }
    
    public void playerRefuse(Session peer, RequestBuilder request) {
        int id = request.getData().getInt("id");
        games.get(id).setPlayerStatus(peer, GamePlayer.PLAYER_REFUSE);
    }
    
    public void playerLunchDice(Session peer, RequestBuilder request) throws Exception {
        int id = request.getData().getInt("id");
        games.get(id).newRoll(peer, request.getData().getJSONArray("dices"));
    }
    
    public void playerEndTurn(Session peer, RequestBuilder request) throws Exception {
        int id = request.getData().getInt("id");
        games.get(id).endTurn(peer);
    }
    
}
