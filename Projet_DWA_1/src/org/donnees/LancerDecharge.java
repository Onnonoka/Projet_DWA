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

@Entity
@Table(name = "LANCER_DECHARGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LancerDecharge.findAll", query = "SELECT l FROM LancerDecharge l"),
    @NamedQuery(name = "LancerDecharge.findByCodePartie", query = "SELECT l FROM LancerDecharge l WHERE l.lancerDechargePK.codePartie = :codePartie"),
    @NamedQuery(name = "LancerDecharge.findByPseudo", query = "SELECT l FROM LancerDecharge l WHERE l.lancerDechargePK.pseudo = :pseudo"),
    @NamedQuery(name = "LancerDecharge.findByNumLance", query = "SELECT l FROM LancerDecharge l WHERE l.lancerDechargePK.numLance = :numLance"),
    @NamedQuery(name = "LancerDecharge.findByNumRound", query = "SELECT l FROM LancerDecharge l WHERE l.lancerDechargePK.numRound = :numRound")})
public class LancerDecharge implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LancerDechargePK lancerDechargePK;
    @JoinColumn(name = "PSEUDO", referencedColumnName = "PSEUDO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Joueur joueur;
    @JoinColumn(name = "CODE_PARTIE", referencedColumnName = "CODE_PARTIE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partie partie;
    @JoinColumn(name = "CODE_DE", referencedColumnName = "CODE_DE")
    @ManyToOne
    private ValDe codeDe;

    public LancerDecharge() {
    }

    public LancerDecharge(LancerDechargePK lancerDechargePK) {
        this.lancerDechargePK = lancerDechargePK;
    }

    public LancerDecharge(BigInteger codePartie, String pseudo, BigInteger numLance, BigInteger numRound) {
        this.lancerDechargePK = new LancerDechargePK(codePartie, pseudo, numLance, numRound);
    }
    
    public LancerDecharge(int codePartie, String pseudo, int numLance, int numRound) {
        this(BigInteger.valueOf(codePartie), pseudo, BigInteger.valueOf(numLance), BigInteger.valueOf(numRound));
    }

    public LancerDechargePK getLancerDechargePK() {
        return lancerDechargePK;
    }

    public void setLancerDechargePK(LancerDechargePK lancerDechargePK) {
        this.lancerDechargePK = lancerDechargePK;
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
        hash += (lancerDechargePK != null ? lancerDechargePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LancerDecharge)) {
            return false;
        }
        LancerDecharge other = (LancerDecharge) object;
        if ((this.lancerDechargePK == null && other.lancerDechargePK != null) || (this.lancerDechargePK != null && !this.lancerDechargePK.equals(other.lancerDechargePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.donnees.LancerDecharge[ lancerDechargePK=" + lancerDechargePK + " ]";
    }
    
}
