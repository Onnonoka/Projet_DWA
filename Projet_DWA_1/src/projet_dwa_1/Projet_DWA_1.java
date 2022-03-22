/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_dwa_1;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.dao.DAOException;
import org.dao.DAO_JPA_JOUEUR;
import org.donnees.Joueur;

/**
 *
 * @author fred2
 */
public class Projet_DWA_1 {
    
    public static void main(String[] args) throws DAOException {
        DAO_JPA_JOUEUR sqlJoueur = new DAO_JPA_JOUEUR();
        Joueur joueur1 = new Joueur();
        
        joueur1.setCodeJoueur(BigDecimal.ONE);
        joueur1.setPseudo("Ono");
        /*joueur1.setMotDePasse("010203");
        joueur1.setSexe('H');
        joueur1.setVille("Pau");
        joueur1.setAge(new BigInteger("24"));*/
        
        sqlJoueur.create(joueur1);
        
    }
}
