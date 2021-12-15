/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sp6;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vit
 */
@Entity
@Table(name = "lidi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lidi.findAll", query = "SELECT l FROM Lidi l"),
    @NamedQuery(name = "Lidi.findById", query = "SELECT l FROM Lidi l WHERE l.id = :id"),
    @NamedQuery(name = "Lidi.findByJmeno", query = "SELECT l FROM Lidi l WHERE l.jmeno = :jmeno"),
    @NamedQuery(name = "Lidi.findByPrijmeni", query = "SELECT l FROM Lidi l WHERE l.prijmeni = :prijmeni")})
public class Lidi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Jmeno")
    private String jmeno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Prijmeni")
    private String prijmeni;

    public Lidi() {
    }

    public Lidi(Integer id) {
        this.id = id;
    }

    public Lidi(Integer id, String jmeno, String prijmeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lidi)) {
            return false;
        }
        Lidi other = (Lidi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.sp6.Lidi[ id=" + id + " ]";
    }
    
}
