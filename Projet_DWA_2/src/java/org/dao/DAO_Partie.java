/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.dao;


import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.persistence.Query;
import org.donnees.Joueur;
import org.donnees.Partie;
import org.donnees.Resultat;

public class DAO_Partie extends DAO<Partie> {
    
    public DAO_Partie() {
        super();
        this.setClass(Partie.class);
    }

    @Override
    public List<Partie> findAll() {
        // Output Array
        List<Partie> partieArray = new ArrayList();
        // Request
        Query query = entityManager.createQuery("select p.codePartie, p.dateDeb, p.dateFin from Partie p");
        List<Partie> partie = (List<Partie>) query.getResultList();
        Iterator itr = partie.iterator();
        // iterate rows
        while(itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            // get the resultat table field
            Partie p = new Partie((BigInteger) obj[0]);
            p.setDateDeb((Date) obj[1]);
            p.setDateFin((Date) obj[2]);
            partieArray.add(p);
        }
        return partieArray;
    }
    
    
    /**
     * Return the winner of the game.
     * @param codePartie the game.
     * @return the winner's pseudo.
     */
    public Joueur getGagnantPartie (BigInteger codePartie){
        DAO_Resultat daoResultat = new DAO_Resultat();
        DAO_Joueur daoJoueur = new DAO_Joueur();
        List<Resultat> resList;
        resList = daoResultat.getPartieResultats(codePartie);
        Resultat res = (Resultat) resList.stream().filter(rest -> rest.getNbJetonDechargeINT() == 0).toArray()[0];
        return daoJoueur.find(res.getResultatPK().getPseudo());
    }
    
    public Joueur getGagnantPartie (int codePartie){
        return this.getGagnantPartie(BigInteger.valueOf(codePartie));
    }
    
    /** Return the winner of the game.
     * @param p the game.
     * @return the winner's pseudo.
     */
    public Joueur getGagnantPartie (Partie p){
        return this.getGagnantPartie(p.getCodePartie());
    }
    
    /** Return the winner of the game.
     * @param p the game.
     * @return the winner's pseudo.
     */
    public String getGagnantPartiePseudo (Partie p){
        return this.getGagnantPartie(p.getCodePartie()).getPseudo();
    }
    
    /** Return the list of the players in the game.
     * @param codePartie the game.
     * @return the list of the players in the game.
     */
    public List<Joueur> getPartieJoueur (BigInteger codePartie){
        DAO_Resultat daoResultat = new DAO_Resultat();
        DAO_Joueur daoJoueur = new DAO_Joueur();
        List<Resultat> resList;
        List<Joueur> joueurlist = new ArrayList();
        resList = daoResultat.getPartieResultats(codePartie);
        resList.stream().forEach(res -> joueurlist.add(daoJoueur.find(res.getResultatPK().getPseudo())));
        return joueurlist;
    }

    /** Return the list of the players in the game.
     * @param codePartie the game.
     * @return the list of the players in the game.
     */
    public List<Joueur> getPartieJoueur (int codePartie){
        return this.getPartieJoueur(BigInteger.valueOf(codePartie));
    }
    
    /** Return the list of the players in the game.
     * @param p the game.
     * @return the list of the players in the game.
     */
    public List<Joueur> getPartieJoueur (Partie p){
        return this.getPartieJoueur(p.getCodePartie());
    }
}
