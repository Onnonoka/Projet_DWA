/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.util.ArrayList;
import java.util.List;
import org.dao.DAO_ValDe;
import org.donnees.Joueur;
import org.donnees.LanceOrdre;
import org.donnees.LancerCharge;
import org.donnees.LancerDecharge;
import org.donnees.ValDe;

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
    
    private List<LancerCharge> lancerCharge;
    private List<LanceOrdre> lanceOrdre;
    private List<LancerDecharge> lancerDecharge;
    
    private DiceValue lastRoll;
    
    public GamePlayer(Joueur j) {
        joueur = j;
        status = PLAYER_WAITING;
        token = 0;
        lancerCharge = new ArrayList();
        lanceOrdre = new ArrayList();
        lancerDecharge = new ArrayList();
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
    
    public void addToken(int nb) {
        token += nb;
    }
    
    public void removeToken(int nb) {
        token -= nb;
    }
    
    public void rollLoad(int d1, int d2, int d3, int gameId, int numLance) {
        DAO_ValDe daoValDe = new DAO_ValDe();
        ValDe valDe  = new ValDe();
        String codeDe = String.valueOf(d1) + String.valueOf(d2) + String.valueOf(d3); 
        LancerCharge lc = new LancerCharge(gameId, joueur.getPseudo(), numLance);
        try {
            valDe.setCodeDe(codeDe);
            valDe.setVal1(d1);
            valDe.setVal2(d2);
            valDe.setVal3(d3);
            daoValDe.create(valDe);
            System.out.println("Created");
        } catch(Exception e) {
            valDe = daoValDe.find(codeDe);
            System.out.println("Got");
        }
        lc.setCodeDe(valDe);
        lancerCharge.add(lc);
        lastRoll = new DiceValue(d1, d2, d3);
    }
    
    public void rollOrder(int d1, int d2, int d3, int gameId, int numLance) {
        lastRoll = new DiceValue(d1, d2, d3);
        DAO_ValDe daoValDe = new DAO_ValDe();
        ValDe valDe  = new ValDe();
        String codeDe = String.valueOf(d1) + String.valueOf(d2) + String.valueOf(d3); 
        LancerCharge lc = new LancerCharge(gameId, joueur.getPseudo(), numLance);
        try {
            valDe.setCodeDe(codeDe);
            valDe.setVal1(d1);
            valDe.setVal2(d2);
            valDe.setVal3(d3);
            daoValDe.create(valDe);
            System.out.println("Created");
        } catch(Exception e) {
            valDe = daoValDe.find(codeDe);
            System.out.println("Got");
        }
        lc.setCodeDe(valDe);
        lancerCharge.add(lc);
        lastRoll = new DiceValue(d1, d2, d3);
    }
    
    public void rollDump(int d1, int d2, int d3, int gameId, int numLance) {
        lastRoll = new DiceValue(d1, d2, d3);
        
    }
    
    public DiceValue getLastRoll() {
        return lastRoll;
    }
    
    public void newRound() {
        lastRoll = null;
    }
    
    public boolean asPlay() {
        return lastRoll != null;
    }
    
}
