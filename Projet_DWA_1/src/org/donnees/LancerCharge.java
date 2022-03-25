/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.donnees;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fred2
 */
@Entity
@Table(name = "LANCER_CHARGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LancerCharge.findAll", query = "SELECT l FROM LancerCharge l"),
    @NamedQuery(name = "LancerCharge.findByCodePartie", query = "SELECT l FROM LancerCharge l WHERE l.lancerChargePK.codePartie = :codePartie"),
    @NamedQuery(name = "LancerCharge.findByPseudo", query = "SELECT l FROM LancerCharge l WHERE l.lancerChargePK.pseudo = :pseudo"),
    @NamedQuery(name = "LancerCharge.findByNumLance", query = "SELECT l FROM LancerCharge l WHERE l.lancerChargePK.numLance = :numLance")})
public class LancerCharge implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LancerChargePK lancerChargePK;
    @JoinColumn(name = "PSEUDO", referencedColumnName = "PSEUDO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Joueur joueur;
    @JoinColumn(name = "CODE_PARTIE", referencedColumnName = "CODE_PARTIE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partie partie;
    @JoinColumn(name = "CODE_DE", referencedColumnName = "CODE_DE")
    @ManyToOne
    private ValDe codeDe;

    public LancerCharge() {
    }

    public LancerCharge(LancerChargePK lancerChargePK) {
        this.lancerChargePK = lancerChargePK;
    }

    public LancerCharge(BigInteger codePartie, String pseudo, BigInteger numLance) {
        this.lancerChargePK = new LancerChargePK(codePartie, pseudo, numLance);
    }
    
    public LancerCharge(int codePartie, String pseudo, int numLance) {
        this(BigInteger.valueOf(codePartie), pseudo, BigInteger.valueOf(numLance));
    }

    public LancerChargePK getLancerChargePK() {
        return lancerChargePK;
    }

    public void setLancerChargePK(LancerChargePK lancerChargePK) {
        this.lancerChargePK = lancerChargePK;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public ValDe getCodeDe() {
        return codeDe;
    }

    public void setCodeDe(ValDe codeDe) {
        this.codeDe = codeDe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lancerChargePK != null ? lancerChargePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LancerCharge)) {
            return false;
        }
        LancerCharge other = (LancerCharge) object;
        if ((this.lancerChargePK == null && other.lancerChargePK != null) || (this.lancerChargePK != null && !this.lancerChargePK.equals(other.lancerChargePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.donnees.LancerCharge[ lancerChargePK=" + lancerChargePK + " ]";
    }
    
}
