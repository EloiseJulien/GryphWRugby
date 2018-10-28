package ayoolamakinde.eloisejulien.gryphswrugby;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

import ayoolamakinde.eloisejulien.gryphswrugby.api.MessageHelper;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeCoachActivity extends AppCompatActivity {

    private String TAG = "Home";
    private FirebaseAuth mAuth;
    private EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_coach);
        mAuth = FirebaseAuth.getInstance();
        editEmail = findViewById(R.id.edit_email);
        Button button1 = (Button) findViewById(R.id.coach_chat);
        Button button2 = (Button) findViewById(R.id.send_email);

        button1.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goToChat();
            }
        });

        button2.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    public void goToChat(){
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);
    }

    public void sendEmail() {
        ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://www.gryphswrugby.com/")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setIOSBundleId("ayoolamakinde.eloisejulien.gryphswrugby.ios")
                        .setAndroidPackageName(
                                "ayoolamakinde.eloisejulien.gryphswrugby.android",
                                true, /* installIfNotAvailable */
                                "12"    /* minimumVersion */)
                        .build();
        if (!TextUtils.isEmpty(editEmail.getText())) {
            String email = editEmail.getText().toString();
            mAuth.sendSignInLinkToEmail(email, actionCodeSettings)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                            }
                        }
                    });
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
