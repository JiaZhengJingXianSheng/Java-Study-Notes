package annotation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/19
 */
// 什么是注解
public class Test01 extends Object{

    // @Override 重写的注解
    @Override
    public String toString() {
        return super.toString();
    }

    // @Deprecated 不推荐使用
    @Deprecated
    public static void test(){
        System.out.println("Deprecated");
    }

    @SuppressWarnings("all")
    public void test02(){
        List<Integer> Lists = new ArrayList<Integer>();
    }

    public static void main(String[] args) {
        test();
    }

}
