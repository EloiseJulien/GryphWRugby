package ayoolamakinde.eloisejulien.gryphswrugby;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.messaging.FirebaseMessaging;

import ayoolamakinde.eloisejulien.gryphswrugby.api.MessageHelper;
import ayoolamakinde.eloisejulien.gryphswrugby.api.UserHelper;
import ayoolamakinde.eloisejulien.gryphswrugby.models.ChatMessage;
import ayoolamakinde.eloisejulien.gryphswrugby.models.User;
import butterknife.BindView;
import butterknife.OnClick;

public class Chat extends BaseActivity implements ChatAdapter.Listener{

    private FirebaseAuth mAuth;


    // FOR DESIGN
    // 1 - Getting all views needed
    @BindView(R.id.activity_mentor_chat_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.activity_mentor_chat_text_view_recycler_view_empty) TextView textViewRecyclerViewEmpty;
    @BindView(R.id.activity_mentor_chat_message_edit_text) TextInputEditText editTextMessage;
    @BindView(R.id.activity_mentor_chat_image_chosen_preview) ImageView imageViewPreview;
    @BindView(R.id.chat_navigationView) BottomNavigationView bottomNavigationView;

    // FOR DATA
    // 2 - Declaring Adapter and data
    private ChatAdapter mentorChatAdapter;
    @Nullable
    private User modelCurrentUser;
    private String currentChatName;

    // STATIC DATA FOR CHAT
    private static final String CHAT_NAME_TEAM = "teamChat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        this.configureRecyclerView(CHAT_NAME_TEAM);
        getCurrentUserFromFirestore();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_calendar:
                        if(modelCurrentUser != null){
                            if(modelCurrentUser.getIsCoach()){
                                Intent intent2 = new Intent(Chat.this, CalendarCoachActivity.class);
                                startActivity(intent2);
                            } else {
                                Intent intent2 = new Intent(Chat.this, CalendarActivity.class);
                                startActivity(intent2);
                            }
                        }
                        return true;
                    case R.id.navigation_home:
                        if(modelCurrentUser != null){
                            if(modelCurrentUser.getIsCoach()){
                                Intent intent2 = new Intent(Chat.this, HomeCoachActivity.class);
                                startActivity(intent2);
                            } else {
                                Intent intent2 = new Intent(Chat.this, HomeActivity.class);
                                startActivity(intent2);
                            }
                        }
                }

                return true;
            }
        });

    }

    @Override
    public int getFragmentLayout() { return R.layout.activity_chat; }


    // --------------------
    // ACTIONS
    // --------------------

    @OnClick(R.id.activity_mentor_chat_send_button)
    public void onClickSendMessage() {
        // 1 - Check if text field is not empty and current user properly downloaded from Firestore
        if (!TextUtils.isEmpty(editTextMessage.getText()) && modelCurrentUser != null){
            // 2 - Create a new Message to Firestore
            MessageHelper.createMessageForChat(editTextMessage.getText().toString(), this.currentChatName, modelCurrentUser).addOnFailureListener(this.onFailureListener());
            // 3 - Reset text field
            this.editTextMessage.setText("");
        }
    }

    // --------------------
    // REST REQUESTS
    // --------------------
    // 4 - Get Current User from Firestore
    private void getCurrentUserFromFirestore(){
        UserHelper.getUser(mAuth.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                modelCurrentUser = documentSnapshot.toObject(User.class);
            }
        });
    }

    // --------------------
    // UI
    // --------------------
    // 5 - Configure RecyclerView with a Query
    private void configureRecyclerView(String chatName){
        //Track current chat name
        this.currentChatName = chatName;
        //Configure Adapter & RecyclerView
        this.mentorChatAdapter = new ChatAdapter(generateOptionsForAdapter(MessageHelper.getAllMessageForChat(this.currentChatName)), Glide.with(this), this, mAuth.getCurrentUser().getUid());
        mentorChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(mentorChatAdapter.getItemCount()); // Scroll to bottom on new messages
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.mentorChatAdapter);
    }

    // 6 - Create options for RecyclerView from a Query
    private FirestoreRecyclerOptions<ChatMessage> generateOptionsForAdapter(Query query){
        return new FirestoreRecyclerOptions.Builder<ChatMessage>()
                .setQuery(query, ChatMessage.class)
                .setLifecycleOwner(this)
                .build();
    }

    // --------------------
    // CALLBACK
    // --------------------

    @Override
    public void onDataChanged() {
        // 7 - Show TextView in case RecyclerView is empty
        broadcastIntent(null);
        textViewRecyclerViewEmpty.setVisibility(this.mentorChatAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);


    }

    public void broadcastIntent(View view){
        Intent intent = new Intent();
        intent.setAction("com.tutorialspoint.CUSTOM_INTENT"); sendBroadcast(intent);
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
