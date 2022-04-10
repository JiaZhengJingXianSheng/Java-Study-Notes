package state;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/09
 */

// 测试生产者-消费者模型 --》利用缓冲区解决：管程法
// 生产者、消费者、产品、缓冲区
public class TestPC {
    public static void main(String[] args) {
        SynContainer container = new SynContainer();
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
        for (int i = 0; i < 150; i++) {

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
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                System.out.println("等待消费...");
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
                System.out.println("等待生产...");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 如果可以消费
        Product product = null;
        if (count > 0) {
            count--;
            product = products[count];
        }


        // 吃完了，通知生产者生产
        this.notifyAll();
        return product;
    }

}