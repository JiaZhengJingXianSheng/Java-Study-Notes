
# Java多线程

<!-- toc -->

[TOC]

## 线程创建

> 继承Thread类
>
> 实现Runnable接口
>
> 实现Callable接口 （了解）



### 一、继承Thread类

>1. 自定义线程类继承Thread类
>2. 重写run() 方法， 编写线程执行体
>3. 创建线程对象， 调用start() 方法启动线程

```java
// 创建线程方式一： 继承Thread类, 重写run() 方法， 调用start开启线程
public class TestThread1 extends Thread {
    @Override
    public void run() {
        // run 方法线程体
        for (int i = 0; i < 20; i++) {
            System.out.println("我在看代码" + i);
        }
    }
    
    // main 线程 主线程
    public static void main(String[] args) {
        // 创建一个线程对象
        TestThread1 testThread1 = new TestThread1();
        // 调用start方法开启线程
        testThread1.start();
        for (int i = 0; i < 20; i++) {
            System.out.println("我在学习多线程" + i);
        }
    }
}
```





#### 实现简单下载器

```java
// 练习Thread ， 实现多线程同步下载图片
public class TestThread2 extends Thread {
    private String url; // 网络图片地址
    private String name; // 保存文件名

    public TestThread2(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public void run() {
        WebDownloader webDownloader = new WebDownloader();
        webDownloader.downloader(url, name);
        System.out.println("下载了文件名为： " + name);
    }

    // 主线程
    public static void main(String[] args) {
        TestThread2 t1 = new TestThread2("https://z3.ax1x.com/2021/10/15/58F85q.jpg", "image/dog1.jpg");
        TestThread2 t2 = new TestThread2("https://z3.ax1x.com/2021/10/15/58F85q.jpg", "image/dog2.jpg");
        TestThread2 t3 = new TestThread2("https://z3.ax1x.com/2021/10/15/58F85q.jpg", "image/dog3.jpg");

        t1.start();
        t2.start();
        t3.start();
    }
}


// 下载器
class WebDownloader {
    // 下载方法
    public void downloader(String url, String name) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO异常，downloader方法出现问题");
        }
    }
}
```





### 二、实现Runnable接口

> 1. 定义MyRunnable类实现Runnable接口
> 2. 实现run() 方法， 编写线程执行体
> 3. 创建线程对象， 调用start() 方法启动线程

```java
/ 创建线程方式2： 实现runnable接口， 重写run方法， 执行线程须丢入runnable接口实现类。调用start方法
public class TestThread3 implements Runnable{
    @Override
    public void run() {
        // run 方法线程体
        for (int i = 0; i < 20; i++) {
            System.out.println("我在看代码" + i);
        }
    }

    public static void main(String[] args) {
        // 创建runnable接口的实现类对象
        TestThread3 testThread3 = new TestThread3();

        // 创建线程对象， 通过线程对象来开启我们的线程 -- 代理
//        Thread thread = new Thread(testThread3);
//        thread.start();

        // 简写
        new Thread(testThread3).start();

        for (int i = 0; i < 20; i++) {
            System.out.println("我在学习多线程" + i);
        }
    }
}
```





### 三、实现Callable接口

> 				1. 实现callable接口， 需要返回值类型
> 				1. 重写call方法， 需要抛出异常
> 				1. 创建目标对象
> 				4. 创建执行服务  ExecutorService ser = Executors.newFixedThreadPool(3);
> 				1. 提交执行 Future<Boolean> r1 = ser.submit(t1);
> 				1. 获取结果  Boolean rs1 = r1.get();
> 				1. 关闭服务  ser.shutdownNow();



```java
// Callable好处： 可以定义返回值， 可以抛出异常   
// 线程创建方式三： 实现callable接口 <Boolean>是返回值
public class TestCallable implements Callable<Boolean> {

    private String url; // 网络图片地址
    private String name; // 保存文件名

    public TestCallable(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public Boolean call() {
        WebDownloader webDownloader = new WebDownloader();
        webDownloader.downloader(url, name);
        System.out.println("下载了文件名为： " + name);
        return true;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TestCallable t1 = new TestCallable("https://z3.ax1x.com/2021/10/15/58F85q.jpg", "image/dog1.jpg");
        TestCallable t2 = new TestCallable("https://z3.ax1x.com/2021/10/15/58F85q.jpg", "image/dog2.jpg");
        TestCallable t3 = new TestCallable("https://z3.ax1x.com/2021/10/15/58F85q.jpg", "image/dog3.jpg");

        // 创建执行服务
        ExecutorService ser = Executors.newFixedThreadPool(3);
        // 提交执行
        Future<Boolean> r1 = ser.submit(t1);
        Future<Boolean> r2 = ser.submit(t2);
        Future<Boolean> r3 = ser.submit(t3);
        // 获取结果
        Boolean rs1 = r1.get();
        Boolean rs2 = r2.get();
        Boolean rs3 = r3.get();
        
        System.out.println(rs1);
        System.out.println(rs2);
        System.out.println(rs3);
        // 关闭服务
        ser.shutdownNow();

    }
}
// 下载器
class WebDownloader {
    // 下载方法
    public void downloader(String url, String name) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO异常，downloader方法出现问题");
        }
    }
}
```



