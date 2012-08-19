/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.Administrador;

import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jcinform.persistencia.Empleados;

/**
 *
 * @author geovanny
 */
public class Administrador {
    Map prop = new HashMap();
          claves val = new claves();

     
   public Administrador(UsuarioActivo user) {
        try {
/*
        prop.put("toplink.cache.type.default","NONE"); 
        prop.put("toplink.jdbc.user", user.getNombre());
        prop.put("toplink.jdbc.password", val.desencriptar(user.getContrasenia()));
        prop.put("toplink.jdbc.url", "jdbc:mysql://"+user.getIp()+":"+user.getPuerto()+"/turnos?zeroDateTimeBehavior=convertToNull");
        */
        //ECLIPSELINK 
        prop.put("eclipselink.cache.type.default","NONE");
        prop.put("javax.persistence.jdbc.url", "jdbc:mysql://"+user.getIp()+":"+user.getPuerto()+"/turnos?zeroDateTimeBehavior=convertToNull");
        prop.put("javax.persistence.jdbc.password", val.desencriptar(user.getContrasenia()));
        prop.put("javax.persistence.jdbc.user", user.getNombre());
        } catch (Exception e) {
            System.out.println("ERROR EN ADMINISTRADOR LINEA 31;"+e);
        }
        //prop.put("toplink.jdbc.driver", "com.mysql.jdbc.Driver");
    }  
    public Administrador() {
        try {
/*
        prop.put("toplink.cache.type.default","NONE"); 
        prop.put("toplink.jdbc.user", user.getNombre());
        prop.put("toplink.jdbc.password", val.desencriptar(user.getContrasenia()));
        prop.put("toplink.jdbc.url", "jdbc:mysql://"+user.getIp()+":"+user.getPuerto()+"/turnos?zeroDateTimeBehavior=convertToNull");
        */
        //ECLIPSELINK 
        prop.put("eclipselink.cache.type.default","NONE");
        prop.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/turnos?zeroDateTimeBehavior=convertToNull");
        prop.put("javax.persistence.jdbc.password", "jcinform@2020");
        prop.put("javax.persistence.jdbc.user", "root");
        } catch (Exception e) {
            System.out.println("ERROR EN ADMINISTRADOR LINEA 31;"+e);
        }
        //prop.put("toplink.jdbc.driver", "com.mysql.jdbc.Driver");
    }
    EntityManagerFactory emf = null; 
    //javax.persistence.Persistence.createEntityManagerFactory("peajePU");

    public EntityManagerFactory getEMF() {
        if (emf == null) {
           emf = Persistence.createEntityManagerFactory("tpLinktrnPU", prop);
        }
        return emf;
    }

    public Integer getNuevaClave(String clase, String campo) {
        EntityManager em = getEMF().createEntityManager();
        try {
            Integer pk = (Integer) em.createQuery("SELECT MAX(o." + campo + ") FROM " + clase + " as o").getSingleResult();
            if (pk == null) {
                return new Integer(1);
            }

            int a = pk.intValue() + 1;
            return new Integer(a);
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return 1;
    }
//
    public Empleados ingresoSistema(String usuario, String clave) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        try {
            // TODO:
      
            String clavellega = val.encriptar(clave);
            List lista = em.createQuery("Select o from Empleados o where o.usuario = '" + usuario.trim() + "'  "
                    + "and o.clave = '" + clavellega + "' ").getResultList();
            if (lista.size() > 0) {
                Empleados user = (Empleados) lista.get(0);
                return user;
            } else {
                return null;
            }

            // em.persist(object);    em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }

    public void guardar(Object object) throws Exception {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();

        em.persist(object);
        em.getTransaction().commit();

    }

    public void actualizar(Object object) throws Exception {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();

        em.merge(object);
        em.getTransaction().commit();

    }

    public List query(String query) throws Exception {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();

        return em.createQuery(query).getResultList();

    }
    public List query(Date fecha, String tarjeta) throws Exception {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();

        return em.createQuery("Select o from Factura as o "
                + "where o.tarjetas.tarjeta = '"+tarjeta+"' and  o.fecha =:feca ").setParameter("feca", fecha).getResultList();

    }

    public List query(String query, int ini, int fin) {
        try {
            EntityManager em = getEMF().createEntityManager();
            em.getTransaction().begin();
            Query query3 = em.createQuery(query);
            query3.setFirstResult(ini);
            query3.setMaxResults(fin);
            return query3.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List listar(String query) throws Exception {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();

        return em.createQuery(query).getResultList();



    }

    public Object querySimple(String query) throws Exception {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();

        return em.createQuery(query).getSingleResult();


    }

    public List queryNativo(String query, Class clase) throws Exception {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();

        return em.createNativeQuery(query, clase).getResultList();


    }

    public List queryNativo(String query) throws Exception {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();

        return em.createNativeQuery(query).getResultList();


    }

    public Object buscarClave(Object pk, Class clase) throws Exception {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();

        return em.find(clase, pk);


    }

    public void eliminarObjeto(Class clase, Object pk) throws Exception {

        EntityManager em = getEMF().createEntityManager();

        em.getTransaction().begin();
        @SuppressWarnings("unchecked")
        Object obj = em.find(clase, pk);
        em.remove(obj);
        em.getTransaction().commit();

    }

    public void ejecutaSql(String query) throws Exception {
        EntityManager em = getEMF().createEntityManager();

        em.getTransaction().begin();
        em.createQuery(query).executeUpdate();
        em.getTransaction().commit();

    }
public java.util.Date Date() {
        EntityManager em = getEMF().createEntityManager();
        try {
            java.util.Date fec = (java.util.Date) ((Vector) em.createNativeQuery("Select now()").getSingleResult()).get(0);
            //System.out.println("FECHA NOW: "+fec);
            return fec;
        } catch (Exception e) {
            //System.out.println("ERROR AL OBTENER FECHA: "+e);
        } finally {
        }
        return new java.util.Date();
    }
    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
