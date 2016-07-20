package evento.com.evento.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

import evento.com.evento.model.beans.CurrentActiveUser;
import evento.com.evento.model.beans.Event;
import evento.com.evento.model.beans.Post;
import evento.com.evento.model.beans.User;

/**
 * Created by Mohamed on 6/20/2016.
 */
public class Commons {
    private static ProgressDialog progressDialog;
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static Event activeEvent;
    public static FirebaseAuth.AuthStateListener firebaseAuthListener;
    public static DatabaseReference firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    public static StorageReference firebaseStorageReference = FirebaseStorage.getInstance().getReference();
    public static CurrentActiveUser currentActiveUser = CurrentActiveUser.getUserInstance();
    public static Post activePost;
    public static User userToShowProfile;
    private static boolean isJointEvent=false;
    private static boolean isFollowUser=false;

    public static void setIsJointEvent(boolean bool){
        isJointEvent=bool;
    }

    public static boolean isUserJointTheEvent(){
        return isJointEvent;
    }

    public static void setIsFollowUser(boolean bool){
        isFollowUser=bool;
    }

    public static boolean isCurrentUserFollowThisUser(){
        return isFollowUser;
    }

    public static void startUserToShowProfile(){
        userToShowProfile=new User();
    }

    public static void finishUserToShowProfile(){
        userToShowProfile=null;
    }

    public static void startActiveEvent(){
        activeEvent=new Event();
    }

    public static void finishActiveEvent(){
        activeEvent=null;
    }

    public static void startActivePost(){
        activePost= new Post();
    }

    public static void finishActivePost(){
        activeEvent=null;
    }

    public static void showProgressDialog(Context context, String textToShow){
        progressDialog=new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(textToShow);
        progressDialog.show();
    }

    public static void hideProgressDialog(){
        progressDialog.dismiss();
    }

    public static void changeActivity(Context context, Class _class){
        Intent intent = new Intent(context, _class);
        context.startActivity(intent);
    }

    public static void changeActivityWithExtra(Context context, Class _class, Serializable serializable, String objectName){
        Intent intent = new Intent(context, _class);
        intent.putExtra(objectName, serializable);
        context.startActivity(intent);
    }
}
