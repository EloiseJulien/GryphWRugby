package ayoolamakinde.eloisejulien.gryphswrugby;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ayoolamakinde.eloisejulien.gryphswrugby.api.UserHelper;
import butterknife.BindView;
import butterknife.OnClick;

public class NewLoginActivity extends BaseActivity {

    // 1 - Getting all views needed
    @BindView(R.id.new_login_edit_email) EditText mEmailField;
    @BindView(R.id.new_login_edit_password) EditText mPasswordField;
    @BindView(R.id.new_login_confirm_password) EditText mConfirmField;
    @BindView(R.id.new_login_edit_fullname) EditText mFullnameField;

    private FirebaseAuth mAuth;
    private String TAG = "New Login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public int getFragmentLayout() { return R.layout.activity_new_login; }

    public void signUp(){
        if (!validateForm()) {
            return;
        }
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        final String fullname = mFullnameField.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserHelper.createUser(user.getUid(), fullname).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "error firebase", Toast.LENGTH_LONG).show();
                                }
                            });
                            connexion();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(NewLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailField.setError("Required.");
            Toast.makeText(NewLoginActivity.this, "Invalid email address ",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String fullname = mFullnameField.getText().toString();
        if (TextUtils.isEmpty(fullname)) {
            mFullnameField.setError("Required.");
            Toast.makeText(NewLoginActivity.this, "Full Name Required",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            mFullnameField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password) || password.length() < 8) {
            mPasswordField.setError("Required.");
            Toast.makeText(NewLoginActivity.this, "Password too short (at least 8 characters)",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        if (!mPasswordField.getText().toString().equals(mConfirmField.getText().toString())) {
            Toast.makeText(NewLoginActivity.this, "Confirm password different",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    @OnClick(R.id.new_login_connexion)
    public void onCLickNewConnexion() {
        signUp();
    }

    public void connexion(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
