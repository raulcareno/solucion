/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.conexion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import jcinform.persistencia.*;

/**
 *
 * @author geovanny
 */
public class Administrador {

    Map prop = new HashMap();

    public Administrador() {
    }
    static EntityManagerFactory emf = null;
    //javax.persistence.Persistence.createEntityManagerFactory("conexionAcademicoPU");

    public EntityManagerFactory getEMF() {
        try {
            prop.put("toplink.jdbc.user", "root");
            prop.put("toplink.jdbc.password", "jcinform@2020");
            prop.put("toplink.cache.type.default","NONE");
            prop.put("toplink.jdbc.url", "jdbc:mysql://localhost:3306/isp?zeroDateTimeBehavior=convertToNull");
        } catch (Exception e) {
            System.out.println("ERROR EN ADMINISTRADOR LINEA 31;" + e);
        }

        if (emf == null) {
            //   emf = Persistence.createEntityManagerFactory("ispPU");
            emf = Persistence.createEntityManagerFactory("ispPU", prop);
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
            if (usuario.equals("admin") && clave.equals("ismaelsami@2020")) {
                Empleados emp = new Empleados(-1);
                emp.setApellidos("SUPER ADMINISTRADOR");
                emp.setNombres("");
//                emp.setEstado(true);
//                emp.setPerfil(new Global(1));
//                emp.setTipo("Interna");
                return emp;
            }

            claves val = new claves();
            String clavellega = val.encriptar(clave);
            List lista = em.createQuery("Select o from Empleados o where o.usuario = '" + usuario.trim() + "'  "
                    + "and o.clave = '" + clavellega + "' and o.estado = true ").getResultList();
            if (lista.size() > 0) {
                Empleados user = (Empleados) lista.get(0);
//                    List lista2  = em.createQuery("Select o from Institucion o").getResultList();
//                    Institucion insts = (Institucion)lista2.get(0);
//                    globales glob =new globales();
//                    List para = em.createQuery("Select o from ParametrosGlobales o").getResultList();
//                    for (Iterator it = para.iterator(); it.hasNext();) {
//                            ParametrosGlobales object = (ParametrosGlobales) it.next();
//                            glob.parametrosGlobales.add(object);
//                     }


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
    public java.util.Date Date(){
        EntityManager em = getEMF().createEntityManager();
        try {
            java.util.Date fec = (java.util.Date) ((Vector)em.createNativeQuery("Select now()").getSingleResult()).get(0);
            //System.out.println("FECHA NOW: "+fec);
            return fec;
        } catch (Exception e) {
            //System.out.println("ERROR AL OBTENER FECHA: "+e);
        } finally {
 
        }
        return new java.util.Date();
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
            throw new PersistenceException(e);
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
