package evento.com.evento.model.dao;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import evento.com.evento.interfaces.UploadCallBack;
import evento.com.evento.model.beans.Category;
import evento.com.evento.model.beans.Event;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;
import evento.com.evento.utils.ImageStorage;

/**
 * Created by Mohamed on 6/17/2016.
 */
public class EventDao {

    public static void insertEventInfo(final Event event, final Context context, final Class newActivityClass){
        event.setId(Commons.firebaseDatabaseReference.child(Constants.EVENT_CHILD).push().getKey());
        Commons.firebaseDatabaseReference.child(Constants.EVENT_CHILD).child(event.getId()).setValue(event);
        ImageStorage.uploadEventCoverFromUri(event.getImageUrl(), event, new UploadCallBack() {
            @Override
            public void successCallBack() {
                Commons.showProgressDialog(context, "Signing up");
                for(Category category : event.getCategories()){
                    Commons.firebaseDatabaseReference.child(Constants.EVENT_CHILD).child(event.getId()).
                            child(Constants.CATEGORY_CHILD).push().setValue(category);
                }
                Commons.firebaseDatabaseReference.child(Constants.CREATOR_EVENTS)
                        .child(Commons.currentActiveUser.getId()).child(event.getId()).setValue(event);
                Commons.hideProgressDialog();
                Commons.changeActivity(context, newActivityClass);
            }

            @Override
            public void failuireCallBack() {

                Commons.showProgressDialog(context, "Signing up");
                for(Category category : event.getCategories()){
                    Commons.firebaseDatabaseReference.child(Constants.EVENT_CHILD).child(event.getId()).
                            child(Constants.CATEGORY_CHILD).push().setValue(category);
                }
                Commons.firebaseDatabaseReference.child(Constants.CREATOR_EVENTS)
                        .child(Commons.currentActiveUser.getId()).child(event.getId()).setValue(event);
                Commons.hideProgressDialog();
                Commons.changeActivity(context, newActivityClass);
            }
        }, context);

    }

    public static void deleteEventInfo(Event event){
        Commons.firebaseDatabaseReference.child(Constants.EVENT_CHILD).child(event.getId()).removeValue();
    }

    public static void updateEventInfo(Event event){
        Commons.firebaseDatabaseReference.child(Constants.EVENT_CHILD).child(event.getId()).setValue(event);
    }

    public static void retrieveEventInfo(String eventId, final Context context, final Class newActivityClass){
        Query queryRef = Commons.firebaseDatabaseReference.child(Constants.EVENT_CHILD)
                .orderByChild(Constants.ID_CHILD)
                .equalTo(eventId);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(Constants.TAG, "retrieveEventInfo");
                Commons.startActiveEvent();
                Commons.activeEvent = (dataSnapshot.getValue(Event.class));
                checkIfCurrentActiveUserJointEvent(Commons.activeEvent);
                retrieveEventInterests(context, newActivityClass);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void checkIfCurrentActiveUserJointEvent(Event event){
        Query queryRef = Commons.firebaseDatabaseReference.child(Constants.EVENT_JOIN_USER_CHILD).child(event.getId())
                .child(Commons.currentActiveUser.getId());
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getValue()==null||dataSnapshot.getValue()==""){
                    Commons.setIsJointEvent(false);
                }
                else Commons.setIsJointEvent(true);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void retrieveEventInterests(final Context context, final Class newActivityClass){

        Query queryRef = Commons.firebaseDatabaseReference.child(Constants.EVENT_CHILD).child(Commons.activeEvent.getId())
                .child(Constants.CATEGORY_CHILD);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(Constants.TAG, "retrieveEventInfo");
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Category category = new Category();
                    category.setName(snapshot.getValue().toString());
                    Commons.activeEvent.addCategory(category);
                }
                Commons.changeActivity(context, newActivityClass);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void insertEvent_JoinUser(Event event){
        Commons.firebaseDatabaseReference.child(Constants.EVENT_JOIN_USER_CHILD).child(event.getId()).
                child(Commons.currentActiveUser.getId()).setValue(Commons.currentActiveUser.exportMiniUser());

        Commons.firebaseDatabaseReference.child(Constants.USER_JOIN_EVENTS_CHILD).child(Commons.currentActiveUser.getId()).
                child(event.getId()).setValue(event);
    }

    public static void deleteEvent_JoinUser(Event event){
        Commons.firebaseDatabaseReference.child(Constants.EVENT_JOIN_USER_CHILD).child(event.getId()).
                child(Commons.currentActiveUser.getId()).removeValue();
    }

    public static Query retrieveEvent_JoinUsers(Event event){
        Query postsQuery = Commons.firebaseDatabaseReference.child(Constants.EVENT_JOIN_USER_CHILD)
                .child(event.getId());
        return postsQuery;
    }

    public static Query retrieveAllEvents(){
        Query eventsQuery = Commons.firebaseDatabaseReference.child(Constants.EVENT_CHILD);
        return eventsQuery;
    }

    public static Query searchForEvent(String name){
        Query queryRef = Commons.firebaseDatabaseReference.child(Constants.EVENT_CHILD)
                .orderByChild(Constants.NAME_CHILD)
                .equalTo(name);
        return queryRef;
    }
}
