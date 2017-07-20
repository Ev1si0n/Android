package xaau.smartoffice4;

import android.app.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class MainActivity extends Activity {
    TextView WD, SD, GZ, RS;
    Button refresh;
    Message msg = new Message();
    Handler handler;
    ClientThread clientThread;
    Switch AC,curtain,light,RSs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  refresh = (Button)this.findViewById(R.id.refresh);
        WD = (TextView) this.findViewById(R.id.WD);
       // button = (Button) this.findViewById(R.id.button);
        SD = (TextView)this.findViewById(R.id.SD);
        GZ = (TextView)this.findViewById(R.id.GZ);
        //RS = (TextView)this.findViewById(R.id.RS);\
        AC = (Switch)this.findViewById(R.id.AC);
        curtain = (Switch)this.findViewById(R.id.curtain);//curtainOPEN打开窗帘
        light = (Switch)this.findViewById(R.id.light) ;//lightOPEN灯光打开
        handler = new Handler(){
        @Override
        public  void handleMessage(Message msg){
                if(msg.what == 0x1){//收
                    String info = msg.obj.toString();
                    //WD.append("\n"+msg.obj.toString());
                   //System.out.println(""+info);
                    String WDinfo = info.substring (0,2);
                    String SDinfo = info.substring (2,4);
                    String GZinfo = info.substring (4,6);
                   WD.setText("温度："+WDinfo+"℃");
                    SD.setText("湿度："+SDinfo+"%");
                    GZ.setText("光照："+GZinfo);

                }
            }
        };
        clientThread = new ClientThread(handler);
        new Thread(clientThread).start();
       /* refresh.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    Message msg = new Message();//实例化一个message对象
                    msg.what = 0x2;//发
                    msg.obj = "login_success";
                    clientThread.revHandler.sendMessage(msg);
                } catch (Exception e) {
                    DialogUtil.showDialog(MainActivity.this, "服务器响应异常", false);
                    e.printStackTrace();

                }
            }
            });*/
       /* try {
            Message msg = new Message();//实例化一个message对象
            msg.what = 0x2;//发
            msg.obj = "loginsuccess";//空调打开
            clientThread.revHandler.sendMessage(msg);
            System.out.println(""+msg.obj.toString());
        } catch (Exception e) {
            DialogUtil.showDialog(MainActivity.this, "服务器响应异常", false);
            e.printStackTrace();
        }*/
        AC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b){
                String s;
                if (b){
                    s = "空调开";
                    try {
                        Message msg = new Message();//实例化一个message对象
                        msg.what = 0x2;//发
                        msg.obj = "ACOPEN";//空调打开
                        clientThread.revHandler.sendMessage(msg);
                    } catch (Exception e) {
                        DialogUtil.showDialog(MainActivity.this, "服务器响应异常", false);
                        e.printStackTrace();
                }
                }
                else {
                    s = "空调关";
                    try {
                        Message msg = new Message();//实例化一个message对象
                        msg.what = 0x2;//发
                        msg.obj = "ACCLOSE";//空调关闭
                        clientThread.revHandler.sendMessage(msg);
                    } catch (Exception e) {
                        DialogUtil.showDialog(MainActivity.this, "服务器响应异常", false);
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
            }
        });
        curtain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b){
                String s;
                if (b){
                    s = "窗帘开";
                    try {
                        Message msg = new Message();//实例化一个message对象
                        msg.what = 0x2;//发
                        msg.obj = "curtainOPEN";//窗帘打开
                        clientThread.revHandler.sendMessage(msg);
                    } catch (Exception e) {
                        DialogUtil.showDialog(MainActivity.this, "服务器响应异常", false);
                        e.printStackTrace();
                    }
                }
                else {
                    s = "窗帘关";
                    try {
                        Message msg = new Message();//实例化一个message对象
                        msg.what = 0x2;//发
                        msg.obj = "curtainCLOSE";//窗帘关闭
                        clientThread.revHandler.sendMessage(msg);
                    } catch (Exception e) {
                        DialogUtil.showDialog(MainActivity.this, "服务器响应异常", false);
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
            }
        });
        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b){
                String s;
                if (b){
                    s = "灯打开";
                    try {
                        Message msg = new Message();//实例化一个message对象
                        msg.what = 0x2;//发
                        msg.obj = "lightOPEN";//灯打开
                        clientThread.revHandler.sendMessage(msg);
                    } catch (Exception e) {
                        DialogUtil.showDialog(MainActivity.this, "服务器响应异常", false);
                        e.printStackTrace();
                    }
                }
                else {
                    s = "灯关闭";
                    try {
                        Message msg = new Message();//实例化一个message对象
                        msg.what = 0x2;//发
                        msg.obj = "lightCLOSE";//灯关闭
                        clientThread.revHandler.sendMessage(msg);
                    } catch (Exception e) {
                        DialogUtil.showDialog(MainActivity.this, "服务器响应异常", false);
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
            }
        });
      /* button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    Message msg = new Message();//实例化一个message对象
                    msg.what = 0x2;//发
                    msg.obj = "1";
                    clientThread.revHandler.sendMessage(msg);
                } catch (Exception e) {
                    DialogUtil.showDialog(MainActivity.this, "服务器响应异常", false);
                    e.printStackTrace();

                }
            }
        });*/
    }
}
