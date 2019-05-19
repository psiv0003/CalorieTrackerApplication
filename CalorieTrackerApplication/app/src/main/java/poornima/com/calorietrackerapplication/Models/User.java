package poornima.com.calorietrackerapplication.Models;

public class User {

    private String name;
    private String surname;
    private String email ;
    private String username;
    private String password;
    private String height ;
    private String weight ;
    private String address ;
    private String postcode ;
    private String dob ;
    private String stepspermile;
    private String activitylevel;
    private String gender ;
    private String userid;


    public User(String name, String surname, String email, String height, String weight, String address,
                String postcode, String dob, String stepspermile, String activitylevel, String gender, String userid){
        this.name = name;
        this.surname = surname;
        this.email = email;
       // this.password = password;
     //   this.username = username;
        this.height = height;
        this.weight = weight;
        this.postcode = postcode;
        this.address = address;
        this.dob = dob;
        this.stepspermile = stepspermile;
        this.gender = gender;
        this.activitylevel = activitylevel;
        this.userid = userid;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getStepspermile() {
        return stepspermile;
    }

    public void setStepspermile(String stepspermile) {
        this.stepspermile = stepspermile;
    }

    public String getActivitylevel() {
        return activitylevel;
    }

    public void setActivitylevel(String activitylevel) {
        this.activitylevel = activitylevel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
