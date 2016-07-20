package evento.com.evento.model.dao;

import com.google.firebase.database.Query;

import evento.com.evento.model.beans.Comment;
import evento.com.evento.model.beans.Post;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;

/**
 * Created by Mohamed on 6/17/2016.
 */
public class CommentDao {

    public static void insertComment(Comment comment) {
        comment.setId(Commons.firebaseDatabaseReference.child(Constants.COMMENT_CHILD).push().getKey());
        Commons.firebaseDatabaseReference.child(Constants.COMMENT_CHILD).child(comment.getPostId())
                .child(comment.getId()).setValue(comment);
    }

    public static void deleteAllPostComments(Post post){
        Commons.firebaseDatabaseReference.child(Constants.COMMENT_CHILD).child(post.getId()).removeValue();
    }

    public static Query retrievePostComments(Post post){
        Query postsQuery = Commons.firebaseDatabaseReference.child(Constants.COMMENT_CHILD)
                .child(post.getId());
        return postsQuery;
    }
}
