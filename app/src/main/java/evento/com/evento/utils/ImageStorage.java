package evento.com.evento.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import evento.com.evento.interfaces.UploadCallBack;
import evento.com.evento.model.beans.Event;

public class ImageStorage {
    private static Uri downloadUrl;

    public static void uploadEventCoverFromUri(String fileUri, final Event event  , UploadCallBack uploadCallBack, Context context){
        Uri myUri = Uri.parse(fileUri);
        uploadFromUri(myUri, event, uploadCallBack, context );
    }

    public static void uploadUserImageFromUri(String fileUri , UploadCallBack uploadCallBack, Context context){
        Uri myUri = Uri.parse(fileUri);
        uploadFromUri(myUri, null , uploadCallBack, context);
    }

    private static void uploadFromUri(Uri fileUri, final Event event  , final UploadCallBack uploadCallBack, Context context) {
        Commons.showProgressDialog(context, "Upload Image");
        downloadUrl=null;
        Log.d(Constants.TAG, "uploadFromUri:src:" + fileUri.toString());
        try {
            final StorageReference photoRef;
            if(event!=null) photoRef = Commons.firebaseStorageReference.child(Constants.IMAGES)
                    .child(event.getId());
            else photoRef = Commons.firebaseStorageReference.child(Constants.IMAGES)
                    .child(Commons.currentActiveUser.getId());
            Log.d(Constants.TAG, "uploadFromUri:dst:" + photoRef.getPath());
            photoRef.putFile(fileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.d(Constants.TAG, "uploadFromUri:onSuccess");
                            downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
                            if(event != null){
                                event.setImageUrl(event.getId());
                            }
                            else{
                                Commons.currentActiveUser.setImageUrl(Commons.currentActiveUser.getId());
                            }
                            uploadCallBack.successCallBack();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.w(Constants.TAG, "uploadFromUri:onFailure", exception);
                            downloadUrl = null;
                            uploadCallBack.failuireCallBack();
                        }
                    });
        }
        catch(Exception e){
            Log.e(Constants.TAG, "error : uploadFromUri" + e);
        }
    }
}