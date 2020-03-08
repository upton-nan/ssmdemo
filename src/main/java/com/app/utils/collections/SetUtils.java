package com.app.utils.collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @ClassName: SetUtils
 * @Description: set集合的工具类
 * @author lyn
 * @date 2019年8月5日
 *
 */
public class SetUtils {
	
	/**
	 * 
	 * @Title: intersection
	 * @Description: 求俩个集合的交集
	 * @param set1  集合
	 * @param set2  集合
	 * @return 交集
	 */
    public static <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        if (set1!=null&&set2!=null) {
            List<T> list = new ArrayList<>(set1);
            list.retainAll(set2);
            return new HashSet<>(list);
        }
        return new HashSet<>();
    }
    
    /**
     * 
     * @Title: unicon
     * @Description: 求俩个集合的交集
     * @param set1
     * @param set2
     * @return 交集
     */
    public static <T> Set<T> unicon(Set<T> set1, Set<T> set2) {
        set1.addAll(set2);
        return set1;
    }
    
    
    /**
     * 
     * @Title: subtract
     * @Description: 求俩个集合的差集
     * @param set1
     * @param set2
     * @return
     */
    public static <T> Set<T> subtract(Set<T> set1, Set<T> set2) {
        Set<T> set = new HashSet<>(set1.size() + set2.size());
        if (set1!=null&&set1.size()>0) {
            set.addAll(set1);
            set.removeAll(set2);
        }
        return set;
    }
    
}
