/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.dao;

import java.util.List;
import org.donnees.Partie;

/**
 *
 * @author fred2
 */
public class DAO_Partie extends DAO<Partie> {
    
    public DAO_Partie() {
        super();
        this.setClass(Partie.class);
    }

    @Override
    public List<Partie> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
