package com.github.jamsa.rap;

import com.sun.tools.internal.ws.wsdl.document.Output;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class AA {

    public static String bb(String urlstr,String params){
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlstr);
            urlConnection = (HttpURLConnection) url.openConnection();

            /* optional request header */
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            /* optional request header */
            urlConnection.setRequestProperty("Accept", "application/json");


            // read response
            /* for Get request */
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());

            wr.writeBytes(params);
            wr.flush();
            wr.close();
            // try to get response
            int statusCode = urlConnection.getResponseCode();
            System.out.println(statusCode);
            if (statusCode == 200) {

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    public static String sendPost(String url, String params) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            //conn.setRequestProperty("accept", "*/*");
            //conn.setRequestProperty("connection", "Keep-Alive");
            //conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf8");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Cache-Control", "no-cache");

            //conn.setRequestProperty("Postman-Token", "6fd48a9d-6e8a-44f6-8fec-925c108a91d9");




            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStream os = conn.getOutputStream();
            // 获取URLConnection对象对应的输出流
            //out = new PrintWriter(os);

            // 发送请求参数
            //out.print(params);
            os.write(params.getBytes());
            os.flush();
            os.close();


            // flush输出流的缓冲
            //out.flush();
            //out.close();

            int respCode = conn.getResponseCode();
            InputStream is;
            if(respCode==200) {
                is = conn.getInputStream();

            }else{
                is = conn.getErrorStream();
            }
            //获取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();

            //OutputStream os = conn.getOutputStream();
            //os.write("{'IsVb':0,'IsCompressed':1,'FilePath':'\\\\Entrance\\\\TrainInfo\\\\EnterRecord\\\\2018\\10\\30\\20181030033726.jpg','Token':'Test','FromIp':'127.0.0.1','FromApp':'cyj','IsSpecial':0,'Ip':'','App':''}".getBytes());
            //os.flush();

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = in.readLine()) != null) {
                System.out.println(line);
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String out = sendPost("http://192.168.25.7:8012/Device/Api/FileDownLoads/ReadImgFile","{'IsVb':0,'IsCompressed':1,'FilePath':'\\\\Entrance\\\\TrainInfo\\\\EnterRecord\\\\2018\\\\10\\\\30\\\\20181030033726.jpg','Token':'Test','FromIp':'127.0.0.1','FromApp':'cyj','IsSpecial':0,'Ip':'cc','App':'cc'}");
        //String aa = new BASE64Encoder().encode("{'IsVb':0,'IsCompressed':1,'FilePath':'20181030033726jpg','Token':'Test','FromIp':'127.0.0.1','FromApp':'cyj','IsSpecial':0,'Ip':'','App':''}".getBytes());
        //System.out.println(aa);
        //String out = sendPost("http://192.168.25.7:8012/Device/Api/FileDownLoads/ReadImgFile",out);
        System.out.println(out);
    }
}
