/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.persistencia;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Familia Jadan Cahueñ
 */
@Entity
@Table(name = "plan")
@NamedQueries({
    @NamedQuery(name = "Plan.findAll", query = "SELECT p FROM Plan p")})
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "valor")
    private Double valor;
    @Column(name = "pvp1")
    private Double pvp1;
    @Column(name = "pvp2")
    private Double pvp2;
    @Column(name = "pvp3")
    private Double pvp3;
    @Column(name = "pvp4")
    private Double pvp4;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "dias")
    private Integer dias;
    @Column(name = "bien")
    private Boolean bien;
     @Column(name = "tipocliente")
    private String tipocliente;
      @Column(name = "nivelcomparticion")
    private String nivelcomparticion;
    
            
    @OneToMany(mappedBy = "plan")
    private Collection<Comisiones> comisionesCollection;
    @OneToMany(mappedBy = "plan")
    private Collection<Detalle> detalleCollection;
    @OneToMany(mappedBy = "plan")
    private Collection<Contratos> contratosCollection;
    @JoinColumn(name = "sucursal", referencedColumnName = "codigo")
    @ManyToOne
    private Sucursal sucursal;

    public Plan() {
    }

    public Plan(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getPvp1() {
        return pvp1;
    }

    public void setPvp1(Double pvp1) {
        this.pvp1 = pvp1;
    }

    public Double getPvp2() {
        return pvp2;
    }

    public void setPvp2(Double pvp2) {
        this.pvp2 = pvp2;
    }

    public Double getPvp3() {
        return pvp3;
    }

    public void setPvp3(Double pvp3) {
        this.pvp3 = pvp3;
    }

    public Double getPvp4() {
        return pvp4;
    }

    public void setPvp4(Double pvp4) {
        this.pvp4 = pvp4;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Boolean getBien() {
        return bien;
    }

    public void setBien(Boolean bien) {
        this.bien = bien;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
    

    public Collection<Comisiones> getComisionesCollection() {
        return comisionesCollection;
    }

    public void setComisionesCollection(Collection<Comisiones> comisionesCollection) {
        this.comisionesCollection = comisionesCollection;
    }

    public Collection<Detalle> getDetalleCollection() {
        return detalleCollection;
    }

    public void setDetalleCollection(Collection<Detalle> detalleCollection) {
        this.detalleCollection = detalleCollection;
    }

    public Collection<Contratos> getContratosCollection() {
        return contratosCollection;
    }

    public void setContratosCollection(Collection<Contratos> contratosCollection) {
        this.contratosCollection = contratosCollection;
    }

    public String getNivelcomparticion() {
        return nivelcomparticion;
    }

    public void setNivelcomparticion(String nivelcomparticion) {
        this.nivelcomparticion = nivelcomparticion;
    }

    public String getTipocliente() {
        return tipocliente;
    }

    public void setTipocliente(String tipocliente) {
        this.tipocliente = tipocliente;
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
        if (!(object instanceof Plan)) {
            return false;
        }
        Plan other = (Plan) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre+" "+tipo;
    }
}
