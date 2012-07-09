package com.playlife.legcoresult.persistence.daos.generic;

import java.io.Serializable;

public interface IGenericDAO<T, PK extends Serializable>{
	public T save(T newInstance);
    public T get(PK id);
    public void update(T transientObject);
    public void delete(T persistentObject);
	public Number count();
}
