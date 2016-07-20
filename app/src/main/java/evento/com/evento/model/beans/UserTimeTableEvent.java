package evento.com.evento.model.beans;

import java.io.Serializable;

public class UserTimeTableEvent implements Serializable {

	private String eventName;
	private String startDate;
	private String eventId;
	private String userID;

	public UserTimeTableEvent() {
		eventName ="";
		startDate="";
		eventId="";
        userID="";
	}

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDate() {
		return startDate;
	}

	public void setDate(String date) {
		this.startDate = date;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

}
