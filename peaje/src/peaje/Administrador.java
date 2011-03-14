/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peaje;


import hibernate.HibernateUtil;
import hibernate.Usuarios;
import hibernate.cargar.claves;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author geovanny
 */
public class Administrador {

    static Session s = null;

    public Administrador() {
        HibernateUtil.conectar();
       s = HibernateUtil.getSessionFactory().openSession();
  
    }

//     public Integer getNuevaClave(String clase, String campo){
//        EntityManager s = getEMF().createEntityManager();
//        try{
//            Integer pk = (Integer)s.createQuery("SELECT MAX(o."+campo+") FROM "+clase+" as o").getSingleResult();
//            if(pk == null)
//                return new Integer(1);
//
//            int a = pk.intValue()+1;
//            return new Integer(a);
//        }catch(Exception e) {
//            s.getTransaction().rollback();
//        } finally {
//            s.close();
//        }
//        return 1;
//    }
//
//
    public void guardar(Object object) {
//         s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        try {
            s.save(object);
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        } finally {
//            s.close();
        }
    }

//
//
//
    public void actualizar(Object object) {
//         s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        try {
            s.merge(object);
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        } finally {
//            s.close();
        }
    }

    public List listar(String query) {
        try {
//             s = HibernateUtil.getSessionFactory().openSession();
            //HibernateUtil.conectar();
            //s = HibernateUtil.getSessionFactory().getCurrentSession();
           s.beginTransaction();
            return s.createQuery(query).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List query(String query) {
//         s = HibernateUtil.getSessionFactory().openSession();
       s.beginTransaction();

        try {

            return s.createQuery(query).list();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        } finally {
//            s.close();
        }
        return null;
    }

    public Object querySimple(String query) {
//         s = HibernateUtil.getSessionFactory().openSession();

        s.beginTransaction();
        try {
            return s.createQuery(query).uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        } finally {
//            s.close();
        }
        return null;
    }
//      public List queryNativo(String query, Class clase){
//        s.beginTransaction();
//        try {
//           return s.createSQLQuery((query, clase).getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            s.getTransaction().rollback();
//        } finally {
//            s.close();
//        }
//        return null;
//
//      }
//public List queryNativo(String query){
//      EntityManager s = getEMF().createEntityManager();
////      if(!s.getTransaction().isActive())
////        s.beginTransaction();
//
//        try {
//           return s.createNativeQuery(query).getResultList();
//        } catch (Exception e) {
////            e.printStackTrace();
////            s.getTransaction().rollback();
//        } finally {
////            s.close();
//        }
//        return null;
//
// }

     public Object buscarClave(String pk, Class clase) {
//         s = HibernateUtil.getSessionFactory().openSession();

        try {
            s.beginTransaction();
            Object cm = s.load(clase, pk);
           
            return cm;
        } catch (Exception e) {
            e.printStackTrace();
//            s.getTransaction().rollback();
        } finally {
//            s.close();
        }
        return null;

    }
    public Object buscarClave(Integer pk, Class clase) {
//         s = HibernateUtil.getSessionFactory().openSession();

        try {
            s.beginTransaction();
            Object cm = s.load(clase, pk);
//            System.out.println(""+cm.getDepartamento());
//            System.out.println(""+cm.getDependencia());
            return cm;
        } catch (Exception e) {
            e.printStackTrace();
//            s.getTransaction().rollback();
        } finally {
//            s.close();
        }
        return null;

    }

    public void eliminarObjeto(Class clase, Integer pk) {
        try {
            s.beginTransaction();
            Object obj = s.load(clase, pk);
            s.delete(obj);
            s.getTransaction().commit();
        } catch(Exception e) {
            System.out.println("error al eliminar"+ e);
           s.getTransaction().rollback();
         } finally {
            s.close();
        }
    }
      
       public void eliminarObjeto(Class clase, String pk) {
        try {
            s.beginTransaction();
            Object obj = s.load(clase, pk);
            s.delete(obj);
            s.getTransaction().commit();
        } catch(Exception e) {
            System.out.println("error al eliminar"+ e);
           s.getTransaction().rollback();
         } finally {
            s.close();
        }
    }
//
       
       
       public Usuarios  ingresoSistema(String usuario, String clave) {
            s = HibernateUtil.getSessionFactory().openSession();
         s.beginTransaction();
       
        try {
            // TODO:
      claves cl = new claves();
            String clavellega = cl.encriptar(clave);
            List lista  = s.createQuery("Select o from Usuarios o where o.usuario = '"+usuario.trim()+"'  " +
                    "and o.clave = '"+clavellega+"' ").list();
         if(lista.size()>0){
                    Usuarios user = (Usuarios)lista.get(0);
                   return user;
        }else{
              return null;
            }

            // em.persist(object);    em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        } finally {
//            s.close();
        }
        return null;
    }
 public void ejecutaSql(String query) {
        try {
            s.beginTransaction();
            s.createSQLQuery(query).executeUpdate();
            s.getTransaction().commit();
        } catch(Exception e) {
            //throw new PersistenceException(e);
        } finally {
//            s.close();
        }
    }
// public void ejecutaSqlNativo(String query) {
//        EntityManager s = getEMF().createEntityManager();
//        try {
//            s.beginTransaction();
//            s.createNativeQuery(query).executeUpdate();
//            s.getTransaction().commit();
//        } catch(Exception e) {
//            //throw new PersistenceException(e);
//        } finally {
//            s.close();
//        }
//    }
}
