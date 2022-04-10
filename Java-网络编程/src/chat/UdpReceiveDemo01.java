package chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/16
 */
public class UdpReceiveDemo01 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(6666);


        while (true){
            // 准备接收包裹
            byte[] container = new byte[1024];
            DatagramPacket packet = new DatagramPacket(container,container.length);

            socket.receive(packet);     // 阻塞式接收

            // 断开连接 bye
            byte[] data = packet.getData();
            String receiveData = new String(data, 0, data.length);

            System.out.println(receiveData);
            // .trim() 是去空格、以及多余的符号
            if(receiveData.trim().equals("bye")){
                break;
            }

        }

    }
}
