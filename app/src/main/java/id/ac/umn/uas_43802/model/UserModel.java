package id.ac.umn.uas_43802.model;

public class UserModel {
    String email, gender, password, name, phone, photoUrl, pin, uid, birthdate;

    public UserModel(String birthdate, String email, String gender, String name, String password, String phone, String photoUrl, String pin, String uid){
        this.birthdate = birthdate;
        this.email =  email;
        this.gender = gender;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.photoUrl = photoUrl;
        this.pin = pin;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getPin() {
        return pin;
    }

    public String getUid() {
        return uid;
    }

    public String getBirthdate() {
        return birthdate;
    }
}
