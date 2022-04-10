package chat;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/17
 */
public class TalkTeacher {
    public static void main(String[] args) {
        new Thread(new TalkSend(5555,"localhost",6666)).start();
        new Thread(new TalkReceive(8888,"学生")).start();

    }
}
