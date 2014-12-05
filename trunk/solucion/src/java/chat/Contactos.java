/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import jcinform.persistencia.Descuentos;

/**
 *
 * @author GEOVANNY
 */
public class Contactos {
    String id;
    String nombre;
    Boolean isLogin = false;

    public Contactos(String id, String nombre, Boolean isLogin) {
        this.id = id;
        this.nombre = nombre;
        this.isLogin = isLogin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contactos)) {
            return false;
        }
        Contactos other = (Contactos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

     
    public String toString() {
        return nombre;
    }
    
    
}
