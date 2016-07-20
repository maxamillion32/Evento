package evento.com.evento.model.dao;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import evento.com.evento.model.beans.Event;
import evento.com.evento.model.beans.Post;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;

/**
 * Created by Mohamed on 6/17/2016.
 */
public class PostDao {

    public static void insertPost(Post post , Context context, Class newActivityClass){
        post.setId(Commons.firebaseDatabaseReference.child(Constants.POST_CHILD).push().getKey());
        Commons.firebaseDatabaseReference.child(Constants.POST_CHILD).child(post.getEventId())
                .child(post.getId()).setValue(post);
        Commons.changeActivity(context, newActivityClass);
    }


    public static void retrievePostInfo(Post post, final Context context, final Class newActivityClass) {
        Query queryRef = Commons.firebaseDatabaseReference.child(Constants.POST_CHILD)
                .child(post.getEventId())
                .orderByChild(Constants.ID_CHILD)
                .equalTo(post.getId());
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(Constants.TAG, "retrievePostInfo");
                Commons.startActivePost();
                Commons.activePost = (dataSnapshot.getValue(Post.class));
                Commons.changeActivity(context, newActivityClass);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void deletePost(Post post){
        CommentDao.deleteAllPostComments(post);
        Commons.firebaseDatabaseReference.child(Constants.POST_CHILD).child(post.getEventId())
                .child(post.getId()).removeValue();
    }

    public static Query retrieveEventPosts(Event event){
        Query postsQuery = Commons.firebaseDatabaseReference.child(Constants.POST_CHILD)
                .child(event.getId());
        return postsQuery;
    }
}
