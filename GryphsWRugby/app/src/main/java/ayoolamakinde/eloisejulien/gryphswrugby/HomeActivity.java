package ayoolamakinde.eloisejulien.gryphswrugby;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
<<<<<<< HEAD
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
=======
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
>>>>>>> aaced57d3e58fd5a34c09e3f2134b9a5ca161271

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private BottomNavigationView bottomNavigationView;
<<<<<<< HEAD
    private Context a;


    private static final String TAG = "HomeActivity";

=======
>>>>>>> aaced57d3e58fd5a34c09e3f2134b9a5ca161271

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        bottomNavigationView = findViewById(R.id.home_navigationView);

<<<<<<< HEAD
        a=this;

=======
>>>>>>> aaced57d3e58fd5a34c09e3f2134b9a5ca161271
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_calendar:
                        Intent intent = new Intent(HomeActivity.this, CalendarActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_chat:
                        goToChat();
                }

                return true;
            }
        });
    }

    public void goToChat(){

        Intent intent = new Intent(this, Chat.class);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                mAuth.signOut();
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
