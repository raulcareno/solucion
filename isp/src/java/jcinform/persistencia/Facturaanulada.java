/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.persistencia;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author inform
 */
@Entity
@Table(name = "facturaanulada")
@XmlRootElement
@NamedQueries({})
public class Facturaanulada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "numero")
    private String numero;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "factura", referencedColumnName = "codigo")
    @ManyToOne
    private Factura factura;
     @JoinColumn(name = "empleados", referencedColumnName = "codigo")
    @ManyToOne
    private Empleados empleados;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "valorpago")
    private BigDecimal valorpago;
    @Column(name = "nopago")
    private Integer nopago;
    
    public Facturaanulada() {
    }

    public Facturaanulada(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Empleados getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getNopago() {
        return nopago;
    }

    public void setNopago(Integer nopago) {
        this.nopago = nopago;
    }

    public BigDecimal getValorpago() {
        return valorpago;
    }

    public void setValorpago(BigDecimal valorpago) {
        this.valorpago = valorpago;
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
        if (!(object instanceof Facturaanulada)) {
            return false;
        }
        Facturaanulada other = (Facturaanulada) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Facturaanulada[ codigo=" + codigo + " ]";
    }
    
}
