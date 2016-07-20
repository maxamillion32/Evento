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
import evento.com.evento.model.beans.Comment;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;

/**
 * Created by amr masoud on 6/27/2016.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {
    public TextView authorView;
    public TextView bodyView;
    public ImageView img;

    public CommentViewHolder(View itemView) {
        super(itemView);
        authorView = (TextView) itemView.findViewById(R.id.comment_author);
        bodyView = (TextView) itemView.findViewById(R.id.comment_body);
        img = (ImageView) itemView.findViewById(R.id.comment_photo);
    }

    public void bindToComment(Comment comment) {
        Commons.firebaseStorageReference.child(Constants.IMAGES+"/"+comment.getCreatorId())
                .getBytes(Long.MAX_VALUE).
                addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Use the bytes to display the image
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        img.setImageBitmap(bmp);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        authorView.setText(comment.getCreatorName());
        bodyView.setText(comment.getContent());
    }
}
