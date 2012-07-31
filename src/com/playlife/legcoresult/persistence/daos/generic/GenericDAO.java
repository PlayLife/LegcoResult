package com.playlife.legcoresult.persistence.daos.generic;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.playlife.legcoresult.utility.PersistenceException;
import com.playlife.legcoresult.utility.persistenceHelpers.PMF;

public class GenericDAO <T, PK extends Serializable> implements IGenericDAO<T, PK>, IFinderExecutor<Object> {
    private Class<T> type;
    
    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    public PersistenceManager pm(){
    	return PMF.pm.get();
    }
    
	@Override
	public T save(T newInstance) {
		try {
			Transaction tx = pm().currentTransaction();
			tx.begin();
			T object = pm().makePersistent(newInstance);
			tx.commit();
			
			return object;
		} catch (Exception ex){
			throw new PersistenceException(-9999, ex);
		} finally {
	    }
	}

	@Override
	public T get(PK id) {
		try {
			T obj_return = pm().getObjectById(type, id);
			return obj_return;
		} catch (Exception ex){
			throw new PersistenceException(-9999, ex);
		} finally {
	    }
	}
	
	@Override
	public void update(T transientObject) {
		
	}

	@Override
	public void delete(T persistentObject) {
		try {
			pm().deletePersistent(persistentObject);
		} catch (Exception ex){
			throw new PersistenceException(-9999, ex);
		} finally {
	    }
	}

	@Override
	public Number count() {
		try {
			Query query = pm().newQuery(type);
			query.setResult("count()");
			return (Number)(query.execute());
		} catch (Exception ex){
			throw new PersistenceException(-9999, ex);
		} finally {
	    }
	}

	@SuppressWarnings("unchecked")
	@Override
	public T executeFindOne(Method method, String jdoql, Map<String, Object> map_param) {
		try {
			Query query = pm().newQuery(type);;
			if (jdoql != null && !jdoql.isEmpty())
				query.setFilter(jdoql);
			
			if (map_param.get("start") != null && map_param.get("end") != null && ((Long)map_param.get("start")) != -1 && ((Long)map_param.get("end")) != -1){
				query.setRange((Long)map_param.get("start"), (Long)map_param.get("end"));
			}
			map_param.remove("start");
			map_param.remove("end");
			
			if (map_param.get("order") != null && !((String)map_param.get("order")).isEmpty())
				query.setOrdering((String)map_param.get("order"));
			
			map_param.remove("order");
						
			String declaration = "";
			for (String key : map_param.keySet()){
				if (!declaration.isEmpty())
					declaration += ",";
				Object object = map_param.get(key);
				declaration += object.getClass().getName() + " " + key;
			}
			query.declareParameters(declaration);
			
			List<Object> list = (List<Object>)query.executeWithMap(map_param);
			if (list.size() > 1)
				throw new PersistenceException(-9999);
			else if (list.size() == 0)
				return null;
			else 
				return (T) list.get(0);
		} catch (Exception ex){
			throw new PersistenceException(-9999, ex);
		}
	}
	
	
	@Override
	public Number executeCount(Method method, String jdoql, Map<String, Object> map_param) {
		try {
			Query query = pm().newQuery(type, jdoql);
			String declaration = "";
			for (String key : map_param.keySet()){
				if (!declaration.isEmpty())
					declaration += ",";
				Object object = map_param.get(key);
				declaration += object.getClass().getName() + " " + key;
			}
			query.declareParameters(declaration);
			
			query.setResult("count()");
			return (Number)query.executeWithMap(map_param);
		} catch (Exception ex){
			throw new PersistenceException(-9999, ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> executeFindAll(Method method, String jdoql, Map<String, Object> map_param) {
		try {
			Query query = pm().newQuery(type);;
			if (jdoql != null && !jdoql.isEmpty())
				query.setFilter(jdoql);
			
			if (map_param.get("start") != null && map_param.get("end") != null)
				query.setRange((Long)map_param.get("start"), (Long)map_param.get("end"));
			map_param.remove("start");
			map_param.remove("end");
			
			if (map_param.get("order") != null && !((String)map_param.get("order")).isEmpty())
				query.setOrdering((String)map_param.get("order"));
			
			map_param.remove("order");
			
			String declaration = "";
			for (String key : map_param.keySet()){
				if (!declaration.isEmpty())
					declaration += ",";
				Object object = map_param.get(key);
				declaration += object.getClass().getName() + " " + key;
			}
			query.declareParameters(declaration);
			
			// make the list become read-only so they will be remind that add in this list is pointless.
			List<Object> resultList =(List<Object>)query.executeWithMap(map_param);
			return Collections.unmodifiableList(resultList);
		} catch (Exception ex){
			throw new PersistenceException(-9999, ex);
		}
	}
}
