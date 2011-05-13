/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jcinform.persistencia;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Familia Jadan Cahueñ
 */
@Entity
@Table(name = "contratos")
@NamedQueries({
    @NamedQuery(name = "Contratos.findAll", query = "SELECT c FROM Contratos c")})
public class Contratos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "estado")
    private String estado;
    @Column(name = "contrato")
    private String contrato;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "formapago")
    private Integer formapago;
    @Column(name = "nocuenta")
    private String nocuenta;
    @Column(name = "tipocuenta")
    private String tipocuenta;
    @Column(name = "diapago")
    private Integer diapago;
    @Column(name = "fechapago")
    @Temporal(TemporalType.DATE)
    private Date fechapago;
    @Column(name = "ip")
    private String ip;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "clave")
    private String clave;
    @Column(name = "autorizado")
    private Boolean autorizado;
    @Column(name = "fechainstalacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainstalacion;
    @Column(name = "fechafinal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @JoinColumn(name = "sucursal", referencedColumnName = "codigo")
    @ManyToOne
    private Sucursal sucursal;
    @JoinColumn(name = "registrador", referencedColumnName = "codigo")
    @ManyToOne
    private Empleados empleados;
    @JoinColumn(name = "plan", referencedColumnName = "codigo")
    @ManyToOne
    private Plan plan;
    @JoinColumn(name = "nodos", referencedColumnName = "codigo")
    @ManyToOne
    private Nodos nodos;
    @JoinColumn(name = "instalador", referencedColumnName = "codigo")
    @ManyToOne
    private Empleados empleados1;
    @JoinColumn(name = "equipos3", referencedColumnName = "codigo")
    @ManyToOne
    private Detallecompra detallecompra;
    @JoinColumn(name = "equipos2", referencedColumnName = "codigo")
    @ManyToOne
    private Detallecompra detallecompra1;
    @JoinColumn(name = "clientes", referencedColumnName = "codigo")
    @ManyToOne
    private Clientes clientes;
    @JoinColumn(name = "empleados", referencedColumnName = "codigo")
    @ManyToOne
    private Empleados empleados2;
    @JoinColumn(name = "equipos1", referencedColumnName = "codigo")
    @ManyToOne
    private Detallecompra detallecompra2;

    public Contratos() {
    }

    public Contratos(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getFormapago() {
        return formapago;
    }

    public void setFormapago(Integer formapago) {
        this.formapago = formapago;
    }

    public String getNocuenta() {
        return nocuenta;
    }

    public void setNocuenta(String nocuenta) {
        this.nocuenta = nocuenta;
    }

    public String getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(String tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public Integer getDiapago() {
        return diapago;
    }

    public void setDiapago(Integer diapago) {
        this.diapago = diapago;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Boolean autorizado) {
        this.autorizado = autorizado;
    }

    public Date getFechainstalacion() {
        return fechainstalacion;
    }

    public void setFechainstalacion(Date fechainstalacion) {
        this.fechainstalacion = fechainstalacion;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Empleados getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Nodos getNodos() {
        return nodos;
    }

    public void setNodos(Nodos nodos) {
        this.nodos = nodos;
    }

    public Empleados getEmpleados1() {
        return empleados1;
    }

    public void setEmpleados1(Empleados empleados1) {
        this.empleados1 = empleados1;
    }

    public Detallecompra getDetallecompra() {
        return detallecompra;
    }

    public void setDetallecompra(Detallecompra detallecompra) {
        this.detallecompra = detallecompra;
    }

    public Detallecompra getDetallecompra1() {
        return detallecompra1;
    }

    public void setDetallecompra1(Detallecompra detallecompra1) {
        this.detallecompra1 = detallecompra1;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Empleados getEmpleados2() {
        return empleados2;
    }

    public void setEmpleados2(Empleados empleados2) {
        this.empleados2 = empleados2;
    }

    public Detallecompra getDetallecompra2() {
        return detallecompra2;
    }

    public void setDetallecompra2(Detallecompra detallecompra2) {
        this.detallecompra2 = detallecompra2;
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
        if (!(object instanceof Contratos)) {
            return false;
        }
        Contratos other = (Contratos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Contratos[codigo=" + codigo + "]";
    }

}
