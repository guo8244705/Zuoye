package guoyifeng.com.example.netplwiz.zuoye;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        String abc = intent.getStringExtra("abc");

        Toast.makeText(context, intent.toString(), Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(context, Main2Activity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
