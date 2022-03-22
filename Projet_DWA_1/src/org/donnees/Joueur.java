/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.donnees;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fred2
 */
@Entity
@Table(name = "JOUEUR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Joueur.findAll", query = "SELECT j FROM Joueur j"),
    @NamedQuery(name = "Joueur.findByCodeJoueur", query = "SELECT j FROM Joueur j WHERE j.codeJoueur = :codeJoueur"),
    @NamedQuery(name = "Joueur.findByPseudo", query = "SELECT j FROM Joueur j WHERE j.pseudo = :pseudo"),
    @NamedQuery(name = "Joueur.findByMotDePasse", query = "SELECT j FROM Joueur j WHERE j.motDePasse = :motDePasse"),
    @NamedQuery(name = "Joueur.findByAge", query = "SELECT j FROM Joueur j WHERE j.age = :age"),
    @NamedQuery(name = "Joueur.findBySexe", query = "SELECT j FROM Joueur j WHERE j.sexe = :sexe"),
    @NamedQuery(name = "Joueur.findByVille", query = "SELECT j FROM Joueur j WHERE j.ville = :ville")})
public class Joueur implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODE_JOUEUR")
    private BigDecimal codeJoueur;
    @Basic(optional = false)
    @Column(name = "PSEUDO")
    private String pseudo;
    @Basic(optional = false)
    @Column(name = "MOT_DE_PASSE")
    private String motDePasse;
    @Column(name = "AGE")
    private BigInteger age;
    @Column(name = "SEXE")
    private Character sexe;
    @Column(name = "VILLE")
    private String ville;

    public Joueur() {
    }

    public Joueur(BigDecimal codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public Joueur(BigDecimal codeJoueur, String pseudo, String motDePasse) {
        this.codeJoueur = codeJoueur;
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
    }

    public BigDecimal getCodeJoueur() {
        return codeJoueur;
    }

    public void setCodeJoueur(BigDecimal codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public BigInteger getAge() {
        return age;
    }

    public void setAge(BigInteger age) {
        this.age = age;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeJoueur != null ? codeJoueur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Joueur)) {
            return false;
        }
        Joueur other = (Joueur) object;
        if ((this.codeJoueur == null && other.codeJoueur != null) || (this.codeJoueur != null && !this.codeJoueur.equals(other.codeJoueur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.donnees.Joueur[ codeJoueur=" + codeJoueur + " ]";
    }
    
}
