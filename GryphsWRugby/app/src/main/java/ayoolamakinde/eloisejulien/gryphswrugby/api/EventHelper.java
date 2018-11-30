package ayoolamakinde.eloisejulien.gryphswrugby.api;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import ayoolamakinde.eloisejulien.gryphswrugby.models.Event;

public class EventHelper {

    private static final String COLLECTION_NAME = "events";


    public static CollectionReference getEventCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<DocumentReference> createEvent(String location, String date, String name, String startHour, String endHour) {
        Event eventToCreate = new Event(location, date, name, startHour, endHour);

        return ChatHelper.getChatCollection()
                .document("teamChat")
                .collection(COLLECTION_NAME)
                .add(eventToCreate);

    }

    public static Query getEventDate(String date){
        return ChatHelper.getChatCollection()
                .document("teamChat")
                .collection(COLLECTION_NAME)
                .whereEqualTo("date", date);
    }

    public static Task<Void> updateEvent(String value, String uid, String field) {
        return ChatHelper.getChatCollection()
                .document("teamChat")
                .collection(COLLECTION_NAME)
                .document(uid).update(field, value);
    }

    public static Task<Void> deleteEvent(String uid) {
        return ChatHelper.getChatCollection()
                .document("teamChat")
                .collection(COLLECTION_NAME).document(uid).delete();
    }

}
