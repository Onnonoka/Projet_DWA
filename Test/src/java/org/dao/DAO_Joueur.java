/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import org.donnees.Joueur;
import org.donnees.Resultat;

public class DAO_Joueur extends DAO<Joueur> {
    
    public DAO_Joueur() {
        super();
        this.setClass(Joueur.class);
    }

    @Override
    public List<Joueur> findAll() {
        // Output Array
        List<Joueur> joueurArray = new ArrayList();
        // Request
        Query query = this.entityManager.createQuery("select j.pseudo, j.mdp, j.age, j.sexe, j.ville from Joueur j");
        List<Joueur> joueur = (List<Joueur>) query.getResultList();
        Iterator itr = joueur.iterator();
        // iterate rows
        while(itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            // get the resultat table field
            Joueur j = new Joueur((String) obj[0]);
            j.setMdp((String) obj[1]);
            j.setAge((BigInteger) obj[2]);
            j.setSexe((Character) obj[3]);
            j.setVille((String) obj[4]);
            joueurArray.add(j);
        }
        
        return joueurArray;
    }
    
    /**
     * Return the number of wins of a player
     * @param pseudo the pseudo of the player
     * @return the number of wins
     */
    public int getJoueurNbVictoire(String pseudo) {
        DAO_Resultat daoResultat = new DAO_Resultat();
        List<Resultat> resList;
        resList = daoResultat.getJoueurResultats(pseudo);
        return (int) resList.stream().filter(res -> res.getNbJetonDechargeINT() == 0).count();
    }
    
    /**
     * Return the number of wins of a player.
     * @param j the player.
     * @return the number of wins.
     */
    public int getJoueurNbVictoire(Joueur j) {
        return this.getJoueurNbVictoire(j.getPseudo());
    }
    
    /**
     * Return the number of games played by a player.
     * @param pseudo the pseudo of the player.
     * @return the number of games played.
     */
    public int getJoueurNbPartie(String pseudo) {
        DAO_Resultat daoResultat = new DAO_Resultat();
        List<Resultat> resList;
        resList = daoResultat.getJoueurResultats(pseudo);
        return (int) resList.stream().count();
    }
    
    /**
     * Return the number of games played by a player.
     * @param j  the player.
     * @return the number of games played.
     */
    public int getJoueurNbPartie(Joueur j)  {
        return this.getJoueurNbPartie(j.getPseudo());
    }
    
    /**
     * Return the average wins number.
     * @param pseudo the player pseudo.
     * @return the average wins number.
     */
    public float getJoueurRatio(String pseudo) {
        return this.getJoueurNbVictoire(pseudo) / this.getJoueurNbPartie(pseudo);
    }
    
     /**
     * Return the average wins number.
     * @param j the player.
     * @return the average wins number.
     */
    public float getJoueurRatio(Joueur j) {
        return this.getJoueurRatio(j.getPseudo());
    }
    
    /**
     * Return the average number of tokens at the end of the charge phase for a player.
     * @param pseudo the player pseudo.
     * @return the average.
     */
    public float getNbJetonChargeMoyenJoueur(String pseudo) {
        DAO_Resultat daoResultat = new DAO_Resultat();
        List<Resultat> resList;
        resList = daoResultat.getJoueurResultats(pseudo);
        int nbJetonCharge = resList.stream().mapToInt(res -> res.getNbJetonChargeINT()).sum();
        return nbJetonCharge / resList.size();
    }
    
    /**
     * Return the average number of tokens at the end of the charge phase for a player.
     * @param j the player.
     * @return the average.
     */
    public float getNbJetonChargeMoyenJoueur(Joueur j) {
        return this.getNbJetonChargeMoyenJoueur(j.getPseudo());
    }
    
    /**
     * Return the average number of tokens at the end of the decharge phase for a player.
     * @param pseudo the player pseudo.
     * @return the average.
     */
    public float getNbJetonDechargeMoyenJoueur(String pseudo) {
        DAO_Resultat daoResultat = new DAO_Resultat();
        List<Resultat> resList;
        resList = daoResultat.getJoueurResultats(pseudo);
        int nbJetonDecharge = resList.stream().mapToInt(res -> res.getNbJetonDechargeINT()).sum();
        return nbJetonDecharge / resList.size();
    }
    
    /**
     * Return the average number of tokens at the end of the decharge phase for a player.
     * @param j the player.
     * @return the average.
     */
    public float getNbJetonDechargeMoyenJoueur(Joueur j) {
        return this.getNbJetonDechargeMoyenJoueur(j.getPseudo());
    }
    
}
