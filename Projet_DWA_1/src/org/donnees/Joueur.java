/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.donnees;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fred2
 */
@Entity
@Table(name = "JOUEUR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Joueur.findAll", query = "SELECT j FROM Joueur j"),
    @NamedQuery(name = "Joueur.findByPseudo", query = "SELECT j FROM Joueur j WHERE j.pseudo = :pseudo"),
    @NamedQuery(name = "Joueur.findByMdp", query = "SELECT j FROM Joueur j WHERE j.mdp = :mdp"),
    @NamedQuery(name = "Joueur.findByAge", query = "SELECT j FROM Joueur j WHERE j.age = :age"),
    @NamedQuery(name = "Joueur.findBySexe", query = "SELECT j FROM Joueur j WHERE j.sexe = :sexe"),
    @NamedQuery(name = "Joueur.findByVille", query = "SELECT j FROM Joueur j WHERE j.ville = :ville")})
public class Joueur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PSEUDO")
    private String pseudo;
    @Column(name = "MDP")
    private String mdp;
    @Column(name = "AGE")
    private BigInteger age;
    @Column(name = "SEXE")
    private Character sexe;
    @Column(name = "VILLE")
    private String ville;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "joueur")
    private Collection<Resultat> resultatCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "joueur")
    private Collection<LancerCharge> lancerChargeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "joueur")
    private Collection<LancerDecharge> lancerDechargeCollection;

    public Joueur() {
    }

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public BigInteger getAge() {
        return age;
    }
    
    public int getAgeINT() {
        return this.getAge().intValue();
    }

    public void setAge(BigInteger age) {
        this.age = age;
    }
    
    public void setAge(int age) {
        this.setAge(BigInteger.valueOf(age));
    }

    public Character getSexe() {
        return sexe;
    }

    public void setSexe(Character sexe) {
        this.sexe = sexe;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @XmlTransient
    public Collection<Resultat> getResultatCollection() {
        return resultatCollection;
    }

    public void setResultatCollection(Collection<Resultat> resultatCollection) {
        this.resultatCollection = resultatCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pseudo != null ? pseudo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Joueur)) {
            return false;
        }
        Joueur other = (Joueur) object;
        if ((this.pseudo == null && other.pseudo != null) || (this.pseudo != null && !this.pseudo.equals(other.pseudo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.donnees.Joueur[ pseudo=" + pseudo + " ]";
    }
    
}
