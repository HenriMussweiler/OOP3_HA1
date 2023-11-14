package dao;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class GenericDAO<T> {

    private final String UNIT_NAME = "OOP3-HA1";

    EntityManagerFactory emf =  Persistence.createEntityManagerFactory(UNIT_NAME);
    EntityManager em = emf.createEntityManager();
    EntityTransaction tr = em.getTransaction();

    private Class<T> entityClass;

    public GenericDAO(){}

    public GenericDAO(Class<T> entityClass){
        this.entityClass = entityClass;

    }

    public void save(T entity){
        tr.begin();
        this.em.persist(entity);
        tr.commit();
    }

    public T update(T entity){
        T ret;
        tr.begin();
        ret= em.merge(entity);
        tr.commit();
        return ret;
    }

    protected boolean delete(Object id, Class<T> classe){
        T entityToBeRemoved = em.getReference(classe, id);
        try {
            tr.begin();
            em.remove(entityToBeRemoved);
            tr.commit();
            return true;
        } catch (Exception e){
            System.out.println("Fehler beim Loeschen der Id: "+id.toString());
            return false;
        }
    }

    public T find(long entityId) {
        return em.find(entityClass, entityId);
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public List<T> findAll(){
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    @SuppressWarnings("unchecked")
    protected T findOneResult (String namedQuery, Map<String, Object> parameters){
        T result = null;
        try {
            Query query = em.createNamedQuery(namedQuery);
            if (parameters != null && !parameters.isEmpty()){
                populateQueryParameters(query, parameters);
            }

            result = (T) query.getSingleResult();

        } catch (Exception e){
            System.out.println("Fehler bei der Query: "+e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    protected List<T> findListResult(String namedQuery, Map<String, Object> parameters){
        List<T> result = null;
        try{
            Query query = em.createNamedQuery(namedQuery);
            if (parameters != null && !parameters.isEmpty()){
                populateQueryParameters(query,parameters);
            }
            result = (List<T>) query.getResultList();
        } catch (Exception e){
            System.out.println("Fehler bei der Query: "+e.getMessage());
        }
        return result;
    }


    private void populateQueryParameters(Query query, Map<String, Object> parameters){
        for (Entry<String, Object> entry : parameters.entrySet()){
            query.setParameter(entry.getKey(),  entry.getValue());
        }
    }


}