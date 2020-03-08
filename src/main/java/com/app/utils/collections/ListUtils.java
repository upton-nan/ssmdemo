package com.app.utils.collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: ListUtils
 * @Description: List的工具类
 * @author lyn
 * @date 2019年8月4日
 *
 */
public class ListUtils {

	 private static Logger logger = LoggerFactory.getLogger(ListUtils.class);
    /**
     * 
     * @Title: getRandomFromArray
     * @Description: 从list中随机获取指定个数元素
     * @param list
     * @param count
     * @return
     */
    public static  <T> List<T> getRandomFromArray(List<T> list, int count) {

        List<T> result = new ArrayList<T>();
        Random random = new Random();

        // 要随机取的元素个数
        if (count > list.size() || count < 0)
            return list;

        int n = 0;
        while (true) {

            if (n == count) // 取到足量随机数后退出循环
                break;
            int size = list.size();
            int temp = random.nextInt(size);
            result.add(list.get(temp));
            list.remove(temp);
            n++;
        }
        return result;
    }
    
    /**
     * 
     * @Title: removeDuplicate
     * @Description: 去除重复元素
     * @param list 需要处理的list
     * @return 去重后的list
     */
    public static <T> List<T> removeDuplicate(List<T> list) {
        if (list == null || list.size() == 0) {
            logger.error("list is empty or is null");
            return new ArrayList<>();
        }
        return new ArrayList<>(new HashSet<>(list));

    }
    /**
     * 
     * @Title: intersection
     * @Description: 求俩个集合的交集
     * @param list1
     * @param list2
     * @return
     */
    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        if (list1!=null&&list2!=null) {
            Set<T> set = new HashSet<>(list1);
            set.retainAll(list2);
            return new ArrayList<>(set);
        }
        return new ArrayList<>();
    }
    
    /**
     * 
     * @Title: unicon
     * @Description: 求俩个集合的并集
     * @param list1
     * @param list2
     * @return
     */
    public static <T> List<T> unicon(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<>();
        list.addAll(list1);
        list.addAll(list2);
        return list;
    }
    
    /**
     * 
     * @Title: subtract
     * @Description: 求俩个集合的差集
     * @param list1
     * @param list2
     * @return
     */
    public static <T> List<T> subtract(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<>(list1.size() + list2.size());
        if (list1!=null&&list1.size()>0) {
            list.addAll(list1);
            list.removeAll(list2);
        }
        return list;
    }
    
    
}
