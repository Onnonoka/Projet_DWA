/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.dao;

import java.util.List;
import org.donnees.LanceOrdre;

/**
 *
 * @author fred2
 */
public class DAO_LanceOrdre extends DAO<LanceOrdre> {
    
    public DAO_LanceOrdre() {
        super();
        this.setClass(LanceOrdre.class);
    }

    @Override
    public List<LanceOrdre> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
