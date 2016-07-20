package evento.com.evento.view.adpters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import evento.com.evento.R;
import evento.com.evento.view.items.EventItem;

/**
 * Created by amr masoud on 6/17/2016.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ContactViewHolder> {

    private List<EventItem> contactList;

    public EventAdapter(List<EventItem> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        EventItem ci = contactList.get(i);
        //contactViewHolder.IMG_event.setBackgroundResource(ci.IMGbackground);

        contactViewHolder.event_name.setText(ci.eventName);
        contactViewHolder.event_owner.setText(ci.eventOwner);
        contactViewHolder.event_tag1.setText(ci.eventTags.get(0));
        contactViewHolder.event_tag2.setText(ci.eventTags.get(1));
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_event, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout IMG_event;
        TextView event_name;
        TextView event_owner;
        TextView event_tag1;
        TextView event_tag2;

        public ContactViewHolder(View itemView) {
            super(itemView);
            //IMG_event = (RelativeLayout) itemView.findViewById(R.id.event_item_background);
            event_name = (TextView) itemView.findViewById(R.id.TVevent_name);
            //event_owner = (TextView) itemView.findViewById(R.id.TVowner);
            event_tag1 = (TextView) itemView.findViewById(R.id.TVtag1);
            event_tag2 = (TextView) itemView.findViewById(R.id.TVtag2);
        }
    }
}