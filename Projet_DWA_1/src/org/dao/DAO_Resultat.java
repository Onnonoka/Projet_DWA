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
import org.donnees.Resultat;

/**
 *
 * @author fred2
 */
public class DAO_Resultat extends DAO<Resultat> {
    
    public DAO_Resultat() {
        super();
        this.setClass(Resultat.class);
    }
    
    /**
     * Return all element from the table RESULTAT
     * @return  all element from the table RESULTAT
     */
    @Override
    public List<Resultat> findAll() {
        // Output Array
        List<Resultat> resultatArray = new ArrayList();
        // Request
        Query query = this.entityManager.createQuery("select r.partie.codePartie, r.joueur.pseudo, r.nbJetonCharge, r.nbJetonDecharge from Resultat r");
        List<Resultat> resultat = (List<Resultat>) query.getResultList();
        Iterator itr = resultat.iterator();
        // iterate rows
        while(itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            // get the resultat table field
            Resultat res = new Resultat((BigInteger) obj[0], (String) obj[1]);
            res.setNbJetonCharge((BigInteger) obj[2]);
            res.setNbJetonDecharge((BigInteger) obj[3]);
            resultatArray.add(res);
        }
        
        return resultatArray;
    }
    
    public List<Resultat> getPartieResultats(int codePartie) {
        // Output Array
        List<Resultat> resultatArray = new ArrayList();
        // Request
        Query query = this.entityManager.createQuery("select r.partie.codePartie, r.joueur.pseudo, r.nbJetonCharge, r.nbJetonDecharge from Resultat r where r.partie.codePartie = " + codePartie);
        List<Resultat> resultat = (List<Resultat>) query.getResultList();
        Iterator itr = resultat.iterator();
        // iterate rows
        while(itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            // get the resultat table field
            Resultat res = new Resultat((BigInteger) obj[0], (String) obj[1]);
            res.setNbJetonCharge((BigInteger) obj[2]);
            res.setNbJetonDecharge((BigInteger) obj[3]);
            resultatArray.add(res);
        }
        
        return resultatArray;
    }
    
    public List<Resultat> getPartieResultats(BigInteger codePartie) {
        return this.getPartieResultats(codePartie.intValue());
    }
    
    public List<Resultat> getJoueurResultats(String pseudo) {
        // Output Array
        List<Resultat> resultatArray = new ArrayList();
        // Request
        Query query = this.entityManager.createQuery("select r.partie.codePartie, r.joueur.pseudo, r.nbJetonCharge, r.nbJetonDecharge from Resultat r where r.joueur.pseudo = '" + pseudo + "'");
        List<Resultat> resultat = (List<Resultat>) query.getResultList();
        Iterator itr = resultat.iterator();
        // iterate rows
        while(itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            // get the resultat table field
            Resultat res = new Resultat((BigInteger) obj[0], (String) obj[1]);
            res.setNbJetonCharge((BigInteger) obj[2]);
            res.setNbJetonDecharge((BigInteger) obj[3]);
            resultatArray.add(res);
        }
        
        return resultatArray;
    }

}
