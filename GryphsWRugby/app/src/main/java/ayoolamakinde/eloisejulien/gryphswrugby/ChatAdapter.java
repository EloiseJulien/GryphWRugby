package ayoolamakinde.eloisejulien.gryphswrugby;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import ayoolamakinde.eloisejulien.gryphswrugby.models.ChatMessage;

public class ChatAdapter extends FirestoreRecyclerAdapter<ChatMessage, ChatViewHolder> {



    public interface Listener {
        void onDataChanged();
    }

    //FOR DATA
    private final RequestManager glide;
    private final String idCurrentUser;
    private Context a;


    //FOR COMMUNICATION
    private Listener callback;

    public ChatAdapter(@NonNull FirestoreRecyclerOptions<ChatMessage> options, RequestManager glide, Listener callback, String idCurrentUser) {
        super(options);
        this.glide = glide;
        this.callback = callback;
        this.idCurrentUser = idCurrentUser;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull ChatMessage model) {
        holder.updateWithMessage(model, this.idCurrentUser, this.glide);
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_chat_item, parent, false));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();

    }


}