## 小结

**不建议使用 继承Thread类 ： 避免OOP单继承局限性**

**推荐使用 实现Runnable接口 ： 方便灵活，方便同一个对象被多个线程使用**

**Callable好处： 可以定义返回值， 可以抛出异常**



## 线程不安全

```java
// 多个线程同时操作同一个对象
// 买火车票的例子
// 发现问题：多个线程操作同一个资源的情况下，线程不安全，数据紊乱
public class TestThread4 implements Runnable {
    
    // 票数
    private int ticketNums = 10;
    
    @Override
    public void run() {
        while (true) {
            if (ticketNums <= 0)
                break;
            // 模拟延时
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "-->拿到了第" + ticketNums-- + "票");
        }
    }

    public static void main(String[] args) {
        TestThread4 ticket = new TestThread4();

        new Thread(ticket, "小明").start();
        new Thread(ticket, "老师").start();
        new Thread(ticket, "黄牛党").start();

    }
}
```





## 龟兔赛跑例子

```java
// 模拟龟兔赛跑
public class Race implements Runnable {
    // 胜利者
    private static String winner;

    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            // 模拟兔子休息
            if (Thread.currentThread().getName().equals("兔子") && i%10 ==0){
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 判断比赛是否结束
            boolean flag = gameOver(i);
            if (flag)
                break;

            System.out.println(Thread.currentThread().getName() + "-->跑了" + i + "步");
        }
    }

    // 判断是否完成比赛
    private boolean gameOver(int steps) {
        // 判断是否有胜利者
        if (winner != null) {//已经存在
            return true;
        }
        if(steps >= 100){
            winner = Thread.currentThread().getName();
            System.out.println("winner is " + winner);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Race race = new Race();

        new Thread(race,"兔子").start();
        new Thread(race,"乌龟").start();
    }
}
```



## 静态代理模式

```java
// 静态代理模式总结
// 真实对象和代理对象都要实现同一个接口
// 代理对象要代理真实角色

// 好处：
//      代理对象可以做很多真实对象做不了的事
//      真实对象专注做自己的事
public class StaticProxy {
    public static void main(String[] args) {

        new Thread(()-> System.out.println("I LOVE YOU")).start();

        You you = new You(); // 你要结婚
        WeddingCompany weddingCompany = new WeddingCompany(you);
        weddingCompany.HappyMarry();
    }
}

interface Marry {
    void HappyMarry();

}

// 真实角色， 你去结婚
class You implements Marry {

    @Override
    public void HappyMarry() {
        System.out.println("老师要结婚了，超开心");
    }
}

// 代理角色，帮助你结婚
class WeddingCompany implements Marry {
    // 代理真实目标角色
    private Marry target;

    public WeddingCompany(Marry target){
        this.target = target;
    }

    @Override
    public void HappyMarry() {
        before();
        this.target.HappyMarry(); // 真实对象
        after();
    }

    private void after() {
        System.out.println("结婚之后， 收尾款");
    }

    private void before() {
        System.out.println("结婚之前，布置现场");
    }
}
```



## Lamda表达式

>**避免匿名内部类定义过多**
>
>**实质属于函数式编程的概念**
>
>**(params) -> expression[表达式]**
>
>**(params) ->statement[语句]**
>
>**(params) -> {statements}**
>
>> **避免匿名内部类定义过多**
>
>> **只留下核心代码**



### 函数式接口：

**任何接口，如果只包含唯一一个抽象方法，那他就是一个函数式接口**

```java
public interface Runnable{
	public abstract void run();
}
```

**对于函数式接口，我们可以通过lambda表达式来创建该接口的对象**

```java
public class TestLambda2 {
    public static void main(String[] args) {
        Ilove love = (int a) -> {
                System.out.println("I love you --> " + a);
        };
        love.love(520);
    }
}

interface Ilove{
    void love(int a);
}
```



