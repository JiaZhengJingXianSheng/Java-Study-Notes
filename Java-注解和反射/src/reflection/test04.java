package reflection;

import java.lang.annotation.ElementType;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/04/09
 */

// 所有类型的class
public class test04 {
    public static void main(String[] args) {
        Class c1 = Object.class;        // 类
        Class C2 = Comparable.class;    // 接口
        Class C3 = String[].class;      // 一维数组
        Class C4 = int[][].class;       // 二维数组
        Class C5 = Override.class;      // 注解
        Class C6 = ElementType.class;   // 枚举
        Class C7 = Integer.class;       // 基本数据类型
        Class C8 = void.class;          // void
        Class C9 = Class.class;         // class


        System.out.println(c1);
        System.out.println(C2);
        System.out.println(C3);
        System.out.println(C4);
        System.out.println(C5);
        System.out.println(C6);
        System.out.println(C7);
        System.out.println(C8);
        System.out.println(C9);

        // 只要元素类型与维度一致，就是同一个Class
        int[] a = new int[10];
        int[] b = new int[100];
        System.out.println(a.getClass().hashCode());
        System.out.println(b.getClass().hashCode());

    }
}
