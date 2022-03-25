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
@Table(name = "LANCE_ORDRE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LanceOrdre.findAll", query = "SELECT l FROM LanceOrdre l"),
    @NamedQuery(name = "LanceOrdre.findByPseudo", query = "SELECT l FROM LanceOrdre l WHERE l.lanceOrdrePK.pseudo = :pseudo"),
    @NamedQuery(name = "LanceOrdre.findByCodePartie", query = "SELECT l FROM LanceOrdre l WHERE l.lanceOrdrePK.codePartie = :codePartie")})
public class LanceOrdre implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LanceOrdrePK lanceOrdrePK;
    @JoinColumn(name = "CODE_PARTIE", referencedColumnName = "CODE_PARTIE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partie partie;
    @JoinColumn(name = "CODE_DE", referencedColumnName = "CODE_DE")
    @ManyToOne
    private ValDe codeDe;

    public LanceOrdre() {
    }

    public LanceOrdre(LanceOrdrePK lanceOrdrePK) {
        this.lanceOrdrePK = lanceOrdrePK;
    }

    public LanceOrdre(String pseudo, BigInteger codePartie) {
        this.lanceOrdrePK = new LanceOrdrePK(pseudo, codePartie);
    }
    
    public LanceOrdre(String pseudo, int codePartie) {
        this(pseudo, BigInteger.valueOf(codePartie));
    }

    public LanceOrdrePK getLanceOrdrePK() {
        return lanceOrdrePK;
    }

    public void setLanceOrdrePK(LanceOrdrePK lanceOrdrePK) {
        this.lanceOrdrePK = lanceOrdrePK;
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
        hash += (lanceOrdrePK != null ? lanceOrdrePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LanceOrdre)) {
            return false;
        }
        LanceOrdre other = (LanceOrdre) object;
        if ((this.lanceOrdrePK == null && other.lanceOrdrePK != null) || (this.lanceOrdrePK != null && !this.lanceOrdrePK.equals(other.lanceOrdrePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.donnees.LanceOrdre[ lanceOrdrePK=" + lanceOrdrePK + " ]";
    }
    
}
