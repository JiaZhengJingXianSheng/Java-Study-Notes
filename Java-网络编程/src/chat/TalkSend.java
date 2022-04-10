package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/17
 */
public class TalkSend implements Runnable{
    private DatagramSocket socket;
    private BufferedReader reader;

    private int fromPort;
    private String toIP;
    private int toPort;

    public TalkSend(int fromPort, String toIP, int toPort) {
        this.fromPort = fromPort;
        this.toIP = toIP;
        this.toPort = toPort;

        try {
            this.socket = new DatagramSocket(this.fromPort);

        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("socket 创建错误！");
        }
    }


    @Override
    public void run() {

        while(true){
            // 准备数据: 控制台读取
            reader = new BufferedReader(new InputStreamReader(System.in));
            String data = null;
            try {
                data = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            DatagramPacket packet = new DatagramPacket(data.getBytes(),data.getBytes().length, new InetSocketAddress(this.toIP ,this.toPort));

            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (data.equals("bye")){
                break;
            }
        }

        socket.close();
    }
}
