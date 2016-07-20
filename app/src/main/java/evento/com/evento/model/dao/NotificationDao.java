package evento.com.evento.model.dao;

import com.google.firebase.database.Query;

import evento.com.evento.model.beans.Notification;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;

/**
 * Created by Mohamed on 6/17/2016.
 */
public class NotificationDao {

    public static void insertNotification(Notification notification){
        notification.setId(Commons.firebaseDatabaseReference.child(Constants.NOTIFICATION_CHILD).push().getKey());
            Commons.firebaseDatabaseReference.child(Constants.NOTIFICATION_CHILD).child(notification.getReceiverId())
                    .child(notification.getId()).setValue(notification);
    }

    public static Query retrieveUserNotifications(){
        Query postsQuery = Commons.firebaseDatabaseReference.child(Constants.NOTIFICATION_CHILD)
                .child(Commons.currentActiveUser.getId());
        return postsQuery;
    }
}
