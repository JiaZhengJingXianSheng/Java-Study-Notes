package lesson02;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/15
 */
public class TcpServerDemo02 {
    public static void main(String[] args) throws Exception {
        // 1.创建服务
        ServerSocket serverSocket = new ServerSocket(9000);
        // 2.监听客户端连接
        Socket socket = serverSocket.accept();// 阻塞式监听，会一直等待客户端
        // 3.获取输入流
        InputStream is = socket.getInputStream();
        // 4.文件输出
        FileOutputStream fos = new FileOutputStream(new File("Java-网络编程/receive.jpg"));
        byte[] buffer = new byte[1024];
        int len;
        while((len=is.read(buffer))!=-1){
            fos.write(buffer,0,len);
        }

        // 通知客户端接受完毕
        OutputStream os = socket.getOutputStream();
        os.write("server接收完毕".getBytes());

        // 关闭资源
        fos.close();
        is.close();
        socket.close();
        serverSocket.close();
    }
}
