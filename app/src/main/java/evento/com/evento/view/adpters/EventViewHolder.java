package evento.com.evento.view.adpters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import evento.com.evento.R;
import evento.com.evento.model.beans.Event;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;

/**
 * Created by amr masoud on 6/26/2016.
 */
public class EventViewHolder extends RecyclerView.ViewHolder  {

    RelativeLayout IMG_event;
    public TextView event_name;
    public TextView event_tag1;
    public TextView event_tag2;

    public EventViewHolder(View itemView) {
        super(itemView);
        IMG_event = (RelativeLayout) itemView.findViewById(R.id.event_item_background);
        event_name = (TextView) itemView.findViewById(R.id.TVevent_name);
        event_tag1 = (TextView) itemView.findViewById(R.id.TVtag1);
        event_tag2 = (TextView) itemView.findViewById(R.id.TVtag2);
    }

    public void bindToEvent(Event event) {
        event_name.setText(event.getName());
        if(event.getCategories().size()>0) event_tag1.setText(event.getCategories().get(0).getName());
        if(event.getCategories().size()>1) event_tag2.setText(event.getCategories().get(1).getName());
        Commons.firebaseStorageReference.child(Constants.IMAGES+"/"+event.getId())
                .getBytes(Long.MAX_VALUE).
                addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Use the bytes to display the image
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Drawable dr = new BitmapDrawable(bmp);
                        IMG_event.setBackgroundDrawable(dr);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

}
