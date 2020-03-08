package com.app.utils.collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @ClassName: MapUtils
 * @Description: Map的工具类
 * @author lyn
 * @date 2019年8月5日
 *
 */
public class MapUtils {
	
	/**
	 * 
	 * @Title: intersection
	 * @Description: Map集合的交集只提供键的交集
	 * @param map1
	 * @param map2
	 * @return 交集
	 */
    public static <K, V> Map<K, V> intersection(Map<K, V> map1, Map<K, V> map2) {
        Map<K, V> map = new HashMap<>(map1.size());
        if (map1!=null&&map1.size()>0&&map2!=null&&map2.size()>0) {
            Set<K> setkey1 = new HashSet<>(map1.keySet());
            Set<K> setkey2 = new HashSet<>(map2.keySet());
            setkey1.retainAll(setkey2);
            for (K k : setkey1) {
                map.put(k, map1.get(k));
            }
        }
        return map;
    }
    
    /**
     * 
     * @Title: unicon
     * @Description: 求俩个map的交集
     * @param map1
     * @param map2
     * @return
     */
    public static <K, V> Map<K, V> unicon(Map<K, V> map1, Map<K, V> map2) {
        Map<K, V> map = new HashMap<>(map1.size() + map2.size());
        map.putAll(map1);
        map.putAll(map2);
        return map;
    }
    
    /**
     * 
     * @Title: subtract
     * @Description: 求俩个集合的差集
     * @param map1
     * @param map2
     * @return
     */
    public static <K, V> Map<K, V> subtract(Map<K, V> map1, Map<K, V> map2) {
        Map<K, V> map = new HashMap<>(map1.size() + map2.size());
        if (map1!=null&&map1.size()>0&&map2!=null&&map2.size()>0) {
            Set<K> setkey1 = new HashSet<>(map1.keySet());
            Set<K> setkey2 = new HashSet<>(map2.keySet());
            for (K k : setkey2) {
                setkey1.remove(k);
            }
            for (K k : setkey1) {
                map.put(k, map1.get(k));
            }
        }
        return map;

    }
    
    /**
     * 
     * @Title: join
     * @Description: 将Map以separator链接并以字符串的形式返回
     * @param map
     * @param separatorK_V 键与值的分割符
     * @param separatorKV_KV 键值与键值的分割符
     * @return
     */
    public static <K, V> String join(Map<K, V> map, String separatorK_V, String separatorKV_KV) {
        if (map == null || map.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            sb.append(String.valueOf(entry.getKey())).append(separatorKV_KV)
                    .append(String.valueOf(entry.getValue())).append(separatorK_V);
        }
        return sb.toString().substring(0, sb.toString().length() - separatorK_V.length());
    }
    
    
    /**
     * 
     * @Title: keyJoin
     * @Description: 将map的key以separator链接并以字符串的形式返回
     * @param map
     * @param separator
     * @return
     */
    public static <K, V> String keyJoin(Map<K, V> map, String separator) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            sb.append(String.valueOf(entry.getKey())).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - separator.length());
    }


    /**
     * 
     * @Title: valueJoin
     * @Description: 将map的value以separator链接并以字符串的形式返回
     * @param map
     * @param separator
     * @return
     */
    public static <K, V> String valueJoin(Map<K, V> map, String separator) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<K, V> entry : map.entrySet()) {
            sb.append(String.valueOf(entry.getValue())).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - separator.length());
    }
    
    
    
    public static void main(String[] args) {
    	Map<String, String> map=new HashMap<>();
    	map.put(1+"", 2+"");
    	map.put("3", "4");
    	map.put("5", "6");
		System.out.println(MapUtils.join(map, "=", "*"));
		System.out.println(MapUtils.keyJoin(map, "="));
	}

    
}
