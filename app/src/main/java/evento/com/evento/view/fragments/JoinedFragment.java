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

import evento.com.evento.R;
import evento.com.evento.view.activities.ShowEventActivity;
import evento.com.evento.view.adpters.EventViewHolder;
import evento.com.evento.controllers.EventController;
import evento.com.evento.controllers.UserController;
import evento.com.evento.model.beans.Event;


public class JoinedFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Event , EventViewHolder> mAdapter;
    private LinearLayoutManager mLayoutManager;


    public JoinedFragment() {
        // Required empty public constructor
    }


    public static JoinedFragment newInstance() {
        JoinedFragment fragment = new JoinedFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_joined, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.LVjointEvents);
        mRecyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Query eventQuery = UserController.getJointEvents();

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

}
