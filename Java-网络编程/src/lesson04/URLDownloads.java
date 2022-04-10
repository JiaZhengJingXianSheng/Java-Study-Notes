package lesson04;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: LYZ
 * @Date: 2022/03/17
 */
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
