package evento.com.evento.model.beans;

import java.io.Serializable;

public class CurrentActiveUser extends User implements Serializable {
    private static CurrentActiveUser currentActiveUser;

    private CurrentActiveUser() {
        super();
    }

    public static CurrentActiveUser getUserInstance(){
        if(currentActiveUser ==null){
            currentActiveUser =new CurrentActiveUser();
        }
        return currentActiveUser;
    }

    @Override
    public UserRepresentation exportMiniUser() {
        return super.exportMiniUser();
    }

    @Override
    public void importMiniUser(UserRepresentation miniUser) {
        super.importMiniUser(miniUser);
    }

    public static void freeUser(){
        currentActiveUser =null;
    }

    @Override
    public String getGender() {
        return super.getGender();
    }

    @Override
    public void setGender(String gender) {
        super.setGender(gender);
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getBirthdate() {
        return super.getBirthdate();
    }

    @Override
    public void setBirthdate(String birthdate) {
        super.setBirthdate(birthdate);
    }

    @Override
    public String getLocation() {
        return super.getLocation();
    }

    @Override
    public void setLocation(String location) {
        super.setLocation(location);
    }

    @Override
    public String getImageUrl() {
        return super.getImageUrl();
    }

    @Override
    public void setImageUrl(String image) {
        super.setImageUrl(image);
    }
}