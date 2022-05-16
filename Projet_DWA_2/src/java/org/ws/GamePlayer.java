/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import java.util.ArrayList;
import java.util.List;
import org.dao.DAO_LanceOrdre;
import org.dao.DAO_LancerCharge;
import org.dao.DAO_LancerDecharge;
import org.dao.DAO_Resultat;
import org.dao.DAO_ValDe;
import org.donnees.Joueur;
import org.donnees.LanceOrdre;
import org.donnees.LancerCharge;
import org.donnees.LancerDecharge;
import org.donnees.Resultat;
import org.donnees.ValDe;

/**
 *
 * @author fred2
 */
public class GamePlayer {
    
    public static final int PLAYER_WAITING = 0;
    public static final int PLAYER_READY = 1;
    public static final int PLAYER_REFUSE = 2;
    
    private final int gameId;
    
    private Joueur joueur;
    
    private int status;
    
    private int token;
    
    private List<LancerCharge> lancerCharge;
    private List<LanceOrdre> lanceOrdre;
    private List<LancerDecharge> lancerDecharge;
    private Resultat resultat;
    
    private DiceValue lastRoll;
    
    public GamePlayer(Joueur j, int id) {
        joueur = j;
        gameId = id;
        status = PLAYER_WAITING;
        token = 0;
        lancerCharge = new ArrayList();
        lanceOrdre = new ArrayList();
        lancerDecharge = new ArrayList();
        resultat = new Resultat(gameId, joueur.getPseudo());
    };
    
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
    
    public void rollLoad(int d1, int d2, int d3, int numLance) {
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
    
    public void rollOrder(int d1, int d2, int d3, int numLance) {
        DAO_ValDe daoValDe = new DAO_ValDe();
        ValDe valDe  = new ValDe();
        String codeDe = String.valueOf(d1) + String.valueOf(d2) + String.valueOf(d3); 
        LanceOrdre lo = new LanceOrdre(joueur.getPseudo(), gameId, numLance);
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
        lo.setCodeDe(valDe);
        lanceOrdre.add(lo);
        lastRoll = new DiceValue(d1, d2, d3);
    }
    
    public void rollDump(int d1, int d2, int d3, int numLance) {
        DAO_ValDe daoValDe = new DAO_ValDe();
        ValDe valDe  = new ValDe();
        String codeDe = String.valueOf(d1) + String.valueOf(d2) + String.valueOf(d3); 
        LancerDecharge ld = new LancerDecharge(gameId, joueur.getPseudo(), numLance, 0);
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
        ld.setCodeDe(valDe);
        lancerDecharge.add(ld);
        lastRoll = new DiceValue(d1, d2, d3);
        
    }
    
    public void endLoad() {
        resultat.setNbJetonCharge(token);
    }
    
    public void endDump() {
        resultat.setNbJetonDecharge(token);
    }
    
    public DiceValue getLastRoll() {
        return lastRoll;
    }
    
    public boolean asPlay() {
        return lastRoll != null;
    }
    
    public void store() throws Exception {
        DAO_LancerCharge daoLancerCharge = new DAO_LancerCharge();
        DAO_LanceOrdre daoLanceOrdre = new DAO_LanceOrdre();
        DAO_LancerDecharge daoLancerDecharge = new DAO_LancerDecharge();
        DAO_Resultat daoResultat = new DAO_Resultat();
        
        daoResultat.create(resultat);
        for(LancerCharge lc : lancerCharge) {
            daoLancerCharge.create(lc);
        }
        for(LanceOrdre lo : lanceOrdre) {
            daoLanceOrdre.create(lo);
        }
        for(LancerDecharge ld : lancerDecharge) {
            daoLancerDecharge.create(ld);
        }
    }
    
}
