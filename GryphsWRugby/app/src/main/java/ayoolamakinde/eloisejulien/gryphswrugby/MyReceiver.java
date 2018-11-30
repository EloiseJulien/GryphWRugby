package ayoolamakinde.eloisejulien.gryphswrugby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver{
    private Context context1;



    @Override
    public void onReceive(Context context, Intent intent) {
       // Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
       //Activity a = (Activity) context;
      //  context=context1.getApplicationContext();
        context1 =context;
        Notification aa = new Notification(context1);
        aa.addNotification();
        //Activity activity = (Activity) context1;
       // addNotification();
        //a.addNotification();



        //Intent myIntent = new Intent(context, activity.getClass());
       // context.startActivity(myIntent);

    }










}
