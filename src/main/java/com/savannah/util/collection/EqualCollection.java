package com.savannah.util.collection;

import java.util.List;
import java.util.Map;

/**
 * 比较map，list是否相同
 * 这个在Object.equals中已经实现
 * 在这之前需要重写对象的equals方法
 * @author stalern
 * @date 2019/12/29~09:22
 */
public class EqualCollection {

    private static boolean equal;

    public static <K,V> boolean equalMap(Map<K,V> map1, Map<K,V> map2) {
        equal = (map1 == null && map2 == null) ||
                (map1 != null && map1.equals(map2));
        return equal;
    }

    public static <E> boolean equalList (List<E> list1, List<E> list2) {
        equal = (list1 == null && list2 == null) ||
                (list1 != null && list1.equals(list2));
        return equal;
    }
}
