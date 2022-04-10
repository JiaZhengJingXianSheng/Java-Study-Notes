package lesson01;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/14
 */
public class TestInetSocketAddress {
    public static void main(String[] args) {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",8080);
        InetSocketAddress socketAddress2 = new InetSocketAddress("localhost",8080);
        System.out.println(socketAddress);
        System.out.println(socketAddress2);

        System.out.println(socketAddress.getAddress());
        System.out.println(socketAddress.getHostName());    // 地址
        System.out.println(socketAddress.getPort());    // 端口
    }
}
