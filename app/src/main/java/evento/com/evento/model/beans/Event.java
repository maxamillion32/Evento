package evento.com.evento.model.beans;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

@IgnoreExtraProperties
public class Event implements Serializable {
	private String id;
	private String name;
	private String description;
	private String location;
	private String creatorId;
	private String creatorName;
	private String imageUrl;
	private String startDate;
	private String endDate;
	private ArrayList<Category>categories;

	public Event() {
		id= "";
		name="";
        description="";
        location="";
		creatorName="";
        creatorId="";
        imageUrl="";
        startDate="";
        endDate="";
		categories=new ArrayList<>();
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public UserTimeTableEvent exportUserTimeTableEvent(){
		UserTimeTableEvent userTimeTableEvent = new UserTimeTableEvent();
		userTimeTableEvent.setDate(startDate);
		userTimeTableEvent.setEventId(id);
		userTimeTableEvent.setEventName(name);
		return userTimeTableEvent;
	}

	public ArrayList<Category> getCategories() {
		return categories;
	}

	public void addCategory(Category category){
		categories.add(category);
	}

	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
