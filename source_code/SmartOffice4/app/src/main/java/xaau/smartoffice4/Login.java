package xaau.smartoffice4;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Mr.Li on 2017/5/10.
 */

public class Login extends Activity {
    private EditText EdituserName;
    private EditText EditpassWord;
    private Button login;
    private ImageButton settings;
    public TextView textView;
    public String ip;
    public String port;
    Handler handler;

    Settings settings1 = new Settings();
    int defaultport = settings1.defaultport;
    String defaultip = settings1.defaultip;
    //ClientThread clientThread;//定义与服务通信的子线程
    @Override//Setting这个活动被销毁后回调回这个onActivityResult方法
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        final TextView textView = (TextView)findViewById(R.id.viewip);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    ip = data.getStringExtra("ip");
                    port = data.getStringExtra("port");
                    defaultip =  ip;
                    defaultport = Integer.parseInt(port);
                    textView.setText("ip:"+defaultip+"\n\rport:"+defaultport);
                }
                break;
            default:
                break;
        }
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // 去除标题this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        EdituserName = (EditText) this.findViewById(R.id.userName);
        EditpassWord = (EditText) this.findViewById(R.id.passWord);
        final Button login = (Button) findViewById(R.id.login);
        final TextView textView = (TextView)findViewById(R.id.viewip);
        ImageButton settings = (ImageButton) findViewById(R.id.settings);
        textView.setText("ip:"+defaultip+"\n\rport:"+defaultport);
        /*clientThread = new ClientThread(handler);
        new Thread(clientThread).start();*/




        settings.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
                /*Toast.makeText(getBaseContext(), "setting",
                        Toast.LENGTH_SHORT).show();*/
               /*Intent intent_settings = new Intent(Login.this, Settings.class);
                startActivityForResult(intent_settings,1);*/

           }
       });


        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String userName = EdituserName.getText().toString();
                String passWord = EditpassWord.getText().toString();
                /*try {
                        Message msg = new Message();//实例化一个message对象
                        msg.what = 0x1;
                        msg.obj = userName + passWord;
                        clientThread.revHandler.sendMessage(msg);
                } catch (Exception e) {
                        DialogUtil.showDialog(Login.this, "服务器响应异常", false);
                    e.printStackTrace();

                    }*/
                if(validate()){
                    if(Loginpro()){
                        Intent intent_login = new Intent(Login.this, MainActivity.class);
                        startActivity(intent_login);
                        finish();
                    }

                }
                /*Toast.makeText(getBaseContext(), "login",
                        Toast.LENGTH_SHORT).show();*/
                    //判断所填是否为空

            }
        });

    }


/*private boolean loginPro(){

    handler = new Handler();
    Message msg = new Message();
   //public boolean handleMessageFrom(Message msg){
            if(.equals("admin")&&.equals("passWord"))//代表来自主活动登陆失败
            {
                return true;
            }
            else{//登陆成功
                return false;
            }

        }

*/
private boolean Loginpro() {
    if (validate()) {
        //如果登陆成功
        String userName = EdituserName.getText().toString();
        String passWord = EditpassWord.getText().toString();
        if (userName.equals("admin") && passWord.equals("passWord")) {
            return true;
        }
        else {
                DialogUtil.showDialog(this, "用户名或密码错误，请重新输入！", false);
        }

    }
   return false;
}



    private  boolean validate() {
    String userName = EdituserName.getText().toString().trim();
    if (userName.equals("")) {
        DialogUtil.showDialog(this, "用户名是必填项!", false);
        return false;
    }
    String passWord = EditpassWord.getText().toString().trim();
    if (passWord.equals("")) {
        DialogUtil.showDialog(this, "密码是必填项!", false);
        return false;
    }

    return true;
}

}



