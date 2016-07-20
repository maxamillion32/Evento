package evento.com.evento.model.beans;

import java.io.Serializable;

public class Comment implements Serializable {
	private String id;
	private String content;
	private String creatorId;
	private String creatorName;
	private String creatorImageUrl;
    private String postId;

	public Comment() {
		id= "";
		content="";
        creatorId="";
		creatorName="";
		creatorImageUrl="";
        postId="";
	}

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getCreatorImageUrl() {
		return creatorImageUrl;
	}

	public void setCreatorImageUrl(String creatorImageUrl) {
		this.creatorImageUrl = creatorImageUrl;
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

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

}
