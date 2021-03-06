package com.cloudSeckill.net.socket.socket;

import com.google.gson.Gson;
import com.proxy.utils.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class SocketClient {

    private static String ipAddress;
    private static int port;
    private OutputStream os;
    private PrintWriter pw;
    private InputStream is;
    private BufferedReader br;

    public static void init(String ipAddress, int port) {
        SocketClient.ipAddress = ipAddress;
        SocketClient.port = port;
    }

    private HashMap<String, String> params = new HashMap();

    private Socket socket;

    public SocketClient() {
        try {
            socket = new Socket(ipAddress, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SocketClient(String ip, int port) {
        try {
            socket = new Socket(ip, port);
        } catch (Exception e) {
            System.out.println("访问地址：" + ip);
            System.out.println("访问端口：" + port);
            e.printStackTrace();
        }
    }

    public void putParam(String key, String value) {
        if (StringUtils.isEmpty(value)) {
            return;
        }
//        params.put(key, Base64.getEncoder().encodeToString(value.getBytes()));
        params.put(key, value);
    }

    public void sendData() {
        try {
            if (os == null) {
                os = socket.getOutputStream();
                pw = new PrintWriter(os);//将输出流包装成打印流
            }
            //字节输出流
            String json = new Gson().toJson(params);
            System.out.println("请求参数：" + json);
            pw.write(json);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.clear();
    }

    public String receiveData() {
        try {
            if (is == null) {
                is = socket.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
            }
            String data = "";
            String info;
            while ((info = br.readLine()) != null) {
                data += info;
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
            if (null != pw) {
                pw.close();
            }
        }
        return "";
    }
}
