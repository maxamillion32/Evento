package evento.com.evento.model.beans;

import java.io.Serializable;

public class Notification implements Serializable {
	private String id;
	private String receiverId;
	private String content;
    private String senderName;
    private String senderImageUrl;
	private String userIdOrEventId;

	public Notification() {
		id= "";
		receiverId="";
		content="";
		userIdOrEventId ="";
        senderName="";
        senderImageUrl="";
	}

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderImageUrl() {
        return senderImageUrl;
    }

    public void setSenderImageUrl(String senderImageUrl) {
        this.senderImageUrl = senderImageUrl;
    }

    public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserIdOrEventId() {
		return userIdOrEventId;
	}

	public void setUserIdOrEventId(String userIdOrEventId) {
		this.userIdOrEventId = userIdOrEventId;
	}

}
