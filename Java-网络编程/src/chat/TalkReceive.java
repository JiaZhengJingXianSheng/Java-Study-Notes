package chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/17
 */
public class TalkReceive implements Runnable{
    private DatagramSocket socket;
    private int port;
    private String msgFrom;

    public TalkReceive(int port, String msgFrom) {
        this.msgFrom = msgFrom;
        this.port = port;
        try {
            this.socket = new DatagramSocket(this.port);
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("socket 创建错误！");
        }
    }

    @Override
    public void run() {

        while (true){
            // 准备接收包裹
            try {
                byte[] container = new byte[1024];
                DatagramPacket packet = new DatagramPacket(container,container.length);

                socket.receive(packet);     // 阻塞式接收

                // 断开连接 bye
                byte[] data = packet.getData();
                String receiveData = new String(data, 0, data.length);

                System.out.println(msgFrom + ": "+ receiveData);
                // .trim() 是去空格、以及多余的符号
                if(receiveData.trim().equals("bye")){
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}