### 简化

```java
        // 简化1. 参数类型
        Ilove love = (a)->{
            System.out.println("I love you --> " + a);
        };
        love.love(520);

        // 简化2. 简化括号
        Ilove love = a->{
            System.out.println("I love you --> " + a);
        };
        love.love(520);

        // 简化3. 去掉花括号  (代码只有一行可简化)
        Ilove love = a-> System.out.println("I love you --> " + a);
        love.love(520);
```

### 总结

**lambda表达式只有一行代码的情况下才能简化成为1行，如果有多行，用 {} 包裹**

**前提是： 接口为函数式接口**







## 线程停止



```java
// 测试线程停止
// 建议使用标志位
// 不建议使用 stop 或 destroy 等
public class TestStop implements Runnable {
    // 设置标志位
    private Boolean flag = true;

    @Override
    public void run() {
        int i = 0;
        while (flag) {
            System.out.println("run...Thread" + i++);
        }
    }

    // 设置公开方法，停止线程
    public void stop() {
        this.flag = false;
    }

    public static void main(String[] args) {
        TestStop testStop = new TestStop();
        new Thread(testStop).start();
        for (int i = 0; i < 1000; i++) {
            System.out.println("main " + i);
            if (i == 900) {
                // 调用自定义stop 方法，切换标志位，让线程停止
                testStop.stop();
                System.out.println("线程停止");
            }
        }

    }
}
```





***

## 线程休眠

**Thread.sleep(int ms)**



***

## 线程礼让

**Thread.yield()**



***

## 线程强制执行 join

**thread.join()  其中thread是一个线程**



***

## 同步方法和同步块

**由于我们可以通过 private 关键字来保证数据对象只能被方法访问，所以我们只需要针对方法提出一套机制，这套机制就是 synchronized 关键字，它包括两种用法： synchronized 方法和 synchronized 块**

**synchronized方法控制对 “对象” 的访问，每个对象对应一把锁，每个synchronized方法都必须获得调用该方法的对象的锁才能执行，否则线程阻塞，方法一旦执行，就独占该锁，直到该方法返回才释放锁，后面被阻塞的线程才能获得这个锁，继续执行。**

**缺陷： 会影响效率**



***

## CopyOnWriteArrayList

```java
// 测试 JUC 安全类型的集合
public class TestJUC {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
        for (int i = 0; i < 10000; i++) {
            new Thread(
                    ()->{
                        list.add(Thread.currentThread().getName());
                    }
            ).start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
    }
}
```





***

## 死锁

**死锁是指两个或两个以上的进程在执行过程中，由于竞争资源或者由于彼此通信而造成的一种阻塞的现象，若无外力作用，它们都将无法推进下去。此时称系统处于死锁状态或系统产生了死锁，这些永远在互相等待的进程称为死锁进程**



****

## Lock锁

**显式锁**

```
private final ReentrantLock lock = new ReentrantLock();
try{
	lock.lock(); // 加锁
	// 处理
}finally{
	lock.unlock(); // 解锁
}

```

```java
/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/09
 */

// 测试 Lock 锁
public class TestLock {

    public static void main(String[] args) {
        TestLock2 testLock2 = new TestLock2();
        new Thread(testLock2).start();
        new Thread(testLock2).start();
        new Thread(testLock2).start();

    }
}

class TestLock2 implements Runnable{

    int tickNums = 10;

    // 定义 lock 锁
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while(true){
            try{
                lock.lock();
                if (tickNums>0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(tickNums--);
                }else{
                    break;
                }
            }finally {
                lock.unlock();
            }
        }
    }

//  未加锁
//    @Override
//    public void run() {
//        while(true){
//            if (tickNums>0){
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println(tickNums--);
//            }else{
//                break;
//            }
//        }
//    }
}
```



***

## 生产者消费者问题

**生产者消费者问题，也称有限缓冲问题。生产者和消费者共享一个资源**

**对于生产者，没有生产产品之前，要通知消费者等待，而生产了产品之后，又要马上通知消费者消费。**

**对于消费者，在消费之后，要通知生产者已经结束消费，需要生产新的产品以供消费。**

**在生产者消费者问题中，仅有synchronized 是不够的**

**synchronized 可阻止并发更新同一个共享资源，实现了同步**

**synchronized 不能用来实现不同线程之间的消息传递（通信）**



**Java 提供了几个方法解决线程之间的通信问题**

``` java
wait(); // 表示线程一直等待，与sleep不同，会释放锁。
notify(); // 唤醒一个处于等待状态的线程
notifyAll(); // 唤醒同一个对象上所有调用wait()方法的线程，优先级别高的线程优先调度。
```



