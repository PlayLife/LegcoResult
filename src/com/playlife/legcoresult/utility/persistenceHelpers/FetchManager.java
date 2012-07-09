package com.playlife.legcoresult.utility.persistenceHelpers;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.springframework.stereotype.Component;


@Component
public class FetchManager {
	private static String GETTER_PREFIX = "get";
	private static String SETTER_PREFIX = "set";
	private HashMap<Object, Object> map;
	
	public static String toGetter(String name){
		return GETTER_PREFIX + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
	}
	public void init(){
		map = new HashMap<Object, Object>();
	}
	
	public void close(){
		map.clear();
		map = null;
	}
	
	public void fetch(Object object, String name) throws Exception {
		Field field_object = object.getClass().getDeclaredField(name);
		FetchObject fetchObject = field_object.getAnnotation(FetchObject.class);
		Field field_key = object.getClass().getDeclaredField(fetchObject.key());
		
		String name_key = field_key.getName();
		String name_key_getter = GETTER_PREFIX + name_key.substring(0, 1).toUpperCase() + name_key.substring(1, name_key.length());
		
		String name_object = field_object.getName();
		String name_object_setter = SETTER_PREFIX + name_object.substring(0, 1).toUpperCase() + name_object.substring(1, name_object.length());
		
		Method method_key = object.getClass().getDeclaredMethod(name_key_getter);
		Method method_object = object.getClass().getDeclaredMethod(name_object_setter, field_object.getType());
		
		Object key = method_key.invoke(object);
		String map_key = field_object.getType() + key.toString();
		if (map.get(map_key) == null){
			Object obj_object = PMF.pm.get().getObjectById(field_object.getType(), (Serializable) key);
			method_object.invoke(object, obj_object);
			map.put(map_key, obj_object);
		} else 
			method_object.invoke(object, map.get(map_key));
		
	}
}
