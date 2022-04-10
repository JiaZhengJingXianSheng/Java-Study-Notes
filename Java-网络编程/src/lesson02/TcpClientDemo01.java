package lesson02;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/15
 */

// 客户端
public class TcpClientDemo01 {
    public static void main(String[] args) {
        Socket socket = null;
        OutputStream os = null;
        try {
            // 1.要知道服务器的地址
            InetAddress serverIp = InetAddress.getByName("127.0.0.1");
            int port = 9999;
            // 2.创建一个socket连接
            socket = new Socket(serverIp,port);
            // 3.发送消息 IO 流
            os = socket.getOutputStream();
            os.write("hello,world".getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
