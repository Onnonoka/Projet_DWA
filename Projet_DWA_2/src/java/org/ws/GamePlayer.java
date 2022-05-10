/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import org.donnees.Joueur;

/**
 *
 * @author fred2
 */
public class GamePlayer {
    
    public static final int PLAYER_WAITING = 0;
    public static final int PLAYER_READY = 1;
    public static final int PLAYER_REFUSE = 2;
    
    private Joueur joueur;
    
    private int status;
    
    private int token;
    
    public GamePlayer(Joueur j) {
        joueur = j;
        status = PLAYER_WAITING;
        token = 0;
    }
    
    public void setStatus(int s) {
        status = s;
    }
    
    public int getStatus() {
        return status;
    }
    
    public Joueur get() {
        return joueur;
    }
    
    public int getToken() {
        return token;
    }
    
}
