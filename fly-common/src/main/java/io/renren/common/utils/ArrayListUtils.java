package io.renren.common.utils;

import java.util.List;

public class ArrayListUtils {

    /**
     * 从数组中随机获取元素
     * @param arrays
     * @return
     */
    public static String getElementFromArray(String[] arrays){
        String random = "";
//        String[] doc = {"成功领取赠险", "安全到家", "已领取50万保障", "和家人团聚","在家中贴春联"};
        int index = (int) (Math.random() * arrays.length);
        random = arrays[index];
        return random;
    }

    /**
     * 从数组中随机获取元素
     * @param list
     * @return
     */
    public static Object getElementFromList(List list){
        Object random = null;
//        String[] doc = {"成功领取赠险", "安全到家", "已领取50万保障", "和家人团聚","在家中贴春联"};
        int index = (int) (Math.random() * list.size());
        random = list.get(index);
        return random;
    }


}
