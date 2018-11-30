package ayoolamakinde.eloisejulien.gryphswrugby;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import java.util.Calendar;
import java.util.Date;

public class Notification {
    private Context context1;

    int a = 0;

    Date currentTime = Calendar.getInstance().getTime();
    //Construct that sets the context
    public Notification(Context c) {
      this.context1= c;
    }




    public void addNotification() {
        a++;
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this.context1)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("GryphsWRugby Chat(s) at "+currentTime.toString())
                        .setContentText("New Message Added");



        Intent notificationIntent = new Intent(this.context1, Chat.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context1, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context1.getSystemService(this.context1.NOTIFICATION_SERVICE);
        manager.notify(a, builder.build());
    }








}
