package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/16
 */
public class UdpSenderDemo01 {
    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket(8888);

        // 准备数据: 控制台读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            String data = reader.readLine();

            DatagramPacket packet = new DatagramPacket(data.getBytes(),data.getBytes().length, new InetSocketAddress("localhost",6666));

            socket.send(packet);
            if (data.equals("bye")){
                break;
            }
        }


        socket.close();
    }
}
