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
import org.donnees.LancerDecharge;
import org.donnees.Partie;
import org.donnees.ValDe;

public class DAO_LancerDecharge extends DAO<LancerDecharge> {
    
    public DAO_LancerDecharge() {
        super();
        this.setClass(LancerDecharge.class);
    }

    @Override
    public List<LancerDecharge> findAll() {
        // Output Array
        List<LancerDecharge> lancerDechargeArray = new ArrayList();
        // Request
        Query query = this.entityManager.createQuery("select ld.lancerDechargePK.codePartie, ld.lancerDechargePK.pseudo, ld.lancerDechargePK.numLance, ld.lancerDechargePK.numRound, ld.codeDe from LancerDecharge ld");
        List<LancerDecharge> lancer = (List<LancerDecharge>) query.getResultList();
        Iterator itr = lancer.iterator();
        // iterate rows
        while(itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            // get the resultat table field
            LancerDecharge ld = new LancerDecharge((BigInteger) obj[0], (String) obj[1], (BigInteger) obj[2], (BigInteger) obj[3]);
            ld.setCodeDe((ValDe) obj[4]);
            
            lancerDechargeArray.add(ld);
        }
        
        return lancerDechargeArray;
    }
    
    /**
     * returns the list of throws of the discharge phase.
     * @param codePartie an int.
     * @return the list of throws of the discharge phase.
     */
    public List<LancerDecharge> getPartieLancerDecharge(int codePartie) {
         // Output Array
        List<LancerDecharge> lancerDechargeArray = new ArrayList();
        // Request
        Query query = this.entityManager.createQuery("select ld.lancerDechargePK.codePartie, ld.lancerDechargePK.pseudo, ld.lancerDechargePK.numLance, ld.lancerDechargePK.numRound, ld.codeDe from LancerDecharge ld where ld.lancerDechargePK.codePartie = " + codePartie);
        List<LancerDecharge> lancer = (List<LancerDecharge>) query.getResultList();
        Iterator itr = lancer.iterator();
        // iterate rows
        while(itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            // get the resultat table field
            LancerDecharge ld = new LancerDecharge((BigInteger) obj[0], (String) obj[1], (BigInteger) obj[2], (BigInteger) obj[3]);
            ld.setCodeDe((ValDe) obj[4]);
            
            lancerDechargeArray.add(ld);
        }
        
        return lancerDechargeArray;
    }
    
    /**
     * returns the list of throws of the discharge phase.
     * @param codePartie a BigInteger.
     * @return the list of throws of the discharge phase.
     */
    public List<LancerDecharge> getPartieLancerDecharge(BigInteger codePartie) { 
        return this.getPartieLancerDecharge(codePartie.intValue());
    }
    
    /**
     * returns the list of throws of the discharge phase.
     * @param p a game.
     * @return the list of throws of the discharge phase.
     */
    public List<LancerDecharge> getPartieLancerDecharge(Partie p) {
        return this.getPartieLancerDecharge(p.getCodePartie());
    }
}
