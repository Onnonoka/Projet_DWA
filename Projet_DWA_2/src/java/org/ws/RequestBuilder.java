/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.util.List;
import java.util.Set;
import org.dao.DAO_Joueur;
import org.donnees.Joueur;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author fred2
 */
public class RequestBuilder {    
    private static final int UNDEFINED_CODE = -999;
    
    public static final int TIMED_OUT = -900;
    public static final int USER_DISCONNECTED = -20;
    
    public static final int GAME_WRONG_PLAYER = -13;
    
    public static final int INFO_WRONG_USERNAME = -3;
    
    public static final int AUTH_USERNAME_ALREADY_USED = -2;
    public static final int AUTH_WRONG_CREDENTIALS = -1;
    
    public static final int AUTH_LOGIN = 0;
    public static final int AUTH_REGISTER = 1;
    public static final int AUTH_UPDATE = 2;
    
    public static final int INFO_GET_PROFILE = 3;
    public static final int INFO_GET_HISTORY = 4;
    public static final int INFO_PLAYER_LIST = 5;
    
    public static final int GAME_NEW_GAME = 6;
    public static final int GAME_PENDING = 7;
    public static final int GAME_PLAYER_ACCEPT = 8;
    public static final int GAME_PLAYER_REFUSE = 9;
    public static final int GAME_START = 10;
    public static final int GAME_CANCEL = 11;
    
    public static final int GAME_DATA = 12;
    public static final int GAME_LUNCH_DICE = 13;
    public static final int GAME_END_ROLL = 14;
    public static final int GAME_END = 15;
    public static final int GAME_DICE_SELECT = 16;
    
    public static final int REPLAY_NEW_REPLAY = 17;
    
    public static final int REPLAY_BACK = 18;
    public static final int REPLAY_NEXT = 19;
    public static final int REPLAY_DATA = 20;
    
    private String message;
    
    private int code = UNDEFINED_CODE;
    
    private JSONObject data;
    
    private Joueur player = null;
    
    private Set<String> playerList = null;
    
    public RequestBuilder() {
        data = new JSONObject();
    }
    
    public RequestBuilder(String msg) {
        message = msg;
        JSONObject jsonMessage = new JSONObject(message);
        code = jsonMessage.getInt("code");
        data = jsonMessage.getJSONObject("data");
    }
    
    public int getCode() {
        return code;
    }
    
    public JSONObject getData() {
        return data;
    }
    
    public Joueur getPlayer() {
        Joueur j = new Joueur(getUsername());
        j.setMdp(getPassword());
        return j;
    }
    
    public String getUsername() {
        return data.getString("username");
    }
    
    public String getPassword() {
        return data.getString("password");
    }
    
    public void setData(int c) {
        code = c;
    }
    
    public void setData(int c, JSONObject jsonData) {
        code = c;
        data = jsonData;
    }
    
    public void setData(int c, Joueur j) {
        code = c;
        player = j;
    }
    
    public void setData(int c, Set<String> users) {
        code = c;
        playerList = users;
    }
        
    public void build() throws Exception {
        DAO_Joueur daoJoueur = new DAO_Joueur();
        JSONObject jsonReplyMessage = new JSONObject();
        JSONObject jsonDataMessage = new JSONObject();
        JSONArray jsonArrayMessage = new JSONArray();
        
        jsonReplyMessage.put("code", code);
        switch(code) {
            case AUTH_LOGIN :
            case INFO_GET_PROFILE:
                jsonDataMessage.put("pseudo", player.getPseudo());
                jsonDataMessage.put("mdp", player.getMdp());
                jsonDataMessage.put("age", player.getAge());
                jsonDataMessage.put("sexe", player.getSexe());
                jsonDataMessage.put("ville", player.getVille());
                jsonDataMessage.put("played", daoJoueur.getJoueurNbPartie(player));
                jsonDataMessage.put("win", daoJoueur.getJoueurNbVictoire(player));
                jsonDataMessage.put("average1", daoJoueur.getNbJetonChargeMoyenJoueur(player));
                jsonDataMessage.put("average2", daoJoueur.getNbJetonDechargeMoyenJoueur(player));
                jsonReplyMessage.put("data", jsonDataMessage);
                break;
            case INFO_PLAYER_LIST :
                for(String username : playerList) {
                    jsonArrayMessage.put(username);
                }
                jsonDataMessage.put("usernames", jsonArrayMessage);
                jsonReplyMessage.put("data", jsonDataMessage);
                break;
            case AUTH_UPDATE:
            case GAME_NEW_GAME :
            case GAME_PENDING :
            case GAME_START :
            case GAME_CANCEL :
            case GAME_DATA :
            case GAME_LUNCH_DICE :
            case GAME_END_ROLL :
            case GAME_END :
            case INFO_GET_HISTORY :
            case REPLAY_DATA :
                jsonReplyMessage.put("data", data);
                break;
            case AUTH_WRONG_CREDENTIALS :
            case AUTH_USERNAME_ALREADY_USED :
            case GAME_WRONG_PLAYER :
                break;
            case UNDEFINED_CODE:
            default :
                throw new Exception("Cannot build a message with the code  " + code);  
        }
        
        message = jsonReplyMessage.toString();
    }
    
    public String getMessage() {
        return message;
    }
}
