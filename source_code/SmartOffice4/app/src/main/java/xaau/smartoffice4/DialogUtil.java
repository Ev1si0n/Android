package xaau.smartoffice4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

/**
 * Created by Mr.Li on 2017/5/25.
 */
public class DialogUtil {
    public static void showDialog(final Context ctx,String msg,boolean goHome)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(msg).setCancelable(false);
        if(goHome)
        {
            builder.setPositiveButton("确定",new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog,int which){
                    Intent i = new Intent(ctx,Login.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ctx.startActivity(i);
                }
            });
        }
        else{
            builder.setPositiveButton("确定",null);
        }
        builder.create().show();
    }
    public static void showDialog(Context ctx, View view)
    {
        new AlertDialog.Builder(ctx)
                .setView(view).setCancelable(false)
                .setPositiveButton("确定",null)
                .create()
                .show();
    }
}
