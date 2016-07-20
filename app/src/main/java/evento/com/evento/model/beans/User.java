package evento.com.evento.model.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String id;
    private String email;
    private String name;
    private String password;
    private String birthdate;
    private String location;
    private String imageUrl;
    private String gender;
    private ArrayList<Category>interests;
    public User() {
        id= "";
        email="";
        name="";
        password="";
        birthdate="";
        location="";
        imageUrl="";
        gender="";
        interests=new ArrayList<>();
    }

    public UserRepresentation exportMiniUser(){
        UserRepresentation miniUser = new UserRepresentation();
        miniUser.setId(id);
        miniUser.setImageUrl(imageUrl);
        miniUser.setName(name);
        return miniUser;
    }

    public void importMiniUser(UserRepresentation miniUser){
        id = miniUser.getId();
        name = miniUser.getName();
        imageUrl = miniUser.getImageUrl();
    }

    public void addInterest(Category interest){
        interests.add(interest);
    }

    public ArrayList<Category> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<Category> interests) {
        this.interests = interests;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String image) {
        this.imageUrl = image;
    }

}