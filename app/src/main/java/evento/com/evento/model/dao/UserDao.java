package evento.com.evento.model.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import evento.com.evento.interfaces.CallBack;
import evento.com.evento.interfaces.UploadCallBack;
import evento.com.evento.model.beans.Category;
import evento.com.evento.model.beans.CurrentActiveUser;
import evento.com.evento.model.beans.User;
import evento.com.evento.model.beans.UserTimeTableEvent;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;
import evento.com.evento.utils.ImageStorage;

/**
 * Created by Mohamed on 6/14/2016.
 */
public class UserDao {


    public static ArrayList<UserTimeTableEvent> retrieveUserTimeTable_withCallBack(final CallBack callBack){
        Query queryRef = Commons.firebaseDatabaseReference.child(Constants.USER_TIMETABLE_EVENTS_CHILD)
                .child(Commons.currentActiveUser.getId()).orderByChild(Constants.DATE_CHILD);
        final ArrayList<UserTimeTableEvent>events=new ArrayList<>();
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    UserTimeTableEvent userTimeTableEvent = postSnapshot.getValue(UserTimeTableEvent.class);
                    Log.i("almgwary b4" , String.valueOf(events.size()));
                    events.add(userTimeTableEvent);
                    Log.i("almgwary aftr" , String.valueOf(events.size()));
                }
                callBack.setData(events);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.i("almgwary aftr 2 " , String.valueOf(events.size()));
        return events;
    }



    public static void insertOrUpdateUserInfo(final Context context, final Class newActivityClass){

        ImageStorage.uploadUserImageFromUri(Commons.currentActiveUser.getImageUrl(), new UploadCallBack() {
            @Override
            public void successCallBack() {
                Commons.showProgressDialog(context, "Signing up");
                Commons.firebaseDatabaseReference.child(Constants.USER_CHILD).child(Commons.currentActiveUser.getId()).
                        setValue(Commons.currentActiveUser);

                for(Category interest : Commons.currentActiveUser.getInterests()){
                    Commons.firebaseDatabaseReference.child(Constants.USER_CHILD).child(Commons.currentActiveUser.getId()).
                            child(Constants.INTEREST_CHILD).push().setValue(interest);
                }
                Commons.hideProgressDialog();
                Commons.changeActivity(context, newActivityClass);
            }




            @Override
            public void failuireCallBack() {
                Toast.makeText(context, "Problem Occured while upload the image",Toast.LENGTH_SHORT).show();
                Commons.showProgressDialog(context, "Signing up");
                Commons.firebaseDatabaseReference.child(Constants.USER_CHILD).child(Commons.currentActiveUser.getId()).
                        setValue(Commons.currentActiveUser);

                for(Category interest : Commons.currentActiveUser.getInterests()){
                    Commons.firebaseDatabaseReference.child(Constants.USER_CHILD).child(Commons.currentActiveUser.getId()).
                            child(Constants.INTEREST_CHILD).push().setValue(interest);
                }
                Commons.hideProgressDialog();
                Commons.changeActivity(context, newActivityClass);
            }
        }, context);
    }

    public static void retrieveUserInfoByID(String id, final Context context , final Class newActivityClass){
        Query queryRef = Commons.firebaseDatabaseReference.child(Constants.USER_CHILD)
                .orderByChild(Constants.ID_CHILD)
                .equalTo(id);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(Constants.TAG, "retrieveUserInfoByID");
                Commons.userToShowProfile = (dataSnapshot.getValue(CurrentActiveUser.class));
                checkIfCurrentActiveUserFollowUser(Commons.userToShowProfile);
                retrieveUserInterests(context, newActivityClass);
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

    public static void retrieveCurrentUserInfoByID(String id, final Context context , final Class newActivityClass){
        Query queryRef = Commons.firebaseDatabaseReference.child(Constants.USER_CHILD)
                .orderByChild(Constants.ID_CHILD)
                .equalTo(id);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(Constants.TAG, "retrieveCurrentUserInfoByID");
                Commons.currentActiveUser = (dataSnapshot.getValue(CurrentActiveUser.class));
                retrieveUserInterests(context, newActivityClass);
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

    public static void retrieveUserInterests(final Context context, final Class newActivityClass){
        Query queryRef = Commons.firebaseDatabaseReference.child(Constants.USER_CHILD).
                child(Commons.currentActiveUser.getId()).child(Constants.INTEREST_CHILD);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(Constants.TAG, "retrieveUserInterests");
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Category interest = new Category();
                    interest.setName(snapshot.getValue().toString());
                    Commons.currentActiveUser.addInterest(interest);

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

    public static void insertTimeTableEvent(UserTimeTableEvent userTimeTableEvent){
        Commons.firebaseDatabaseReference.child(Constants.USER_TIMETABLE_EVENTS_CHILD).child(Commons.currentActiveUser.getId()).
                child(userTimeTableEvent.getEventId()).setValue(userTimeTableEvent);
    }

    public static void deleteTimeTableEvent(UserTimeTableEvent userTimeTableEvent){
        Commons.firebaseDatabaseReference.child(Constants.USER_TIMETABLE_EVENTS_CHILD).child(Commons.currentActiveUser.getId()).
                child(userTimeTableEvent.getEventId()).removeValue();
    }

    public static void checkIfCurrentActiveUserFollowUser(User user){
        Query queryRef = Commons.firebaseDatabaseReference.child(Constants.FOLLOWING_CHILD).
                child(Commons.currentActiveUser.getId()).
                child(user.getId());
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getValue()==null||dataSnapshot.getValue()==""){
                    Commons.setIsFollowUser(false);
                }
                else Commons.setIsFollowUser(true);
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

    public static Query retrieveJointEvents(){
        Query queryRef = Commons.firebaseDatabaseReference.child(Constants.USER_JOIN_EVENTS_CHILD)
                .child(Commons.currentActiveUser.getId());
        return queryRef;
    }

    public static Query retrieveUserTimeTableEvents(){
        Query timeTableEventsQuery = Commons.firebaseDatabaseReference.child(Constants.USER_TIMETABLE_EVENTS_CHILD)
                .child(Commons.currentActiveUser.getId()).orderByChild("startDate");
        return timeTableEventsQuery;
    }

    public static Query retrieveCreatedEvents(){
        Query timeTableEventsQuery = Commons.firebaseDatabaseReference.child(Constants.CREATOR_EVENTS)
                .child(Commons.currentActiveUser.getId());
        return timeTableEventsQuery;
    }

    public static void insertInFollowing(User user){
        Commons.firebaseDatabaseReference.child(Constants.FOLLOWING_CHILD).child(Commons.currentActiveUser.getId()).
                child(user.getId()).setValue(user.exportMiniUser());
    }

    public static void insertInFollower(User user){
        Commons.firebaseDatabaseReference.child(Constants.FOLLOWERS_CHILD).child(user.getId()).
            child(Commons.currentActiveUser.getId()).setValue(Commons.currentActiveUser.exportMiniUser());
    }

    public static void deleteFromFollowing(String userId){
        Commons.firebaseDatabaseReference.child(Constants.FOLLOWING_CHILD).child(Commons.currentActiveUser.getId()).
                child(userId).removeValue();
    }

    public static void deleteFromFollower(String userId){
        Commons.firebaseDatabaseReference.child(Constants.FOLLOWERS_CHILD).child(userId).
                child(Commons.currentActiveUser.getId()).removeValue();
    }

    public static Query retrieveUserFollowing(){
        Query followingQuery = Commons.firebaseDatabaseReference.child(Constants.FOLLOWING_CHILD)
                .child(Commons.currentActiveUser.getId());
        return followingQuery;
    }

    public static Query retrieveUserFollowers(){
        Query followersQuery = Commons.firebaseDatabaseReference.child(Constants.FOLLOWERS_CHILD)
                .child(Commons.currentActiveUser.getId());
        return followersQuery;
    }

    public static Query searchForUser(String name){
        Query queryRef = Commons.firebaseDatabaseReference.child(Constants.USER_CHILD)
                .orderByChild(Constants.NAME_CHILD)
                .equalTo(name);
        return queryRef;
    }

}