/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.dao;

import java.util.List;
import org.donnees.LancerCharge;

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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
