package evento.com.evento.interfaces;

import java.util.ArrayList;

import evento.com.evento.model.beans.UserTimeTableEvent;

/**
 * Created by amr masoud on 6/28/2016.
 */
public interface CallBack {
    void setData (ArrayList<UserTimeTableEvent> eventsList) ;
}
