package xaau.smartoffice4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Mr.Li on 2017/5/12.
 */

public class Settings extends Activity {

    public EditText ipEdit;
    public EditText portEdit;
    public Button confirm;
    public TextView test;
    public String defaultip="192.168.123.1";
    public int defaultport=5432;
    Handler handler;
    //ClientThread clientThread;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settings);
        ipEdit = (EditText) findViewById(R.id.ip);
        portEdit = (EditText) findViewById(R.id.port);
        Button confirm = (Button) findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String ip = ipEdit.getText().toString();
                defaultip = ip;
                final String port = portEdit.getText().toString();
                defaultport =  Integer.parseInt(port);
                if(ip.equals("")&&port.equals("")) {
                    Toast.makeText(getBaseContext(), "不能为空 ",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent setting_data1 = new Intent();
                    setting_data1.putExtra("ip", ip);
                    setting_data1.putExtra("port", port);
                    setResult(RESULT_OK, setting_data1);
                    finish();
                }

            }
        });
    }

}




