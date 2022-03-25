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
import org.donnees.LancerCharge;
import org.donnees.Partie;
import org.donnees.ValDe;

/**
 *
 * @author fred2
 */
public class DAO_LancerCharge extends DAO<LancerCharge> {
    
    public DAO_LancerCharge() {
        super();
        this.setClass(LancerCharge.class);
    }

    @Override
    public List<LancerCharge> findAll() {
        // Output Array
        List<LancerCharge> lancerChargeArray = new ArrayList();
        // Request
        Query query = this.entityManager.createQuery("select lc.lancerChargePK.codePartie, lc.lancerChargePK.pseudo, lc.lancerChargePK.numLance, lc.codeDe from LancerCharge lc");
        List<LancerCharge> lancer = (List<LancerCharge>) query.getResultList();
        Iterator itr = lancer.iterator();
        // iterate rows
        while(itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            // get the resultat table field
            LancerCharge lc = new LancerCharge((BigInteger) obj[0], (String) obj[1], (BigInteger) obj[2]);
            lc.setCodeDe((ValDe) obj[3]);
            
            lancerChargeArray.add(lc);
        }
        
        return lancerChargeArray;
    }
    
    public List<LancerCharge> getPartieLancerCharge(int codePartie) {
        // Output Array
        List<LancerCharge> lancerChargeArray = new ArrayList();
        // Request
        Query query = this.entityManager.createQuery("select lc.lancerChargePK.codePartie, lc.lancerChargePK.pseudo, lc.lancerChargePK.numLance, lc.codeDe from LancerCharge lc where lc.lancerChargePK.codePartie = " + codePartie);
        List<LancerCharge> lancer = (List<LancerCharge>) query.getResultList();
        Iterator itr = lancer.iterator();
        // iterate rows
        while(itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            // get the resultat table field
            LancerCharge lc = new LancerCharge((BigInteger) obj[0], (String) obj[1], (BigInteger) obj[2]);
            lc.setCodeDe((ValDe) obj[3]);
            
            lancerChargeArray.add(lc);
        }
        
        return lancerChargeArray;
    }
    
    public List<LancerCharge> getPartieLancerCharge(BigInteger codePartie) {
        return this.getPartieLancerCharge(codePartie.intValue());
    }
    
    public List<LancerCharge> getPartieLancerCharge(Partie p) {
        return this.getPartieLancerCharge(p.getCodePartie());
    }
}
