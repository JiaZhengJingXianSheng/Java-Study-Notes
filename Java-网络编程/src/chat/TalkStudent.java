package chat;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/17
 */
public class TalkStudent {
    public static void main(String[] args) {
        // 开启两个线程
        new Thread(new TalkSend(7777,"localhost",8888)).start();
        new Thread(new TalkReceive(6666,"老师")).start();

    }
}