### 解决方法一 （管程法）

* **生产者：负责生产数据的模块**
* **消费者：负责处理数据的模块**

* **缓冲区：消费者不能直接使用生产者的数据，他们之间有个 “ 缓冲区 ”**

**生产者将生产好的数据放入缓冲区，消费者从缓冲区拿出数据。**

```java
/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/09
 */

// 测试生产者-消费者模型 --> 利用缓冲区解决：管程法
// 生产者、消费者、产品、缓冲区
public class TestPC {
    public static void main(String[] args) {
        SynContainer container= new SynContainer();
        new Productor(container).start();
        new Consumer(container).start();
        new Consumer(container).start();
    }
}

// 生产者
class Productor extends Thread {
    SynContainer container;

    public Productor(SynContainer container) {
        this.container = container;
    }

    // 生产
    @Override
    public void run() {
        // 生产 100 个产品
        for (int i = 0; i < 200; i++) {

            container.push(new Product(i));
            System.out.println("生产了" + i + "个产品");
        }
    }
}

// 消费者
class Consumer extends Thread {
    SynContainer container;

    public Consumer(SynContainer container) {
        this.container = container;
    }

    // 消费

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("消费了-->" + container.pop().id + "个产品");
        }
    }
}

// 产品
class Product {
    int id; // 产品编号

    public Product(int id) {
        this.id = id;
    }
}

// 缓冲区
class SynContainer {
    // 需要一个容器大小
    Product[] products = new Product[10];
    // 容器计数器
    int count = 0;

    // 生产者放入产品
    public synchronized void push(Product product) {
        // 如果容器满了，等待消费者消费
        // if语句中醒来的线程 不会再一次进行判断了 而while会重新再判断
        while (count == products.length) {
            // 通知消费者消费，生产者等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        // 如果没有满，生产产品
        products[count] = product;
        count++;


        //可以通知消费者消费了
        this.notifyAll();
    }

    // 消费者消费产品
    public synchronized Product pop() {
        // 判断能否消费
        // if语句中醒来的线程 不会再一次进行判断了 而while会重新再判断
        while (count <= 0) {
            // 等待生产者生产
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 如果可以消费
        Product product = null;
        if(count>0){
            count--;
            product = products[count];
        }


        // 吃完了，通知生产者生产
        this.notifyAll();
        return product;
    }

}
```



### 解决方法二 （信号灯法）

**设置标志位**

```java
/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/10
 */

// 测试生产者-消费者问题2： 信号灯法，设置标志位
public class TestPC2 {
    public static void main(String[] args) {
        TV tv = new TV();
        new Player(tv).start();
        new Watcher(tv).start();
    }
}

// 生产者 --> 演员
class Player extends Thread {
    TV tv;

    public Player(TV tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                this.tv.play("快乐大本营");
            }
            else{
                this.tv.play("抖音：记录美好生活");
            }
        }
    }
}

// 消费者 --> 观众
class Watcher extends Thread {
    TV tv;

    public Watcher(TV tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            tv.watch();
        }
    }
}

// 产品 --> 节目
class TV {
    // 演员表演，观众等待
    // 观众观看，演员等待
    String voice; // 表演的节目
    boolean flag = true;

    // 表演
    public synchronized void play(String voice) {
        while (!flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("演员表演了" + voice);
        // 通知观众观看
        this.notifyAll(); // 唤醒
        this.voice = voice;
        this.flag = !this.flag;
    }

    // 观看
    public synchronized void watch() {
        while (flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("观众观看了" + voice);

        // 通知演员表演
        this.notifyAll();
        this.flag = !this.flag;
    }
}
```





***

## 线程池

**如果并发的线程数量很多，并且每个线程都是执行一个时间很短的任务就结束了，这样频繁创建线程就会大大降低系统的效率，因为频繁创建线程和销毁线程需要时间。线程池可以实现复用。**

**JDK 5.0 起提供了线程池相关的API ：ExecutorService 和 Executors **

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/11
 */

// 测试线程池
public class TestPool {
    public static void main(String[] args) {
        // 1. 创建线程池
        //    newFixedThreadPool 参数为线程池大小
        ExecutorService service = Executors.newFixedThreadPool(10);
        // 执行
        service.execute(new MyThread());
        service.execute(new MyThread());
        service.execute(new MyThread());
        service.execute(new MyThread());

        // 2. 关闭连接
        service.shutdown();
    }
}


class MyThread implements Runnable {

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName());

    }
}
```

