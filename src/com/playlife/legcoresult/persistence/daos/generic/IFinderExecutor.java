package com.playlife.legcoresult.persistence.daos.generic;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public interface IFinderExecutor<T>{
    T executeFindOne(Method method, String jdoql, Map<String, Object> map_param);
    List<Object> executeFindAll(Method method, String jdoql, Map<String, Object> map_param);
    Number executeCount(Method method, String jdoql, Map<String, Object> map_param);
}