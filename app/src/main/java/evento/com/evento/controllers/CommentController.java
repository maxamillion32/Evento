package evento.com.evento.controllers;

import evento.com.evento.model.beans.Comment;
import evento.com.evento.model.beans.Post;
import evento.com.evento.model.dao.CommentDao;
import com.google.firebase.database.Query;

/**
 * Created by Mohamed on 6/24/2016.
 */
public class CommentController {

    public static void createComment(Comment comment){
        CommentDao.insertComment(comment);
    }

    public static Query getPostComments(Post post){
        return CommentDao.retrievePostComments(post);
    }
}
