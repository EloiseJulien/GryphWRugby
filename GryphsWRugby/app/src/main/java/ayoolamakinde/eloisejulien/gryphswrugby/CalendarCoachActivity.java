package ayoolamakinde.eloisejulien.gryphswrugby;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ayoolamakinde.eloisejulien.gryphswrugby.api.EventHelper;
import ayoolamakinde.eloisejulien.gryphswrugby.api.UserHelper;
import ayoolamakinde.eloisejulien.gryphswrugby.models.Event;
import ayoolamakinde.eloisejulien.gryphswrugby.models.User;

public class CalendarCoachActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private EventAdapter adapter;
    private List<Event> events;

    private TextView viewDate;
    private BottomNavigationView bottomNavigationView;
    private TextView nothing;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_calendar);

        calendar = Calendar.getInstance();

        viewDate = findViewById(R.id.date);
        bottomNavigationView = findViewById(R.id.calendar_navigationView);
        nothing = findViewById(R.id.nothing);
        mListView = findViewById(R.id.list_event);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(calendar.getTime());
        viewDate.setText(date);

        loadEvent();


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id){
                Event eventIntent = events.get(pos);
                Intent intent = new Intent(CalendarCoachActivity.this, ModifEvent.class);
                intent.putExtra("Event", eventIntent);
                startActivity(intent);
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_chat:
                        Intent intent = new Intent(CalendarCoachActivity.this, Chat.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_home:
                        Intent intent2 = new Intent(CalendarCoachActivity.this, HomeCoachActivity.class);
                        startActivity(intent2);
                                
                }

                return true;
            }
        });

    }
    

    public void OnClickLeft(View view){
        calendar.add(Calendar.DATE, -1);
        viewDate.setText(dateFormat.format(calendar.getTime()));
        loadEvent();
    }

    public void OnClickRight(View view){
        calendar.add(Calendar.DATE, 1);
        viewDate.setText(dateFormat.format(calendar.getTime()));
        loadEvent();
    }

    private void loadEvent(){
        events = new ArrayList<>();
        Query query = EventHelper.getEventDate(dateFormat.format(calendar.getTime()));

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e)
            {
                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments())
                {
                    Event event = doc.toObject(Event.class);
                    event.setId(doc.getId());
                    events.add(event);
                }
                //check historical empty
                if(events.size() <= 0){
                    nothing.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                } else {
                    nothing.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);

                    adapter = new EventAdapter(CalendarCoachActivity.this, events);
                    mListView.setAdapter(adapter);
                }
            }
        });
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
