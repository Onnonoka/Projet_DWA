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
import org.donnees.LanceOrdre;
import org.donnees.Partie;
import org.donnees.ValDe;

public class DAO_LanceOrdre extends DAO<LanceOrdre> {
    
    public DAO_LanceOrdre() {
        super();
        this.setClass(LanceOrdre.class);
    }

    @Override
    public List<LanceOrdre> findAll() {
        // Output Array
        List<LanceOrdre> lanceOrdreArray = new ArrayList();
        // Request
        Query query = this.entityManager.createQuery("select lo.lanceOrdrePK.pseudo, lo.lanceOrdrePK.codePartie,  lo.lanceOrdrePK.numLance, lo.codeDe from LanceOrdre lo");
        List<LanceOrdre> lance = (List<LanceOrdre>) query.getResultList();
        Iterator itr = lance.iterator();
        // iterate rows
        while(itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            // get the resultat table field
            LanceOrdre lo = new LanceOrdre((String) obj[0], (BigInteger) obj[1], (BigInteger) obj[2]);
            lo.setCodeDe((ValDe) obj[3]);
            
            lanceOrdreArray.add(lo);
        }
        
        return lanceOrdreArray;
    }
    
    /**
     * returns the list of throws to choose the order of the players.
     * @param codePartie an int.
     * @return the list of throws to choose the order of the players.
     */
    public List<LanceOrdre> getPartieLanceOrdre(int codePartie) {
        // Output Array
        List<LanceOrdre> lanceOrdreArray = new ArrayList();
        // Request
        Query query = this.entityManager.createQuery("select lo.lanceOrdrePK.pseudo, lo.lanceOrdrePK.codePartie,  lo.lanceOrdrePK.numLance, lo.codeDe from LanceOrdre lo where lo.lanceOrdrePK.codePartie = " + codePartie);
        List<LanceOrdre> lance = (List<LanceOrdre>) query.getResultList();
        Iterator itr = lance.iterator();
        // iterate rows
        while(itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            // get the resultat table field
            LanceOrdre lo = new LanceOrdre((String) obj[0], (BigInteger) obj[1],  (BigInteger) obj[2]);
            lo.setCodeDe((ValDe) obj[3]);
            
            lanceOrdreArray.add(lo);
        }
        
        return lanceOrdreArray;
    }
    
    /**
     * returns the list of throws to choose the order of the players.
     * @param codePartie a BigInteger.
     * @return the list of throws to choose the order of the players.
     */
    public List<LanceOrdre> getPartieLanceOrdre(BigInteger codePartie) {
        return this.getPartieLanceOrdre(codePartie.intValue());
    }
    
    /**
     * returns the list of throws to choose the order of the players.
     * @param p a game.
     * @return the list of throws to choose the order of the players.
     */
    public List<LanceOrdre> getPartieLanceOrdre(Partie p) {
        return this.getPartieLanceOrdre(p.getCodePartie());
    }
}
