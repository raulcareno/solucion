/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate.cargar;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import hibernate.Usuarios;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Query;

/**
 *
 * @author geovanny
 */
public class Administrador {
    Map prop = new HashMap();
          claves val = new claves();
    public Administrador(UsuarioActivo user) {
        try {


        prop.put("toplink.jdbc.user", user.getNombre());
        prop.put("toplink.jdbc.password", val.desencriptar(user.getContrasenia()));
        prop.put("toplink.jdbc.url", "jdbc:mysql://"+user.getIp()+":"+user.getPuerto()+"/peaje?zeroDateTimeBehavior=convertToNull");
        } catch (Exception e) {
            System.out.println("ERROR EN ADMINISTRADOR LINEA 31;"+e);
        }
        //prop.put("toplink.jdbc.driver", "com.mysql.jdbc.Driver");
        
    }
    EntityManagerFactory emf = null;
    //javax.persistence.Persistence.createEntityManagerFactory("peajePU");

    public EntityManagerFactory getEMF() {
        if (emf == null) {
           emf = Persistence.createEntityManagerFactory("peajePU", prop);
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
    public Usuarios ingresoSistema(String usuario, String clave) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        try {
            // TODO:
      
            String clavellega = val.encriptar(clave);
            List lista = em.createQuery("Select o from Usuarios o where o.usuario = '" + usuario.trim() + "'  "
                    + "and o.clave = '" + clavellega + "' ").getResultList();
            if (lista.size() > 0) {
                Usuarios user = (Usuarios) lista.get(0);
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
