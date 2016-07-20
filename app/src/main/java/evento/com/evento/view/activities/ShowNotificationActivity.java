package evento.com.evento.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import evento.com.evento.R;
import evento.com.evento.view.adpters.NotificationViewHolder;
import evento.com.evento.controllers.NotificationController;
import evento.com.evento.model.beans.Notification;

public class ShowNotificationActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseRecyclerAdapter<Notification, NotificationViewHolder> mNotificationAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);

        mRecyclerView = (RecyclerView) findViewById(R.id.LVnotification);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Query notificationsQuery = NotificationController.getUserNotifications();
        mNotificationAdapter = new FirebaseRecyclerAdapter<Notification, NotificationViewHolder>(Notification.class, R.layout.item_notification_poupup,
                NotificationViewHolder.class, notificationsQuery) {
            @Override
            protected void populateViewHolder(NotificationViewHolder viewHolder, Notification model, int position) {
                viewHolder.binedToNotification(model);
            }
        };

        mRecyclerView.setAdapter(mNotificationAdapter);


    }
}
