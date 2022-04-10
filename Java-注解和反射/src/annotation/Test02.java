package annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/21
 */

// 测试元注解
@MyAnnotation
public class Test02 {
    @MyAnnotation
    public void test(){

    }
}

// 定义一个注解
// Target 表示我们的注解可以用在什么地方
@Target(value = {ElementType.METHOD,ElementType.TYPE})
// Retention 表示我我们的注解在什么地方有效
// RUNTIME > CLASS > SOURCE
@Retention(value= RetentionPolicy.RUNTIME)
// Document 表示是否将我们的注解生成在 JavaDoc 中
@Documented
// Inherited 表示子类可以继承父类的注解
@Inherited
@interface MyAnnotation{

}