package lesson03;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/16
 */

// 等待接受
public class UdpServerDemo01 {
    public static void main(String[] args) throws Exception {
        // 开放端口
        DatagramSocket socket = new DatagramSocket(9090);

        // 接收数据包
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);// 接收

        socket.receive(packet);     // 阻塞接受
        System.out.println(new String(packet.getData()));
        // 关闭连接
        socket.close();
    }
}
