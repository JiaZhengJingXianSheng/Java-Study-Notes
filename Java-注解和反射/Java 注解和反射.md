# Java 注解和反射



<!-- toc -->

[TOC]

# 注解

## 什么是注解

* **Annotation 不是程序本身， 可以对程序做出解释。**
* **可以被其他程序（比如：编译）读取。**
* **注解是以 "@注释名" 在代码中存在，还可以添加一些参数值**
* **可以附加在package， class， method， field等上面，相当于给他们添加了额外的辅助信息，我们可以通过反射机制编程实现对这些元数据的访问。**



***

## 内置注解

* **@Override：检查该方法是否是重写方法。如果发现其父类，或者是引用的接口中并没有该方法时，会报编译错误。** 
* **@Deprecated： 标记过时方法。如果使用该方法，会报编译警告。**
* **@SuppressWarnings： 指示编译器去忽略注解中声明的警告。**



***

## 元注解

**负责注解其他注解的注解。**

* **@Target： 用于描述注解的使用范围，标记这个注解应该是哪种 Java 成员。**
* **@Retention： 表示需要在什么级别保存该注释信息，用于描述注解的生命周期。( SOURCE < CLASS < RUNTIME )**
* **@Document： 说明该注解将被包含在 javadoc 中。**
* **@Inherited： 说明子类可以继承父类中的该注解。**



***

## 自定义注解

**使用 @interface 自定义注解时，自动继承了 java.lang.annotation.Annotation 接口**

### 分析

* **@interface 用来声明一个注解，格式：public @interface 注解名 { 定义内容 }**
* **其中每一个方法实际上是声明了一个配置参数，方法的名称就是参数的名称**
* **返回值类型就是参数的类（返回值只能是基本类型， Class， String， enum）**
* **可以通过default来声明参数的默认值。**
* **如果只有一个参数成员，一般参数名为value**
* **注解元素必须要有值，我们定义注解元素时，经常使用空字符串，0作为默认值。**





***

# 反射



## 动态语言 VS 静态语言

### 动态语言

* **在运行时可以改变其结构的语言。**
* **主要动态语言：Object-C、 C#、 JavaScript、PHP、 Python等**

### 静态语言

* **与动态语言相对应，运行时结构不可变的语言就是静态语言， 如Java、 C、 C++**
* **Java 不是动态语言，但 Java 可以称之为 “准动态语言”**



***

## Java Reflection

* **Reflection（反射）是 Java 被视为动态语言的关键，反射机制允许程序在执行期借助于 Reflection API 取得任何类的内部信息，并能直接操作任意对象的内部属性及方法。**

```Java
Class c = Class.forName("java.lang.String")
```

* **加载完类之后，在堆内存的方法区中就产生了一个 Class 类型的对象（一个类只有一个 Class 对象），这个对象就包含了完整的类的结构信息。我们可以通过这个对象看到类的结构。这个对象就像一面镜子，透过这个镜子看到类的结构，所以，我们形象的成为：反射**



### 优点

**可以实现动态创建对象和编译，体现出很大的灵活性**

### 缺点

* **对性能有影响。使用反射基本上是一种解释操作，我们可以告诉 JVM ，我们希望做什么并且它满足我们的要求。这类操作总是慢于直接执行相同的操作。**



### 反射相关的主要API

* **java.lang.Class：代表一个类**
* **java.lang.reflect.Method：代表类的方法**
* **java.lang.reflect.Field：代表类的成员变量**
* **java.lang.reflect.Constructor：代表类的构造器**
* **... ...**



### Class 类

**在Object类中定义了以下的方法，此方法将被所有子类继承**

```java
public final Class getClass()
```

**这个方法返回值类型是一个Class类，此类是Java反射的源头。**



| 方法名                            | 功能说明                                  |
| --------------------------------- | ----------------------------------------- |
| static Class.forName(String name) | 返回指定类名name的Class对象               |
| Object newInstance()              | 返回缺省构造函数，返回Class对象的一个实例 |
| getName()                         | 返回此Class对象所表示实体的名称           |
| Class getSuperClass()             | 返回父类的Class对象                       |
| Class[] getInterfaces()           | 返回当前Class对象的接口                   |
| ClassLoader getClassLoader()      | 返回该类的类加载器                        |
| Constructor[] getConstructors()   | 返回一个包含某些Constructor对象的数组     |



### 代码示例

```java
// 测试class类的创建方式有哪些
public class Test03 {
    public static void main(String[] args) throws ClassNotFoundException {
        Person person = new Student();
        System.out.println("这个人是：" + person.name);

        // 方式一：通过对象获得
        Class c1 = person.getClass();
        System.out.println(c1.hashCode());

        // 方式二：通过forname获取
        Class c2 = Class.forName("reflection.Student");
        System.out.println(c2.hashCode());

        // 方式三：通过类名.class 获得
        Class c3 = Student.class;
        System.out.println(c3.hashCode());

        // 方式四：基本内置类型的包装类都有一个Type属性
        Class c4 = Integer.TYPE;
        System.out.println(c4);

        // 获得父类类型
        Class c5 = c1.getSuperclass();
        System.out.println(c5);
    }

}


class Person {
    String name;

    public Person() {
    }


    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}


class Student extends Person{
    public Student(){
        this.name = "学生";
    }
}

class Teacher extends Person{
    public Teacher(){
        this.name = "老师";
    }
}
```



```java
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
```



