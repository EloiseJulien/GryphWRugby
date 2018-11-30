package ayoolamakinde.eloisejulien.gryphswrugby;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import ayoolamakinde.eloisejulien.gryphswrugby.api.EventHelper;
import ayoolamakinde.eloisejulien.gryphswrugby.models.Event;

public class ModifEvent extends BaseActivity {

    private FirebaseAuth mAuth;

    private BottomNavigationView bottomNavigationView;
    private TextView editName;
    private TextView editLocation;
    private TextView editDate;
    private TextView editStartTime;
    private TextView editEndTime;

    private boolean locationModify = false;
    private boolean dateModify = false;
    private boolean startTimeModify = false;
    private boolean endTimeModify = false;
    private String AM_PM = null;

    private Event currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        editName = findViewById(R.id.modif_event_name);
        editLocation = findViewById(R.id.modif_event_location);
        editDate = findViewById(R.id.modif_event_date);
        editStartTime = findViewById(R.id.modif_event_startTime);
        editEndTime = findViewById(R.id.modif_event_endTime);

        bottomNavigationView = findViewById(R.id.modif_event_navigationView);

        currentEvent = (Event) getIntent().getSerializableExtra("Event");

        editName.setText(currentEvent.getName());
        editLocation.setText(currentEvent.getLocation());
        editDate.setText(currentEvent.getDate());
        editStartTime.setText(currentEvent.getStartHour());
        editEndTime.setText(currentEvent.getEndHour());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_calendar:
                        Intent intent = new Intent(ModifEvent.this, CalendarActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_chat:
                        Intent intent1 = new Intent(ModifEvent.this, Chat.class);
                        startActivity(intent1);

                    case R.id.navigation_home:
                        Intent intent2 = new Intent(ModifEvent.this, HomeCoachActivity.class);
                        startActivity(intent2);

                }

                return true;
            }
        });

    }

    @Override
    public int getFragmentLayout() { return R.layout.activity_modif_event; }

    public void deleteEvent(View view){
        EventHelper.deleteEvent(currentEvent.getId()).addOnFailureListener(this.onFailureListener());
        Toast.makeText(ModifEvent.this, "Event Delete",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ModifEvent.this, CalendarCoachActivity.class);
        startActivity(intent);

    }

    public void modifyLocation(View view){
        // create dialog
        final Dialog d = new Dialog(ModifEvent.this);

        // parameters dialog
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setContentView(R.layout.dialog_layout);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        TextView title = d.findViewById(R.id.dialog_title);
        final EditText input = d.findViewById(R.id.edit_dialog);
        title.setText("Set new Location");

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(input.getText().toString())) {
                    editLocation.setText(input.getText());
                    locationModify = true;
                }
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    public void modifyDate(View view){
        // create dialog
        final Dialog d = new Dialog(ModifEvent.this);

        // parameters dialog
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setContentView(R.layout.dialog_date);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        TextView title = d.findViewById(R.id.dialog_title);
        final EditText input = d.findViewById(R.id.edit_dialog);
        title.setText("Set new Date");

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(input.getText().toString())) {
                    editDate.setText(input.getText());
                    dateModify = true;
                }
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    public void modifyStartTime(View view){
        // create dialog
        final Dialog d = new Dialog(ModifEvent.this);

        // parameters dialog
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setContentView(R.layout.dialog_layout_time);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        TextView title = d.findViewById(R.id.dialog_title);
        final EditText input = d.findViewById(R.id.edit_dialog);
        title.setText("Set new Start Time");

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(input.getText().toString()) && AM_PM != null) {
                    editStartTime.setText(input.getText() + AM_PM);
                    AM_PM = null;
                    startTimeModify = true;
                }
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    public void modifyEndTime(View view){
        // create dialog
        final Dialog d = new Dialog(ModifEvent.this);

        // parameters dialog
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setContentView(R.layout.dialog_layout_time);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        TextView title = d.findViewById(R.id.dialog_title);
        final EditText input = d.findViewById(R.id.edit_dialog);
        title.setText("Set new End Time");

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(input.getText().toString()) && AM_PM != null) {
                    editEndTime.setText(input.getText() + AM_PM);
                    AM_PM = null;
                    endTimeModify = true;
                }
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    public void updateEvent(View view){
        if(locationModify){
            EventHelper.updateEvent(editLocation.getText().toString(), currentEvent.getId(),"location").addOnFailureListener(this.onFailureListener());
        }
        if(dateModify){
            EventHelper.updateEvent(editDate.getText().toString(), currentEvent.getId(),"date").addOnFailureListener(this.onFailureListener());
        }
        if(startTimeModify){
            EventHelper.updateEvent(editStartTime.getText().toString(), currentEvent.getId(),"startHour").addOnFailureListener(this.onFailureListener());
        }
        if(endTimeModify){
            EventHelper.updateEvent(editEndTime.getText().toString(), currentEvent.getId(),"endHour").addOnFailureListener(this.onFailureListener());
        }

        Toast.makeText(ModifEvent.this, "Event Update",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ModifEvent.this, CalendarCoachActivity.class);
        startActivity(intent);

    }

    public void onRadioButtonDialog(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.am:
                if (checked){
                    AM_PM = "am";
                    break;
                }
            case R.id.pm:
                if (checked) {
                    AM_PM = "pm";
                    break;
                }
        }
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
