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
import evento.com.evento.model.beans.Post;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;

/**
 * Created by amr masoud on 6/27/2016.
 */
public class PostViewHolder extends RecyclerView.ViewHolder {
    public ImageView IMGauthor;
    public TextView authorView;
    public TextView bodyView;
    public ImageView IMGdelete;


    public PostViewHolder(View itemView) {
        super(itemView);

        IMGauthor = (ImageView) itemView.findViewById(R.id.post_author_photo);
        authorView = (TextView) itemView.findViewById(R.id.post_author);
        bodyView = (TextView) itemView.findViewById(R.id.post_body2);
        IMGdelete= (ImageView) itemView.findViewById(R.id.IMGdelete);

    }

    public void bindToPost(final Post post ) {
        Commons.firebaseStorageReference.child(Constants.IMAGES+"/"+post.getCreatorId())
                .getBytes(Long.MIN_VALUE).
                addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Use the bytes to display the image
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        IMGdelete.setImageBitmap(bmp);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        IMGauthor.setImageResource(R.drawable.ic_action_account_circle_40);
        authorView.setText(post.getCreatorName());
        bodyView.setText(post.getContent());

    }
}
