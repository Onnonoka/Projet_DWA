/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.donnees;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "PARTIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partie.findAll", query = "SELECT p FROM Partie p"),
    @NamedQuery(name = "Partie.findByCodePartie", query = "SELECT p FROM Partie p WHERE p.codePartie = :codePartie"),
    @NamedQuery(name = "Partie.findByDateDeb", query = "SELECT p FROM Partie p WHERE p.dateDeb = :dateDeb"),
    @NamedQuery(name = "Partie.findByDateFin", query = "SELECT p FROM Partie p WHERE p.dateFin = :dateFin")})
public class Partie implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODE_PARTIE")
    private BigInteger codePartie;
    @Column(name = "DATE_DEB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDeb;
    @Column(name = "DATE_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partie")
    private Collection<Resultat> resultatCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partie")
    private Collection<LancerCharge> lancerChargeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partie")
    private Collection<LancerDecharge> lancerDechargeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partie")
    private Collection<LanceOrdre> lanceOrdreCollection;

    public Partie() {
    }

    public Partie(BigInteger codePartie) {
        this.codePartie = codePartie;
    }
    
    public Partie(int codePartie) {
        this(BigInteger.valueOf(codePartie));
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

    public Date getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(Date dateDeb) {
        this.dateDeb = dateDeb;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
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
        hash += (codePartie != null ? codePartie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partie)) {
            return false;
        }
        Partie other = (Partie) object;
        if ((this.codePartie == null && other.codePartie != null) || (this.codePartie != null && !this.codePartie.equals(other.codePartie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.donnees.Partie[ codePartie=" + codePartie + " ]";
    }

    public void dateDeb(Date date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
