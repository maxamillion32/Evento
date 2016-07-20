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
import evento.com.evento.model.beans.UserRepresentation;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;

/**
 * Created by amr masoud on 6/27/2016.
 */
public class FollowViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView name;

    public FollowViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.IMGfollow);
        name = (TextView) itemView.findViewById(R.id.TVname_follow);

    }

    public void bindTOFollow(UserRepresentation user){
        Commons.firebaseStorageReference.child(Constants.IMAGES+"/"+user.getId())
                .getBytes(Long.MAX_VALUE).
                addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Use the bytes to display the image
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        image.setImageBitmap(bmp);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        name.setText(user.getName());
    }
}
