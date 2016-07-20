package evento.com.evento.view.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import evento.com.evento.R;
import evento.com.evento.view.adpters.InterstedAdapter;
import evento.com.evento.view.adpters.PostViewHolder;
import evento.com.evento.controllers.PostController;
import evento.com.evento.controllers.UserController;
import evento.com.evento.model.beans.Post;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;

public class ShowEventActivity extends AppCompatActivity {


    RecyclerView mRecyclerView;
    FirebaseRecyclerAdapter<Post , PostViewHolder> mPostAdapter;
    LinearLayoutManager mLayoutManager;
    RelativeLayout mRelativeLayout;
    ImageView mIMGOwner;
    TextView mEevntName;
    TextView mStartDate;
    TextView mEndDate;
    TextView mDescription;
    TextView mTag1;
    TextView mTag2;
    Button mJoin;
    ImageView mdeletePost;
    TextView creatorName;

    InterstedAdapter listAdapter;
    ArrayList<String> listDataHeader;
    HashMap<String, ArrayList<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        getpost();


        mEevntName.setText(Commons.activeEvent.getName());
        mTag1.setText(Commons.activeEvent.getCategories().get(0).getName());
        mTag2.setText(Commons.activeEvent.getCategories().get(1).getName());
        mStartDate.setText(Commons.activeEvent.getStartDate());
        mEndDate.setText(Commons.activeEvent.getEndDate());
        mDescription.setText(Commons.activeEvent.getDescription());
        mdeletePost= (ImageView) findViewById(R.id.IMGdelete);
        creatorName.setText(Commons.activeEvent.getCreatorName());
        Commons.firebaseStorageReference.child(Constants.IMAGES+"/"+Commons.activeEvent.getId())
                .getBytes(Long.MAX_VALUE).
                addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Use the bytes to display the image
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Drawable dr = new BitmapDrawable(bmp);
                        mRelativeLayout.setBackgroundDrawable(dr);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        if (Commons.isUserJointTheEvent())
            mJoin.setText("unattend");
        else
            mJoin.setText("attend");

        mJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mJoin.getText()=="attend"){
                    UserController.joinEvent(Commons.activeEvent);
                    mJoin.setText("unattend");
                }else {
                    UserController.disJoinEvent(Commons.activeEvent);
                    mJoin.setText("attend");
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowEventActivity.this , CreatePostActivity.class);
                ShowEventActivity.this.startActivity(intent);
            }
        });
    }

    void init(){
        creatorName=(TextView)  findViewById(R.id.TVeventCreatorName);
        mRelativeLayout= (RelativeLayout) findViewById(R.id.event_item_background);
        mIMGOwner= (ImageView) findViewById(R.id.IMGeventOwner);
        mStartDate= (TextView) findViewById(R.id.TVstartDate);
        mEevntName = (TextView) findViewById(R.id.TVeventName);
        mEndDate= (TextView) findViewById(R.id.TVendDate);
        mDescription= (TextView) findViewById(R.id.TVeventdescr);
        mTag1= (TextView) findViewById(R.id.TVshowTag1);
        mTag2= (TextView) findViewById(R.id.TVshowTag2);


        //mExpandableListView= (ExpandableListView) findViewById(R.id.LVshowEventIntersted);
        mJoin= (Button) findViewById(R.id.BTjoin);
        //mRecyclerView = (RecyclerView) findViewById(R.id.LVposts);
        //mRecyclerView.setHasFixedSize(true);
    }

    void getpost(){
        mRecyclerView = (RecyclerView) findViewById(R.id.LVposts);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Query postQuery = PostController.getEventPosts(Commons.activeEvent);
        mPostAdapter = new FirebaseRecyclerAdapter<Post , PostViewHolder>(Post.class , R.layout.item_post ,
                PostViewHolder.class, postQuery){

            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, final Post model, int position) {

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PostController.getPostById(model, ShowEventActivity.this, ShowPostActivity.class);

                    }
                });

                viewHolder.bindToPost(model);

            }
        };

        mRecyclerView.setAdapter(mPostAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Commons.changeActivity(ShowEventActivity.this, MainActivity.class);
    }
}
