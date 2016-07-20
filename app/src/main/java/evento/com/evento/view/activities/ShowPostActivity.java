package evento.com.evento.view.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import evento.com.evento.R;
import evento.com.evento.view.adpters.CommentViewHolder;
import evento.com.evento.controllers.CommentController;
import evento.com.evento.controllers.UserController;
import evento.com.evento.model.beans.Comment;
import evento.com.evento.utils.Commons;

public class ShowPostActivity extends AppCompatActivity {

    TextView mAuthorView;
    TextView mBodyView;
    EditText mCommentField;
    RecyclerView mCommentsRecycler;
    LinearLayoutManager mLayoutManager;
    Button mBTcomment;

    FirebaseRecyclerAdapter<Comment , CommentViewHolder> mCommeentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuthorView = (TextView) findViewById(R.id.post_author);
        mBodyView = (TextView) findViewById(R.id.post_body);
        mCommentField = (EditText) findViewById(R.id.ETcomment);
        mCommentsRecycler = (RecyclerView) findViewById(R.id.LVcomments);
        mBTcomment= (Button) findViewById(R.id.BTpostComment);

        mAuthorView.setText(Commons.activePost.getCreatorName());
        mBodyView.setText(Commons.activePost.getContent());

        getComments();

        mBTcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comm = new Comment();
                comm.setCreatorImageUrl(Commons.currentActiveUser.getImageUrl());
                comm.setCreatorId(Commons.currentActiveUser.getId());
                comm.setCreatorName(Commons.currentActiveUser.getName());
                comm.setPostId(Commons.activePost.getId());
                comm.setContent(mCommentField.getText().toString());
                CommentController.createComment(comm);
                mCommentField.setText("");
            }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commons.changeActivity(ShowPostActivity.this, ShowEventActivity.class);
            }
        });
    }

    void getComments(){
        mCommentsRecycler = (RecyclerView) findViewById(R.id.LVcomments);
        mCommentsRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mCommentsRecycler.setLayoutManager(mLayoutManager);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCommentsRecycler.setLayoutManager(mLayoutManager);
        Query commentQuary = CommentController.getPostComments(Commons.activePost);
        mCommeentAdapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(Comment.class, R.layout.item_comment,
                CommentViewHolder.class, commentQuary) {
            @Override
            protected void populateViewHolder(CommentViewHolder viewHolder, final Comment model, int position) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserController.getUserToShowProfile(model.getCreatorId(), ShowPostActivity.this, ShowProfileActivity.class);
                    }
                });
                viewHolder.bindToComment(model);
            }
        };

        mCommentsRecycler.setAdapter(mCommeentAdapter);


    }


}
