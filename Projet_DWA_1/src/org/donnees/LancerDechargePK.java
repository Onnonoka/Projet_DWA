/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.donnees;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LancerDechargePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CODE_PARTIE")
    private BigInteger codePartie;
    @Basic(optional = false)
    @Column(name = "PSEUDO")
    private String pseudo;
    @Basic(optional = false)
    @Column(name = "NUM_LANCE")
    private BigInteger numLance;
    @Basic(optional = false)
    @Column(name = "NUM_ROUND")
    private BigInteger numRound;

    public LancerDechargePK() {
    }

    public LancerDechargePK(BigInteger codePartie, String pseudo, BigInteger numLance, BigInteger numRound) {
        this.codePartie = codePartie;
        this.pseudo = pseudo;
        this.numLance = numLance;
        this.numRound = numRound;
    }
    
    public LancerDechargePK(int codePartie, String pseudo, int numLance, int numRound) {
        this(BigInteger.valueOf(codePartie), pseudo, BigInteger.valueOf(numLance), BigInteger.valueOf(numRound));
    }

    public BigInteger getCodePartie() {
        return codePartie;
    }
    
    public int getCodePartieINT() {
        return this.getCodePartie().intValue();
    }

    public void setCodePartie(BigInteger codePartie) {
        this.codePartie = codePartie;
    }
    
    public void setCodePartie(int codePartie) {
        this.setCodePartie(BigInteger.valueOf(codePartie));
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public BigInteger getNumLance() {
        return numLance;
    }
    
    public int getNumLanceINT() {
        return this.getNumLance().intValue();
    }

    public void setNumLance(BigInteger numLance) {
        this.numLance = numLance;
    }
    
    public void setNumLance(int numLance) {
        this.setNumLance(BigInteger.valueOf(numLance));
    }

    public BigInteger getNumRound() {
        return numRound;
    }
    
    public int getNumRoundINT() {
        return this.getNumRound().intValue();
    }

    public void setNumRound(BigInteger numRound) {
        this.numRound = numRound;
    }
    
    public void setNumRound(int numRound) {
        this.setNumRound(BigInteger.valueOf(numRound));
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codePartie != null ? codePartie.hashCode() : 0);
        hash += (pseudo != null ? pseudo.hashCode() : 0);
        hash += (numLance != null ? numLance.hashCode() : 0);
        hash += (numRound != null ? numRound.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LancerDechargePK)) {
            return false;
        }
        LancerDechargePK other = (LancerDechargePK) object;
        if ((this.codePartie == null && other.codePartie != null) || (this.codePartie != null && !this.codePartie.equals(other.codePartie))) {
            return false;
        }
        if ((this.pseudo == null && other.pseudo != null) || (this.pseudo != null && !this.pseudo.equals(other.pseudo))) {
            return false;
        }
        if ((this.numLance == null && other.numLance != null) || (this.numLance != null && !this.numLance.equals(other.numLance))) {
            return false;
        }
        if ((this.numRound == null && other.numRound != null) || (this.numRound != null && !this.numRound.equals(other.numRound))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.donnees.LancerDechargePK[ codePartie=" + codePartie + ", pseudo=" + pseudo + ", numLance=" + numLance + ", numRound=" + numRound + " ]";
    }
    
}
