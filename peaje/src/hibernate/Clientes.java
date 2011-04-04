/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Familia Jadan Cahueñ
 */
@Entity
@Table(name = "clientes")
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c")})
public class Clientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "ultimoacceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoacceso;
    @Column(name = "acceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date acceso;
    @Column(name = "tarifamensual")
    private Double tarifamensual;
    @Column(name = "valor")
    private BigDecimal valor;
    @JoinColumn(name = "producto", referencedColumnName = "codigo")
    @ManyToOne
    private Productos productos;
//    @OneToMany(mappedBy = "clientes")
//    private Collection<Factura> facturaCollection;
//    @OneToMany(mappedBy = "clientes")
//    private Collection<Tarjetas> tarjetasCollection;

    public Clientes() {
    }

    public Clientes(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Date getUltimoacceso() {
        return ultimoacceso;
    }

    public void setUltimoacceso(Date ultimoacceso) {
        this.ultimoacceso = ultimoacceso;
    }

    public Date getAcceso() {
        return acceso;
    }

    public void setAcceso(Date acceso) {
        this.acceso = acceso;
    }

    public Double getTarifamensual() {
        return tarifamensual;
    }

    public void setTarifamensual(Double tarifamensual) {
        this.tarifamensual = tarifamensual;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
 

    public Productos getProductos() {
        return productos;
    }

    public void setProductos(Productos productos) {
        this.productos = productos;
    }

//    public Collection<Factura> getFacturaCollection() {
//        return facturaCollection;
//    }
//
//    public void setFacturaCollection(Collection<Factura> facturaCollection) {
//        this.facturaCollection = facturaCollection;
//    }
//
//    public Collection<Tarjetas> getTarjetasCollection() {
//        return tarjetasCollection;
//    }
//
//    public void setTarjetasCollection(Collection<Tarjetas> tarjetasCollection) {
//        this.tarjetasCollection = tarjetasCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombres;
    }

}
