package evento.com.evento.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.Query;

import evento.com.evento.model.beans.CurrentActiveUser;
import evento.com.evento.model.beans.Event;
import evento.com.evento.model.beans.Notification;
import evento.com.evento.model.beans.User;
import evento.com.evento.model.beans.UserTimeTableEvent;
import evento.com.evento.model.dao.EventDao;
import evento.com.evento.model.dao.NotificationDao;
import evento.com.evento.model.dao.UserDao;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;

/**
 * Created by Mohamed on 6/19/2016.
 */
public class UserController {

    public static void signUp(final Context context , final Class newActivityClass) {
        try {
            Commons.showProgressDialog(context, "signing up");
            Commons.firebaseAuth.createUserWithEmailAndPassword(Commons.currentActiveUser.getEmail(),
                    Commons.currentActiveUser.getPassword()).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(Constants.TAG, "signUpWithEmailAndPassword:onComplete:" + task.isSuccessful());
                            Commons.hideProgressDialog();
                            if (task.isSuccessful()) {
                                Commons.currentActiveUser.setId(Commons.firebaseAuth.getCurrentUser().getUid());
                                UserDao.insertOrUpdateUserInfo(context ,newActivityClass);

                            } else {
                                Commons.currentActiveUser.freeUser();
                                Toast.makeText(context, "problem occured while registeration", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e(Constants.TAG, "error : signUpWithEmailAndPassword" + e);
        }
    }

    public static void login(final Context context, final Class newActivityClass) {
        Commons.showProgressDialog(context, "logging in");
        try {
            Commons.firebaseAuth.signInWithEmailAndPassword(Commons.currentActiveUser.getEmail(),
                    Commons.currentActiveUser.getPassword()).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(Constants.TAG, "loginWithEmailAndPassword:onComplete:" + task.isSuccessful());
                            Commons.hideProgressDialog();
                            if (task.isSuccessful()) {
                                Commons.currentActiveUser.setId(Commons.firebaseAuth.getCurrentUser().getUid());
                                UserDao.retrieveCurrentUserInfoByID(Commons.currentActiveUser.getId()
                                        , context, newActivityClass);
                            } else {
                                Toast.makeText(context, "email or password not correct", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e(Constants.TAG, "error : signinWithEmailAndPassword" + e);
        }
        Commons.hideProgressDialog();
    }

    public static void userIsLoggedIn(final Context context , final Class newActivityClass){
        Commons.currentActiveUser.setId(Commons.firebaseAuth.getCurrentUser().getUid());
        UserDao.retrieveCurrentUserInfoByID(Commons.currentActiveUser.getId(), context, newActivityClass);
    }

    public static void resetPasswordBySendEmail(String email, final Context context){
        Commons.showProgressDialog(context, "sending email");
        Commons.firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Commons.hideProgressDialog();
                if (task.isSuccessful()) {
                    Log.d(Constants.TAG, "Email sent.");
                    Toast.makeText(context, "email sent", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(context, "email is not correct", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void logOut(Context context, Class newActivityClass) {
        try {
            Commons.firebaseAuth.signOut();
            CurrentActiveUser.freeUser();
            Commons.changeActivity(context, newActivityClass);
        } catch (Exception e) {
            Log.e(Constants.TAG, "error : freeUser" + e);
        }
    }

    public static Query loadTimeline(){
        return EventDao.retrieveAllEvents();
    }

    public static Query getCreatedEvents(){
        return UserDao.retrieveCreatedEvents();
    }

    public static boolean isJointEvent(Event event){
        return Commons.isUserJointTheEvent();
    }

    public static void getUserToShowProfile(String id, Context context, Class newActivityClass){
        UserDao.retrieveUserInfoByID(id, context, newActivityClass);
    }

    public static Query getJointEvents(){
        return UserDao.retrieveJointEvents();
    }

    public static void follow(User user){
        UserDao.insertInFollower(user);
        UserDao.insertInFollowing(user);
        Notification notification = new Notification();
        notification.setContent(user.getName()+" has followed you");
        notification.setReceiverId(user.getId());
        notification.setSenderImageUrl(Commons.currentActiveUser.getImageUrl());
        notification.setSenderName(Commons.currentActiveUser.getName());
        notification.setUserIdOrEventId(Commons.currentActiveUser.getId());
        NotificationDao.insertNotification(notification);
    }

    public static void unfollow(String userId){
        UserDao.deleteFromFollower(userId);
        UserDao.deleteFromFollowing(userId);
    }

    public static void joinEvent(Event event){
        EventDao.insertEvent_JoinUser(event);
        UserTimeTableEvent userTimeTableEvent = event.exportUserTimeTableEvent();
        userTimeTableEvent.setUserID(Commons.currentActiveUser.getId());
        UserDao.insertTimeTableEvent(userTimeTableEvent);
    }

    public static void disJoinEvent(Event event){
        EventDao.deleteEvent_JoinUser(event);
        UserTimeTableEvent userTimeTableEvent = event.exportUserTimeTableEvent();
        userTimeTableEvent.setUserID(Commons.currentActiveUser.getId());
        UserDao.deleteTimeTableEvent(userTimeTableEvent);
    }

    public static Query showFollowing(){
        return UserDao.retrieveUserFollowing();
    }

    public static Query showFollowers(){
        return UserDao.retrieveUserFollowers();
    }

    public static Query searchForUser(String userName){
        return UserDao.searchForUser(userName);
    }

    public static void viewUserProfile(String userId, Context context, Class newClass){
        UserDao.retrieveCurrentUserInfoByID(userId, context, newClass);
    }

    public static Query getTimeTableEvents(){
        return UserDao.retrieveUserTimeTableEvents();
    }
}
