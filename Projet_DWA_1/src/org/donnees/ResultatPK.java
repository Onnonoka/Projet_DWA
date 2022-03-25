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

/**
 *
 * @author fred2
 */
@Embeddable
public class ResultatPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CODE_PARTIE")
    private BigInteger codePartie;
    @Basic(optional = false)
    @Column(name = "PSEUDO")
    private String pseudo;

    public ResultatPK() {
    }

    public ResultatPK(BigInteger codePartie, String pseudo) {
        this.codePartie = codePartie;
        this.pseudo = pseudo;
    }
    
    public ResultatPK(int codePartie, String pseudo) {
        this(BigInteger.valueOf(codePartie), pseudo);
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codePartie != null ? codePartie.hashCode() : 0);
        hash += (pseudo != null ? pseudo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResultatPK)) {
            return false;
        }
        ResultatPK other = (ResultatPK) object;
        if ((this.codePartie == null && other.codePartie != null) || (this.codePartie != null && !this.codePartie.equals(other.codePartie))) {
            return false;
        }
        if ((this.pseudo == null && other.pseudo != null) || (this.pseudo != null && !this.pseudo.equals(other.pseudo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.donnees.ResultatPK[ codePartie=" + codePartie + ", pseudo=" + pseudo + " ]";
    }
    
}
