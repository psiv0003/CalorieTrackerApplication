package poornima.com.calorietrackerapplication.Models;

public class UserCredential {

    private String passwordhash;
    private String signupdate;
    private User userid;
    private String username;

    public  UserCredential(String passwordhash, String signupdate, User userid, String username){
        this.passwordhash = passwordhash;
        this.signupdate = signupdate;
        this.userid = userid;
        this.username = username;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public String getSignupdate() {
        return signupdate;
    }

    public void setSignupdate(String signupdate) {
        this.signupdate = signupdate;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
