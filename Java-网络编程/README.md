# Java 网络编程

<!-- toc -->

[TOC]

## 测试IP

```java
// 测试IP
public class TestInetAddress {
    public static void main(String[] args) {
        try {
            // 查询本机地址
            InetAddress inetAddress1 = InetAddress.getByName("127.0.0.1");
            System.out.println(inetAddress1);
            InetAddress inetAddress2 = InetAddress.getByName("localhost");
            System.out.println(inetAddress2);
            InetAddress inetAddress3 = InetAddress.getLocalHost();
            System.out.println(inetAddress3);

            // 查询百度地址
            InetAddress inetAddress4 = InetAddress.getByName("www.baidu.com");
            System.out.println(inetAddress4);

            // 常用方法
            System.out.println(inetAddress1.getCanonicalHostName()); //规范的名字
            System.out.println(inetAddress1.getHostAddress()); // ip
            System.out.println(inetAddress1.getHostName()); // 域名，或者自己电脑的名字

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
```



***

## 端口

* 范围 0-65535

* 端口分类

  * 公有端口 0-1023

    * HTTP: 80
    * HTTPS: 443
    * FTP: 21
    * SSH: 22
    * Telent: 23

  * 程序注册端口: 1024-49151, 分配给用户或者程序

    * Tomcat: 8080
    * MySQL: 3306
    * Oracle: 1521

  * 动态、私有：49152-65535

    

```shell
netstat -ano #查看所有端口
netstat -ano|findstr "5900" #查看指定端口
tasklist|find "8689" #查看指定端口的进程
```



```java
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
```



***

## 通信协议

### OSI 协议

**七层**划分为：**应用层、表示层、会话层、传输层、网络层、数据链路层、物理层**。

**五层**划分为：**应用层、传输层、网络层、数据链路层、物理层**。



### TCP/IP协议簇

重要：

* TCP：用户传输协议
* UDP：用户数据报协议

出名的协议：

* TCP：

* IP：网络互联协议

  

### TCP UDP对比

**TCP面向连接 (三次握手)**

**UDP面向无连接**



***

## TCP 实现

### 客户端

```java
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
```

### 服务端

```java
// 服务端
public class TcpServerDemo01 {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            // 1.得有一个地址
            serverSocket = new ServerSocket(9999);
            while(true){
                // 2.等待客户端连接
                socket = serverSocket.accept();
                // 3.读取客户端消息
                is = socket.getInputStream();

                // 管道流
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                System.out.println(baos.toString());
            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```



## TCP 实现文件上传

### 客户端

```java
// 客户端
public class TcpClientDemo02 {
    public static void main(String[] args) throws Exception {
        // 1.创建一个socket
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9000);

        // 2.创建一个输出流
        OutputStream os = socket.getOutputStream();

        // 3.读取文件
        FileInputStream fis = new FileInputStream(new File("头像.jpg"));

        // 4.写出文件
        byte[] buffer = new byte[1024];
        int len;
        while((len = fis.read(buffer))!=-1){
            os.write(buffer,0,len);
        }

        // 通知服务器，发送完毕
        socket.shutdownOutput();    // 已经发送完毕

        // 确定服务器接受完毕，才能断开连接
        InputStream is = socket.getInputStream();
        // String byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer2 = new byte[1024];
        int len2;
        while((len2 = is.read(buffer2))!=-1){
            baos.write(buffer2,0,len2);
        }
        System.out.println(baos.toString());
        // 5.关闭资源
        baos.close();
        fis.close();
        os.close();
        socket.close();

    }
}

```

### 服务端

```java
// 服务端
public class TcpServerDemo02 {
    public static void main(String[] args) throws Exception {
        // 1.创建服务
        ServerSocket serverSocket = new ServerSocket(9000);
        // 2.监听客户端连接
        Socket socket = serverSocket.accept();// 阻塞式监听，会一直等待客户端
        // 3.获取输入流
        InputStream is = socket.getInputStream();
        // 4.文件输出
        FileOutputStream fos = new FileOutputStream(new File("receive.jpg"));
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
```



***

## Tomcat

**服务端**

* 自定义S
* Tomcat服务器S

**客户端**

* 自定义C
* 浏览器B



***

## UDP 消息发送

### 发送端

```java
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
```

### 接收端

```java
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
```



***

## UDP 实现聊天

### 接收端

```java
// 接收端
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
```

### 发送端

```java
// 发送端
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
```



## 在线咨询

**双方都可收发**

### 发送类

```java
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
```



### 接受类

```java
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
```



**开启聊天线程**

### 启动聊天

```java
public class TalkTeacher {
    public static void main(String[] args) {
        new Thread(new TalkSend(5555,"localhost",6666)).start();
        new Thread(new TalkReceive(8888,"学生")).start();

    }
}
```



```java
public class TalkStudent {
    public static void main(String[] args) {
        // 开启两个线程
        new Thread(new TalkSend(7777,"localhost",8888)).start();
        new Thread(new TalkReceive(6666,"老师")).start();

    }
}
```



***

## URL

**统一资源定位符：定位互联网上某一个资源。**

**DNS：域名解析**

> 协议 : // ip地址 : 端口 /  项目名 / 资源



```java
public class URLDemo01 {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8080/helloworld/index.jsp?username=test&password=123");
        System.out.println(url.getProtocol());      // 协议
        System.out.println(url.getHost());          // 主机IP
        System.out.println(url.getPort());          // 端口
        System.out.println(url.getPath());          // 文件
        System.out.println(url.getFile());          // 全路径
        System.out.println(url.getQuery());         // 参数
    }
}

```

### URL 下载资源

**将URL的路径换成网络资源链接，就可以下载网络资源。**

```java
public class URLDownloads {
    public static void main(String[] args) throws Exception {
        // 1.下载地址
        // 需要提前在 tomcat 的 webapp 路径下配置下
        URL url = new URL("http://localhost:8080/lyz/SecurityFile.txt");
        // 2.连接到这个资源
        URLConnection urlConnection = (HttpURLConnection)url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();

        FileOutputStream fos = new FileOutputStream("SecurityFile.txt");
        byte[] buffer = new byte[1024];
        int len;
        while((len = inputStream.read(buffer))!=-1){
            fos.write(buffer,0,len);      // 写出这个数据
        }
        fos.close();
        inputStream.close();
    }
}
```

