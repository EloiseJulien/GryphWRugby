package ayoolamakinde.eloisejulien.gryphswrugby;

<<<<<<< HEAD
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
=======
>>>>>>> aaced57d3e58fd5a34c09e3f2134b9a5ca161271
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
<<<<<<< HEAD
import android.support.v4.app.NotificationCompat;
=======
>>>>>>> aaced57d3e58fd5a34c09e3f2134b9a5ca161271
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import ayoolamakinde.eloisejulien.gryphswrugby.api.EventHelper;
import ayoolamakinde.eloisejulien.gryphswrugby.models.Event;
import ayoolamakinde.eloisejulien.gryphswrugby.models.User;

public class AddEventActivity extends BaseActivity {

    private FirebaseAuth mAuth;

    private BottomNavigationView bottomNavigationView;
    private EditText editName;
    private EditText editLocation;
    private EditText editDate;
    private EditText editStartTime;
    private EditText editEndTime;

    private String AM_PM_START;
    private String AM_PM_END;

    private String eventID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        editName = findViewById(R.id.event_name);
        editLocation = findViewById(R.id.event_location);
        editDate = findViewById(R.id.event_date);
        editStartTime = findViewById(R.id.event_startTime);
        editEndTime = findViewById(R.id.event_endTime);

        bottomNavigationView = findViewById(R.id.add_event_navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_calendar:
                        Intent intent = new Intent(AddEventActivity.this, CalendarCoachActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_chat:
                        Intent intent1 = new Intent(AddEventActivity.this, Chat.class);
                        startActivity(intent1);

                    case R.id.navigation_home:
                        Intent intent2 = new Intent(AddEventActivity.this, HomeCoachActivity.class);
                        startActivity(intent2);

                }

                return true;
            }
        });
<<<<<<< HEAD


=======
>>>>>>> aaced57d3e58fd5a34c09e3f2134b9a5ca161271
    }

    @Override
    public int getFragmentLayout() { return R.layout.activity_add_event; }

    private boolean validateForm() {
        boolean valid = true;

        String name = editName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            editName.setError("Required.");
        } else {
            editName.setError(null);
        }

        String location = editLocation.getText().toString();
        if (TextUtils.isEmpty(location)) {
            editLocation.setError("Required.");
            valid = false;
        } else {
            editLocation.setError(null);
        }

        String date = editDate.getText().toString();
        if (TextUtils.isEmpty(date)) {
            editDate.setError("Required.");
            valid = false;
        } else {
            editDate.setError(null);
        }

        String endTime = editEndTime.getText().toString();
        if (TextUtils.isEmpty(endTime)) {
            editEndTime.setError("Required.");
            valid = false;
        } else {
            editEndTime.setError(null);
        }

        String startTime = editStartTime.getText().toString();
        if (TextUtils.isEmpty(startTime)) {
            editStartTime.setError("Required.");
            valid = false;
        } else {
            editStartTime.setError(null);
        }

        if (AM_PM_END == null){
            Toast.makeText(AddEventActivity.this, "Select am or pm",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (AM_PM_START == null){
            Toast.makeText(AddEventActivity.this, "Select am or pm",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }


        return valid;
    }

<<<<<<< HEAD


=======
>>>>>>> aaced57d3e58fd5a34c09e3f2134b9a5ca161271
    public void saveEvent(View view){
        if(!validateForm()){
            return;
        }
        String location = editLocation.getText().toString();
        String date = editDate.getText().toString();
        String name = editName.getText().toString();
        String start = editStartTime.getText().toString() + AM_PM_START;
        String end = editEndTime.getText().toString() + AM_PM_END;
        EventHelper.createEvent(location, date, name, start, end).addOnFailureListener(this.onFailureListener());
        Toast.makeText(AddEventActivity.this, "Event Add",
                Toast.LENGTH_SHORT).show();
        editLocation.setText("");
        editName.setText("");
        editDate.setText("");
        editStartTime.setText("");
        editEndTime.setText("");
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

    public void onRadioButton(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.am_starttime:
                if (checked){
                    AM_PM_START = "am";
                    break;
                }
            case R.id.pm_starttime:
                if (checked) {
                    AM_PM_START = "pm";
                    break;
                }
            case R.id.am_endtime:
                if (checked) {
                    AM_PM_END = "am";
                    break;
                }
            case R.id.pm_endtime:
                if (checked) {
                    AM_PM_END = "pm";
                    break;
                }
        }
    }

}
