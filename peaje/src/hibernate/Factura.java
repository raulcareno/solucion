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
@Table(name = "factura")
@NamedQueries({})
public class Factura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "numero")
    private String numero;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "anulado")
    private Boolean anulado;
    @Column(name = "tarifa0")
    private Boolean tarifa0;
    @Column(name = "ticket")
    private String ticket;
    @Column(name = "dias")
    private Integer dias;
    @Column(name = "placa")
    private String placa;
    @Column(name = "fecha")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @Column(name = "iva")
    private BigDecimal iva;
    @Column(name = "descuento")
    private BigDecimal descuento;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "fechaini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaini;
    @Column(name = "fechafin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafin;
    @Column(name = "tiempo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tiempo;
    @JoinColumn(name = "tarjeta", referencedColumnName = "tarjeta")
    @ManyToOne
    private Tarjetas tarjetas;
    @JoinColumn(name = "cliente", referencedColumnName = "codigo")
    @ManyToOne
    private Clientes clientes;
    @JoinColumn(name = "usuario", referencedColumnName = "codigo")
    @ManyToOne
    private Usuarios usuario;
    @JoinColumn(name = "usuarioc", referencedColumnName = "codigo")
    @ManyToOne
    private Usuarios usuarioc;
    @Column(name = "nocontar")
    private Boolean nocontar;
    @Column(name = "sellado")
    private Boolean sellado;
//    @OneToMany(mappedBy = "factura")
//    private Collection<Detalle> detalleCollection;

    public Factura() {
    }

    public Factura(Integer codigo) {
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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getFechaini() {
        return fechaini;
    }

    public void setFechaini(Date fechaini) {
        this.fechaini = fechaini;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Date getTiempo() {
        return tiempo;
    }

    public void setTiempo(Date tiempo) {
        this.tiempo = tiempo;
    }

    public Tarjetas getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(Tarjetas tarjetas) {
        this.tarjetas = tarjetas;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Boolean getTarifa0() {
        return tarifa0;
    }

    public void setTarifa0(Boolean tarifa0) {
        this.tarifa0 = tarifa0;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Usuarios getUsuarioc() {
        return usuarioc;
    }

    public void setUsuarioc(Usuarios usuarioc) {
        this.usuarioc = usuarioc;
    }

    public Boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(Boolean anulado) {
        this.anulado = anulado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Boolean getNocontar() {
        return nocontar;
    }

    public void setNocontar(Boolean nocontar) {
        this.nocontar = nocontar;
    }

    public Boolean getSellado() {
        return sellado;
    }

    public void setSellado(Boolean sellado) {
        this.sellado = sellado;
    }

    
//
//    public Collection<Detalle> getDetalleCollection() {
//        return detalleCollection;
//    }
//
//    public void setDetalleCollection(Collection<Detalle> detalleCollection) {
//        this.detalleCollection = detalleCollection;
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
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getPlaca();
    }

}
