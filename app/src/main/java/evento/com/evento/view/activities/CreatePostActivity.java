package evento.com.evento.view.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import evento.com.evento.R;
import evento.com.evento.controllers.PostController;
import evento.com.evento.model.beans.Post;
import evento.com.evento.utils.Commons;

public class CreatePostActivity extends AppCompatActivity {

    EditText mBodyField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBodyField = (EditText) findViewById(R.id.ETbodyPost);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post post = new Post();

                post.setCreatorName(Commons.currentActiveUser.getName());
                post.setCreatorId(Commons.currentActiveUser.getId());
                post.setCreatorImageUrl(Commons.currentActiveUser.getImageUrl());
                post.setEventId(Commons.activeEvent.getId());
                post.setContent(mBodyField.getText().toString());
                PostController.createPost(post, CreatePostActivity.this, ShowEventActivity.class);
            }
        });
    }

}
