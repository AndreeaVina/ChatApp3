package client;

public class User {
    String userName;
    String fullName;
    String password;
    String email;
    String gender;
    String phoneNumber;
    User(){

    }
    public User(String fullName, String userName, String email, String phone, String gender, String password){
        this.setFullName(fullName);
        this.setUserName(userName);
        this.setEmail(email);
        this.setPhoneNumber(phone);
        this.setGender(gender);
        this.setPassword(password);
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }
}