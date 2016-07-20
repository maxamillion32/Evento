package evento.com.evento.view.adpters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import evento.com.evento.R;
import evento.com.evento.model.beans.UserTimeTableEvent;

/**
 * Created by amr masoud on 6/28/2016.
 */
public class TimetableViewHolder extends RecyclerView.ViewHolder {
    TextView eventName;
    TextView eventDate;

    public TimetableViewHolder(View itemView) {
        super(itemView);
        eventName= (TextView) itemView.findViewById(R.id.TVeventNameTime);
        eventDate= (TextView) itemView.findViewById(R.id.TVeventDateTime);
    }

    public void bindToUserTimetable(UserTimeTableEvent user){
        eventName.setText(user.getEventName());
        eventDate.setText(user.getDate());
    }
}
