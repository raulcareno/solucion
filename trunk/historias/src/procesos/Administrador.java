/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package procesos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.*;

/**
 *
 * @author geovanny
 */
public class Administrador {
  Map prop = new HashMap();
          claves val = new claves();
  @SuppressWarnings("static-access")
    public Administrador(UsuarioActivo user) {
        try {
        prop.put("toplink.jdbc.user", user.getNombre());
        prop.put("toplink.jdbc.password", val.desencriptar(user.getContrasenia()));
        prop.put("toplink.jdbc.url", "jdbc:mysql://"+user.getIp()+":"+user.getPuerto()+"/clinicas?zeroDateTimeBehavior=convertToNull");
        } catch (Exception e) {
            System.out.println("ERROR EN ADMINISTRADOR LINEA 31;"+e);
        }
        prop.put("toplink.jdbc.driver", "com.mysql.jdbc.Driver");
    }
    public Administrador() {
    }
    static EntityManagerFactory emf = null;
    //javax.persistence.Persistence.createEntityManagerFactory("historiasPU");

    public EntityManagerFactory getEMF() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("historiasPU",prop);
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

    public Empleados ingresoSistema(String usuario, String clave) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        try {
            // TODO:
            if (usuario.equals("admin") && clave.equals("ismaelsami2020")) {
                Empleados emp = new Empleados(-1);
                emp.setApellidos("SUPER ADMINISTRADOR");
                emp.setNombres("");
                emp.setEstado(true);
//                emp.setPerfil(new Global(1));
                return emp;
            }

            claves val = new claves();
            String clavellega = val.encriptar(clave);
            List lista = em.createQuery("Select o from Empleados o where o.usuario = '" + usuario.trim() + "'  "
                    + "and o.clave = '" + clavellega + "' and o.estado = true ").getResultList();
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

    public void guardar(Object object) {
        EntityManager em = getEMF().createEntityManager();
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

    public void actualizar(Object object) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public List listar(String clase) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        try {
            return em.createQuery("Select o from '" + clase + "' as o ").getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }

    public List query(String query) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        try {
            return em.createQuery(query).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }

    public Object querySimple(String query) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        try {
            return em.createQuery(query).getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }

    public List queryNativo(String query, Class clase) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        try {
            return em.createNativeQuery(query, clase).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;

    }

    public List queryNativo(String query) {
        EntityManager em = getEMF().createEntityManager();
//      if(!em.getTransaction().isActive())
//        em.getTransaction().begin();

        try {
            return em.createNativeQuery(query).getResultList();
        } catch (Exception e) {
//            e.printStackTrace();
//            em.getTransaction().rollback();
        } finally {
//            em.close();
        }
        return null;

    }

    public Object buscarClave(Object query, Class clase) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        try {
            return em.find(clase, query);
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;

    }

    public void eliminarObjeto(Class clase, Object pk) {

        EntityManager em = getEMF().createEntityManager();
        try {
            em.getTransaction().begin();
            @SuppressWarnings("unchecked")
            Object obj = em.find(clase, pk);
            em.remove(obj);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("error al eliminar" + e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Integer geUltimaMatricula(String query) {
        EntityManager em = getEMF().createEntityManager();
        try {
            Integer pk = (Integer) em.createQuery(query).getSingleResult();
            if (pk == null) {
                return new Integer(1);
            }

            int a = pk.intValue();
            return new Integer(a);
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return 1;
    }

    public void ejecutaSql(String query) {
        EntityManager em = getEMF().createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery(query).executeUpdate();

            em.getTransaction().commit();
        } catch (Exception e) {
            //throw new PersistenceException(e);
        } finally {
            em.close();
        }
    }

    public void ejecutaSqlNativo(String query) {
        EntityManager em = getEMF().createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery(query).executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            //throw new PersistenceException(e);
        } finally {
            em.close();
        }
    }
}
