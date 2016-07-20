package evento.com.evento.controllers;

import android.content.Context;

import com.google.firebase.database.Query;

import evento.com.evento.model.beans.Event;
import evento.com.evento.model.beans.Post;
import evento.com.evento.model.dao.PostDao;

/**
 * Created by Mohamed on 6/24/2016.
 */
public class PostController {
    public static void createPost(Post post, Context context, Class newActivityClass){
        PostDao.insertPost(post, context, newActivityClass);
    }

    public static void deletePost(Post post){
        PostDao.deletePost(post);
    }

    public static Query getEventPosts(Event event){
        return PostDao.retrieveEventPosts(event);
    }

    public static void getPostById(Post post, Context context, Class newActivityClass){
        PostDao.retrievePostInfo(post,context, newActivityClass);
    }
}
