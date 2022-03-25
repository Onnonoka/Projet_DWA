/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.donnees;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
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
@Table(name = "RESULTAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resultat.findAll", query = "SELECT r FROM Resultat r"),
    @NamedQuery(name = "Resultat.findByCodePartie", query = "SELECT r FROM Resultat r WHERE r.resultatPK.codePartie = :codePartie"),
    @NamedQuery(name = "Resultat.findByPseudo", query = "SELECT r FROM Resultat r WHERE r.resultatPK.pseudo = :pseudo"),
    @NamedQuery(name = "Resultat.findByNbJetonCharge", query = "SELECT r FROM Resultat r WHERE r.nbJetonCharge = :nbJetonCharge"),
    @NamedQuery(name = "Resultat.findByNbJetonDecharge", query = "SELECT r FROM Resultat r WHERE r.nbJetonDecharge = :nbJetonDecharge")})
public class Resultat implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResultatPK resultatPK;
    @Column(name = "NB_JETON_CHARGE")
    private BigInteger nbJetonCharge;
    @Column(name = "NB_JETON_DECHARGE")
    private BigInteger nbJetonDecharge;
    @JoinColumn(name = "PSEUDO", referencedColumnName = "PSEUDO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Joueur joueur;
    @JoinColumn(name = "CODE_PARTIE", referencedColumnName = "CODE_PARTIE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partie partie;

    public Resultat() {
    }

    public Resultat(ResultatPK resultatPK) {
        this.resultatPK = resultatPK;
    }

    public Resultat(BigInteger codePartie, String pseudo) {
        this.resultatPK = new ResultatPK(codePartie, pseudo);
    }
    
    public Resultat(int codePartie, String pseudo) {
        this(BigInteger.valueOf(codePartie), pseudo);
    }

    public ResultatPK getResultatPK() {
        return resultatPK;
    }

    public void setResultatPK(ResultatPK resultatPK) {
        this.resultatPK = resultatPK;
    }

    public BigInteger getNbJetonCharge() {
        return nbJetonCharge;
    }
    
    public int getNbJetonChargeINT() {
        return this.getNbJetonCharge().intValue();
    }

    public void setNbJetonCharge(BigInteger nbJetonCharge) {
        this.nbJetonCharge = nbJetonCharge;
    }
    
    public void setNbJetonCharge(int nbJetonCharge) {
        this.setNbJetonCharge(BigInteger.valueOf(nbJetonCharge));
    }

    public BigInteger getNbJetonDecharge() {
        return nbJetonDecharge;
    }
    
    public int getNbJetonDechargeINT() {
        return this.getNbJetonDecharge().intValue();
    }

    public void setNbJetonDecharge(BigInteger nbJetonDecharge) {
        this.nbJetonDecharge = nbJetonDecharge;
    }
    
    public void setNbJetonDecharge(int nbJetonDecharge) {
        this.setNbJetonDecharge(BigInteger.valueOf(nbJetonDecharge));
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resultatPK != null ? resultatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resultat)) {
            return false;
        }
        Resultat other = (Resultat) object;
        if ((this.resultatPK == null && other.resultatPK != null) || (this.resultatPK != null && !this.resultatPK.equals(other.resultatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.donnees.Resultat[ resultatPK=" + resultatPK + " ]";
    }
    
}
