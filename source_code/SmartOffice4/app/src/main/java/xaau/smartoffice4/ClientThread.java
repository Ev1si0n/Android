package xaau.smartoffice4;

import android.os.Handler;
import android.os.Looper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Timer;

import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Mr.Li on 2017/5/26.
 */
public class ClientThread implements Runnable {
    public Socket s;
    Settings settings = new Settings();
    Login login = new Login();
    private Handler handler;
    public Handler revHandler;
    BufferedReader br = null;
    OutputStream os = null;


    public ClientThread(Handler handler) {
        this.handler = handler;
    }

    public void run() {
        String ip = login.defaultip;
        int port = login.defaultport;

        try {
            s = new Socket();
          //  System.out.println(ip);
            s.connect(new InetSocketAddress("" + ip, port), 10000);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = s.getOutputStream();
            new Thread(){
                @Override

                public void run() {
                    super.run();
                    try {
                        String content = null;
                        while ((content = br.readLine()) != null) {
                            Message msg = new Message();
                            msg.what = 0x1;//收
                            msg.obj = content;
                            handler.sendMessage(msg);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }.start();

            Looper.prepare();

            revHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {//发
                    if (msg.what == 0x2)//如果msg来自登陆界面的用户信息将数据写入网络
                    {

                        try {
                            os.write((msg.obj.toString() + "\r\n").getBytes("utf-8"));
                           // System.out.println("123");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

            };
            Looper.loop();

        } catch (SocketTimeoutException e1) {
            System.out.println("网络连接超时！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}



