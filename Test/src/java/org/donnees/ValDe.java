/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.donnees;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "VAL_DE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValDe.findAll", query = "SELECT v FROM ValDe v"),
    @NamedQuery(name = "ValDe.findByCodeDe", query = "SELECT v FROM ValDe v WHERE v.codeDe = :codeDe"),
    @NamedQuery(name = "ValDe.findByVal1", query = "SELECT v FROM ValDe v WHERE v.val1 = :val1"),
    @NamedQuery(name = "ValDe.findByVal2", query = "SELECT v FROM ValDe v WHERE v.val2 = :val2"),
    @NamedQuery(name = "ValDe.findByVal3", query = "SELECT v FROM ValDe v WHERE v.val3 = :val3")})
public class ValDe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODE_DE")
    private String codeDe;
    @Column(name = "VAL1")
    private BigInteger val1;
    @Column(name = "VAL2")
    private BigInteger val2;
    @Column(name = "VAL3")
    private BigInteger val3;
    @OneToMany(mappedBy = "codeDe")
    private Collection<LancerCharge> lancerChargeCollection;
    @OneToMany(mappedBy = "codeDe")
    private Collection<LancerDecharge> lancerDechargeCollection;
    @OneToMany(mappedBy = "codeDe")
    private Collection<LanceOrdre> lanceOrdreCollection;

    public ValDe() {
    }

    public ValDe(String codeDe) {
        this.codeDe = codeDe;
    }

    public String getCodeDe() {
        return codeDe;
    }

    public void setCodeDe(String codeDe) {
        this.codeDe = codeDe;
    }

    public BigInteger getVal1() {
        return val1;
    }
    
    public int getVal1INT() {
        return this.getVal1().intValue();
    }

    public void setVal1(BigInteger val1) {
        this.val1 = val1;
    }
    
    public void setVal1(int val1) {
        this.setVal1(BigInteger.valueOf(val1));
    }

    public BigInteger getVal2() {
        return val2;
    }
    
    public int getVal2INT() {
        return this.getVal2().intValue();
    }

    public void setVal2(BigInteger val2) {
        this.val2 = val2;
    }
    
    public void setVal2(int val2) {
        this.setVal2(BigInteger.valueOf(val2));
    }

    public BigInteger getVal3() {
        return val3;
    }
    
    public int getVal3INT() {
        return this.getVal3().intValue();
    }

    public void setVal3(BigInteger val3) {
        this.val3 = val3;
    }
    
    public void setVal3(int val3) {
        this.setVal3(BigInteger.valueOf(val3));
    }

    @XmlTransient
    public Collection<LancerCharge> getLancerChargeCollection() {
        return lancerChargeCollection;
    }

    public void setLancerChargeCollection(Collection<LancerCharge> lancerChargeCollection) {
        this.lancerChargeCollection = lancerChargeCollection;
    }

    @XmlTransient
    public Collection<LancerDecharge> getLancerDechargeCollection() {
        return lancerDechargeCollection;
    }

    public void setLancerDechargeCollection(Collection<LancerDecharge> lancerDechargeCollection) {
        this.lancerDechargeCollection = lancerDechargeCollection;
    }

    @XmlTransient
    public Collection<LanceOrdre> getLanceOrdreCollection() {
        return lanceOrdreCollection;
    }

    public void setLanceOrdreCollection(Collection<LanceOrdre> lanceOrdreCollection) {
        this.lanceOrdreCollection = lanceOrdreCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeDe != null ? codeDe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValDe)) {
            return false;
        }
        ValDe other = (ValDe) object;
        if ((this.codeDe == null && other.codeDe != null) || (this.codeDe != null && !this.codeDe.equals(other.codeDe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.donnees.ValDe[ codeDe=" + codeDe + " ]";
    }
    
}
