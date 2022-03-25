/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.dao;

import java.util.List;
import org.donnees.LancerDecharge;

/**
 *
 * @author fred2
 */
public class DAO_LancerDecharge extends DAO<LancerDecharge> {
    
    public DAO_LancerDecharge() {
        super();
        this.setClass(LancerDecharge.class);
    }

    @Override
    public List<LancerDecharge> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
