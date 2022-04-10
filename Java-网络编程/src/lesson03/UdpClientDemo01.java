package lesson03;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/16
 */

// udp 不需要连接服务器，
// 发送端
public class UdpClientDemo01 {
    public static void main(String[] args) throws Exception {
        // 1.建立一个 socket
        DatagramSocket socket = new DatagramSocket();

        // 2.建个包
        String msg = "你好啊，服务器！";
        // 发送给谁
        InetAddress localhost = InetAddress.getLocalHost();
        int port = 9090;
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, localhost, port);

        // 3.发送包
        socket.send(packet);

        // 4.关闭流
        socket.close();

    }
}
