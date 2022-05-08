/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_dwa_1;

import java.math.BigInteger;
import java.util.List;
import org.dao.DAO_Joueur;
import org.dao.DAO_LanceOrdre;
import org.dao.DAO_LancerCharge;
import org.dao.DAO_LancerDecharge;
import org.dao.DAO_Partie;
import org.dao.DAO_Resultat;
import org.dao.DAO_ValDe;
import org.donnees.Joueur;
import org.donnees.LanceOrdre;
import org.donnees.LanceOrdrePK;
import org.donnees.LancerCharge;
import org.donnees.LancerDecharge;
import org.donnees.Partie;
import org.donnees.Resultat;
import org.donnees.ResultatPK;
import org.donnees.ValDe;

public class Projet_DWA_1 {
    
    public static void main(String[] args) {

            DAO_Joueur daoJoueur = new DAO_Joueur();
            DAO_Resultat daoResultat = new DAO_Resultat();
            DAO_Partie daoPartie = new DAO_Partie();
            DAO_ValDe daoValDe = new DAO_ValDe();
            DAO_LanceOrdre daoLO = new DAO_LanceOrdre();
            DAO_LancerCharge daoLC = new DAO_LancerCharge();
            DAO_LancerDecharge daoLD = new DAO_LancerDecharge();
            
            Joueur j1 = new Joueur();
            Partie p1 = new Partie();
            ResultatPK res1PK = new ResultatPK();
            Resultat res1 = new Resultat();
            ValDe d1 = new ValDe();
            LanceOrdre lo;
            LanceOrdrePK loPK;
            LancerCharge lc;
            LancerDecharge ld;
            
            List<Resultat> resList;
            List<Joueur> jList;
            List<LanceOrdre> loList;
            List<LancerCharge> lcList;
            List<LancerDecharge> ldList;

            /*try {
                j1.setPseudo("Ono");
                j1.setMdp("010203");
                j1.setAge(BigInteger.valueOf(24));
                j1.setSexe('H');
                j1.setVille("Pau");
                daoJoueur.create(j1);
            } catch (Exception e) {
                System.out.println("Erreur");
            }*/
            /*
            
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
            System.out.println(j1.getPseudo() + " " + j1.getMdp());/*
            
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
            
            /*d1.setCodeDe("123");
            d1.setVal1(1);
            d1.setVal2(2);
            d1.setVal3(3);
            daoValDe.create(d1);
            d1 = daoValDe.find("123");
            lo = new LanceOrdre(j1.getPseudo(), p1.getCodePartie());
            lo.setCodeDe(d1);
            daoLO.create(lo);
            loPK = new LanceOrdrePK(j1.getPseudo(), p1.getCodePartie());
            loList = daoLO.findAll();
            loList = daoLO.getPartieLanceOrdre(p1);
            System.out.println(loList);*/
            /*d1 = daoValDe.find("123");
            
            lc = new LancerCharge(p1.getCodePartieINT(), j1.getPseudo(), 1);
            lc.setCodeDe(d1);
            daoLC.create(lc);
            
            ld = new LancerDecharge(p1.getCodePartieINT(), j1.getPseudo(), 1, 1);
            lc.setCodeDe(d1);
            daoLD.create(ld);*/
            
            /*lcList = daoLC.findAll();
            System.out.println("Requete 1 : " + lcList);
            lcList = daoLC.getPartieLancerCharge(p1);
            System.out.println("Requete 2 : " + lcList);
            
            ldList = daoLD.findAll();
            System.out.println("Requete 3 : " + ldList);
            ldList = daoLD.getPartieLancerDecharge(p1);
            System.out.println("Requete 4 : " + ldList);
            /*j1 = new Joueur("Blanero");
            daoJoueur.create(j1);
            res1 = new Resultat(p1.getCodePartie(), j1.getPseudo());
            res1.setNbJetonDecharge(0);
            daoResultat.create(res1);*/
           /* System.out.println(daoPartie.getPartieJoueur(p1));
            System.out.println(daoPartie.getGagnantPartie(p1.getCodePartie()));
            
            System.out.println("--------------------------------------");
            Joueur player = daoJoueur.find("azer");
            System.out.println("Player = " + player);*/
           /*j1.setPseudo("Ono");
           j1.setMdp("123");
           daoJoueur.create(j1);*/
    }
}
