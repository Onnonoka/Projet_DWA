/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.dao;

import org.donnees.Joueur;

/**
 *
 * @author fred2
 */
public class DAO_JPA_JOUEUR extends DAO<Joueur> {
    
    public DAO_JPA_JOUEUR() {
        setClass(Joueur.class);
    }
    
}
