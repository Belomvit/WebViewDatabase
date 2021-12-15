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
@Table(name = "own")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Own.findAll", query = "SELECT o FROM Own o"),
    @NamedQuery(name = "Own.findByIdLidi", query = "SELECT o FROM Own o WHERE o.idLidi = :idLidi"),
    @NamedQuery(name = "Own.findByIdBook", query = "SELECT o FROM Own o WHERE o.idBook = :idBook"),
    @NamedQuery(name = "Own.findByDate", query = "SELECT o FROM Own o WHERE o.date = :date"),
    @NamedQuery(name = "Own.findById", query = "SELECT o FROM Own o WHERE o.id = :id")})
public class Own implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "id_lidi")
    private Integer idLidi;
    @Column(name = "id_book")
    private Integer idBook;
    @Size(max = 45)
    @Column(name = "date")
    private String date;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;

    public Own() {
    }

    public Own(Integer id) {
        this.id = id;
    }
    
    public Own(Integer id, Integer idLidi, Integer idBook, String date ) {
        this.id = id;
        this.idLidi = idLidi;
        this.idBook = idBook;
        this.date = date;
    }

    public Integer getIdLidi() {
        return idLidi;
    }

    public void setIdLidi(Integer idLidi) {
        this.idLidi = idLidi;
    }

    public Integer getIdBook() {
        return idBook;
    }

    public void setIdBook(Integer idBook) {
        this.idBook = idBook;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Own)) {
            return false;
        }
        Own other = (Own) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.sp6.Own[ id=" + id + " ]";
    }
    
}
