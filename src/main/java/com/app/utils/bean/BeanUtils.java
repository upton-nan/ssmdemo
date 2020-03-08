package com.app.utils.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @ClassName: BeanUtils
 * @Description: Bean操作工具类
 * @author lyn
 * @date 2019年8月7日
 *
 */
public class BeanUtils {

	/**
	 * 
	 * @Title: map2Object
	 * @Description: map转成object
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
		if (map != null) {
			try {
				T obj = clazz.newInstance();
				BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				for (PropertyDescriptor property : propertyDescriptors) {
					Method setter = property.getWriteMethod();
					if (setter != null) {
						setter.invoke(obj, map.get(property.getName()));
					}
				}
				return obj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: object2Map
	 * @Description: object转成map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> object2Map(Object obj) {
		if (obj != null) {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				for (PropertyDescriptor property : propertyDescriptors) {
					String key = property.getName();
					if (!key.equalsIgnoreCase("class")) {
						Method getter = property.getReadMethod();
						Object value = getter == null ? null : getter.invoke(obj);
						map.put(key, value);
					}
				}
				return map;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
