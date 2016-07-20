package evento.com.evento.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import evento.com.evento.R;
import evento.com.evento.view.adpters.TimetableViewHolder;
import evento.com.evento.controllers.EventController;
import evento.com.evento.controllers.UserController;
import evento.com.evento.model.beans.UserTimeTableEvent;

public class TimetableActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseRecyclerAdapter<UserTimeTableEvent, TimetableViewHolder> mTimetableAdapter;
    LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);


        mRecyclerView = (RecyclerView) findViewById(R.id.LVtimetable);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Query timetableQuery = UserController.getTimeTableEvents();

        mTimetableAdapter = new FirebaseRecyclerAdapter<UserTimeTableEvent, TimetableViewHolder>(UserTimeTableEvent.class, R.layout.item_timetable,
                TimetableViewHolder.class, timetableQuery) {
            @Override
            protected void populateViewHolder(TimetableViewHolder viewHolder, final UserTimeTableEvent model, int position) {

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventController.getEvent(model.getEventId(), TimetableActivity.this, ShowEventActivity.class);
                    }
                });
                viewHolder.bindToUserTimetable(model);
            }
        };


        mRecyclerView.setAdapter(mTimetableAdapter);


    }
}
