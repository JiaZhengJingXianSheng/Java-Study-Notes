package state;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/09
 */

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
