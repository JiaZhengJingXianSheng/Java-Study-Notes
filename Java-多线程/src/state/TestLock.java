package state;

import java.util.concurrent.locks.ReentrantLock;

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
