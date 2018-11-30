package ayoolamakinde.eloisejulien.gryphswrugby;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import ayoolamakinde.eloisejulien.gryphswrugby.api.UserHelper;
import ayoolamakinde.eloisejulien.gryphswrugby.models.User;
import butterknife.BindView;
import butterknife.OnClick;


public class Login extends BaseActivity{

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private static final String TAG = "EmailPassword";
    private User modelUser;

    // 1 - Getting all views needed
    @BindView(R.id.edit_email) EditText mEmailField;
    @BindView(R.id.edit_password) EditText mPasswordField;
    @BindView(R.id.connexion) Button buttonConnexion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public int getFragmentLayout() { return R.layout.activity_login; }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            firebaseUser = mAuth.getCurrentUser();
                            connection();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            firebaseUser = null;
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    public void connection(){
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);
       /*getCurrentUserFromFirestore();
        if(modelUser != null) {
            if (modelUser.getIsCoach()) {
                Intent intent = new Intent(this, HomeCoachActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }
        }*/
    }

    private void getCurrentUserFromFirestore(){
        UserHelper.getUser(mAuth.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                modelUser = documentSnapshot.toObject(User.class);
            }
        });
    }

    public void broadcastIntent(View view){
        Intent intent = new Intent();
        intent.setAction("com.tutorialspoint.CUSTOM_INTENT"); sendBroadcast(intent);
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }


    @OnClick(R.id.connexion)
    public void onClickButton() {
        signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
    }

}
