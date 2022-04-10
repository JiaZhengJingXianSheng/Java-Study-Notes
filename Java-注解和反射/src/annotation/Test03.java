package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/21
 */

// 自定义注解
public class Test03 {

    // 注解可以显式赋值，如果没有默认值，就必须给注解赋值
    @MyAnnotation2(name = "LYZ", schools = {"西安电子科技大学"})
    public void Test(){ }

    @MyAnnotation3(value="LYZ")
    public void Test2(){}
}


@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation2 {

    // 注解的参数： 参数类型 + 参数名();
    String name() default "";
    int age() default 0;
    int id() default -1;    // 如果默认值为-1，代表不存在

    String[] schools();
}

@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation3 {

    String value();
}