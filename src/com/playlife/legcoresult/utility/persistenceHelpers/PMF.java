package com.playlife.legcoresult.utility.persistenceHelpers;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class PMF {
	public static ThreadLocal<PersistenceManager> pm = new ThreadLocal<PersistenceManager>();
    private static PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
    
    private PMF() {}

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
    
    public static void set(PersistenceManagerFactory _pmfInstance){
    	pmfInstance = _pmfInstance;
    }
}
