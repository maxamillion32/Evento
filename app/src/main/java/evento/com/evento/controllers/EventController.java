package evento.com.evento.controllers;

import android.content.Context;

import com.google.firebase.database.Query;

import evento.com.evento.model.beans.Event;
import evento.com.evento.model.dao.EventDao;

/**
 * Created by Mohamed on 6/24/2016.
 */
public class EventController {

    public static void createEvent(Event event, Context context, Class newActivityClass){

        EventDao.insertEventInfo(event, context, newActivityClass);
    }



    public static void deleteEvent(Event event){
        EventDao.deleteEventInfo(event);
    }

    /*public static void updateEvent(Event event){
        EventDao.updateEventInfo(event);
        getJoinList(event).addChildEventListener(new Chil)
    }*/

    public static void getEvent(String eventId, Context context, Class newClass){
        EventDao.retrieveEventInfo(eventId, context, newClass);
    }

    public static Query getJoinList(Event event){
        return EventDao.retrieveEvent_JoinUsers(event);
    }

    public static Query searchForEvent(String name){
        return EventDao.searchForEvent(name);
    }
}
