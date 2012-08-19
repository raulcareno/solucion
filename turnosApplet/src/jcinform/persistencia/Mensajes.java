/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "mensajes")
@NamedQueries({
    @NamedQuery(name = "Mensajes.findAll", query = "SELECT m FROM Mensajes m")})
public class Mensajes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "titulo")
    private String titulo;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "duracion")
    private Integer duracion;
    @Column(name = "orden")
    private Integer orden;
    @JoinColumn(name = "video", referencedColumnName = "codigo")
    @ManyToOne
    private Videos video;

    public Mensajes() {
    }

    public Mensajes(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Videos getVideo() {
        return video;
    }

    public void setVideo(Videos video) {
        this.video = video;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensajes)) {
            return false;
        }
        Mensajes other = (Mensajes) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.Persistencia.Mensajes[ codigo=" + codigo + " ]";
    }
    
}
