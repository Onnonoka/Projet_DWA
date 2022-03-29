/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.dao;

import java.util.List;
import org.donnees.ValDe;

/**
 *
 * @author fred2
 */
public class DAO_ValDe extends DAO<ValDe> {
    
    public DAO_ValDe() {
        super();
        this.setClass(ValDe.class);
    }

    @Override
    public List<ValDe> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
