package evento.com.evento.controllers;

import com.google.firebase.database.Query;

import evento.com.evento.model.dao.NotificationDao;

/**
 * Created by Mohamed on 6/24/2016.
 */
public class NotificationController {
    public static Query getUserNotifications(){
        return NotificationDao.retrieveUserNotifications();
    }
}
