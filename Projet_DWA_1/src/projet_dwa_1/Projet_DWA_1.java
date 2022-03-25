/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_dwa_1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.dao.DAOException;
import org.dao.DAO_Joueur;
import org.dao.DAO_Partie;
import org.dao.DAO_Resultat;
import org.donnees.Joueur;
import org.donnees.Partie;
import org.donnees.Resultat;
import org.donnees.ResultatPK;

/**
 *
 * @author fred2
 */
public class Projet_DWA_1 {
    
    public static void main(String[] args) throws DAOException {

            DAO_Joueur daoJoueur = new DAO_Joueur();
            DAO_Resultat daoResultat = new DAO_Resultat();
            DAO_Partie daoPartie = new DAO_Partie();
            
            Joueur j1 = new Joueur();
            Partie p1 = new Partie();
            ResultatPK res1PK = new ResultatPK();
            Resultat res1 = new Resultat();
            
            List<Resultat> resList;
            List<Joueur> jList;

            /*j1.setPseudo("Ono");
            j1.setMdp("010203");
            j1.setAge(BigInteger.valueOf(24));
            j1.setSexe('H');
            j1.setVille("Pau");
            
            p1.setCodePartie(BigDecimal.valueOf(10));
            
            res1PK.setCodePartie(p1.getCodePartie().toBigInteger());
            res1PK.setPseudo(j1.getPseudo());
            res1.setResultatPK(res1PK);
            res1.setNbJetonCharge(BigInteger.valueOf(11));
            res1.setNbJetonDecharge(BigInteger.valueOf(2));
            
            daoJoueur.create(j1);
            daoPartie.create(p1);
            daoResultat.create(res1);*/
            
            j1 = daoJoueur.find("Ono");
            System.out.println(j1.getPseudo() + " " + j1.getMdp());
            
            p1 = daoPartie.find(BigInteger.valueOf(10));
            System.out.println(p1.getCodePartie());
            
            resList = daoResultat.getPartieResultats(10);
            System.out.println(resList);
            
            resList = daoResultat.getJoueurResultats("Ono");
            System.out.println("Request 2 : " + resList);
            
            resList = daoResultat.findAll();
            System.out.println("Request 3 : " + resList);
            
            jList = daoJoueur.findAll();
            System.out.println("Request 4 : " + jList);
            
            int nbWin = daoJoueur.getJoueurNbVictoire(j1);
            System.out.println(nbWin);
            
            int nbGame = daoJoueur.getJoueurNbPartie(j1);
            System.out.println(nbGame);
            
            float ratio = daoJoueur.getJoueurRatio(j1);
            System.out.println(ratio);
            
            float ratioCharge = daoJoueur.getNbJetonChargeMoyenJoueur(j1);
            System.out.println(ratioCharge);
            
            float ratioDecharge = daoJoueur.getNbJetonDechargeMoyenJoueur(j1);
            System.out.println(ratioDecharge);
            
    }
}
