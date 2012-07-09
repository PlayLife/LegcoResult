package com.playlife.legcoresult.utility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Date;

public class GenericComparator implements Comparator<Object>{
	private static final int LESSER = -1;
	private static final int EQUAL = 0;
	private static final int GREATER = 1;
	
	private static final String TYPE_STRING = "java.lang.String";
	private static final String TYPE_DATE = "java.util.Date";
	private static final String TYPE_INTEGER = "java.lang.Integer";
	private static final String TYPE_LONG = "java.lang.Long";
	private static final String TYPE_FLOAT = "java.lang.Float";
	private static final String TYPE_DOUBLE = "java.lang.Double";
	private enum CompareMode { EQUAL, LESS_THAN, GREATER_THAN, DEFAULT }
	
	private Method method;
	private boolean isAscending;
	
	public GenericComparator(Method method, boolean isAscending) {
		super();
		this.method = method;
		this.isAscending = isAscending;
	}
	
	@Override
	public int compare(Object o1, Object o2) {
		try {
			int response = LESSER;
		
			Object v1 = (this.method == null) ? o1 : getValue(o1);
			Object v2 = (this.method == null) ? o2 : getValue(o2);		
			CompareMode cm = findCompareMode(v1, v2);
			
			if (!cm.equals(CompareMode.DEFAULT)) {
				return compareAlternate(cm);
			}
	
			final String returnType = (this.method == null) ? o1.getClass().getName() : this.method.getReturnType().getName();
			response = compareActual(v1, v2, returnType);
			
			return response;
		} catch (Exception ex){
			throw new LogicException(-9999, ex);
		}
	}

	private int compareAlternate(CompareMode cm) {
		int compareState = LESSER;
		switch(cm) {
			case LESS_THAN:
				compareState = LESSER * determinePosition();
				break;
			case GREATER_THAN:
				compareState = GREATER * determinePosition();
				break;
			case EQUAL:
				compareState = EQUAL * determinePosition();
				break;
		}
		return compareState;
	}	
	
	private int compareActual(Object v1, Object v2, String returnType) {
		int acutal = LESSER;
		if (returnType.equals(TYPE_INTEGER)) {
			acutal = (((Integer) v1).compareTo((Integer) v2) * determinePosition());
		} else if (returnType.equals(TYPE_LONG)) {
			acutal = (((Long) v1).compareTo((Long) v2) * determinePosition());
		} else if (returnType.equals(TYPE_STRING)) {
			acutal = (((String) v1).compareTo((String) v2) * determinePosition());
		} else if (returnType.equals(TYPE_DATE)) {
			acutal = (((Date) v1).compareTo((Date) v2) * determinePosition());
		} else if (returnType.equals(TYPE_FLOAT)) {
			acutal = (((Float) v1).compareTo((Float) v2) * determinePosition());
		} else if (returnType.equals(TYPE_DOUBLE)) {
			acutal = (((Double) v1).compareTo((Double) v2) * determinePosition());
		}
		return acutal;
	}
	
	private CompareMode findCompareMode(Object o1, Object o2) {
		CompareMode cm = CompareMode.LESS_THAN;
		
		if(null != o1 & null != o2) {
			cm = CompareMode.DEFAULT;
		} else if (null == o1 & null != o2) {
			cm = CompareMode.LESS_THAN;
		} else if (null != o1 & null == o2) {
			cm = CompareMode.GREATER_THAN;
		} else if (null == o1 & null == o2) {
			cm = CompareMode.EQUAL;			
		}
		
		return cm;		
	}	
	
	private int determinePosition() {
		return isAscending ? GREATER : LESSER;
	}
	
	private Object getValue(Object obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return method.invoke(obj);
	}
}
