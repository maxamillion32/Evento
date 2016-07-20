package evento.com.evento.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import evento.com.evento.R;
import evento.com.evento.view.activities.ShowEventActivity;
import evento.com.evento.view.adpters.EventViewHolder;
import evento.com.evento.controllers.EventController;
import evento.com.evento.controllers.UserController;
import evento.com.evento.model.beans.Event;
import evento.com.evento.view.items.EventItem;


public class HomeFragment extends Fragment {

    private static final String LOG_TAG = HomeFragment.class.getSimpleName();
    private static final int EVENT_LOADER = 0;

    private ArrayList<EventItem> mlist;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Event , EventViewHolder> mAdapter;
    private LinearLayoutManager mLayoutManager;



    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.LVhome);
        mRecyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        /*mAdapter = new EventAdapter(getEvents(100));
        mRecyclerView.setAdapter(mAdapter);*/

        Query eventQuery = UserController.loadTimeline();

        mAdapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(Event.class, R.layout.item_event,
                EventViewHolder.class, eventQuery) {
            @Override
            protected void populateViewHolder(EventViewHolder viewHolder, final Event model, int position) {

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventController.getEvent(model.getId(), getContext(), ShowEventActivity.class);

                    }
                });

                viewHolder.bindToEvent(model);

            }
        };

        mRecyclerView.setAdapter(mAdapter);

    }

    ArrayList<EventItem> getEvents(int size){

        ArrayList<EventItem> list = new ArrayList<EventItem>();

        for (int i=1; i<size; i++){
            EventItem item = new EventItem() ;
            //item.IMGbackground = R.drawable.amr;
            item.eventName=" Event "+i;
            item.eventOwner= " Owner "+i;
            item.eventTags.add(0,"tag1");
            item.eventTags.add(1,"tag2");
            list.add(item);
            //mlist.get(i).setImgPath(R.drawable.amr);
        }
        return list;
    }

}
