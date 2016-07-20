package evento.com.evento.view.adpters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import evento.com.evento.R;
import evento.com.evento.model.beans.Notification;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;

/**
 * Created by amr masoud on 6/27/2016.
 */
public class NotificationViewHolder extends RecyclerView.ViewHolder {

    public ImageView IMGowner;
    public TextView nameCreator;
    public TextView notificationContent;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        IMGowner= (ImageView) itemView.findViewById(R.id.IMGnotification);
        nameCreator= (TextView) itemView.findViewById(R.id.TVnotifictionName);
        notificationContent= (TextView) itemView.findViewById(R.id.TVnotifictionContent);
    }
    public void binedToNotification(Notification notification){
        Commons.firebaseStorageReference.child(Constants.IMAGES+"/"+notification.getSenderImageUrl())
                .getBytes(Long.MAX_VALUE).
                addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Use the bytes to display the image
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        IMGowner.setImageBitmap(bmp);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        nameCreator.setText(notification.getSenderName());
        notificationContent.setText(notification.getContent());
    }
}
