package poornima.com.calorietrackerapplication.Models;

public class Report {
    private  String reportid;
    private  String caloriegoal;
    private String date;
    private String totalcaloriesconsumed;
    private String totalsteps;
    private User userid;
    private String totalcaloriesburned;

    public Report(String reportid, String caloriegoal, String date, String totalcaloriesconsumed, String totalsteps, User userid, String totalcaloriesburned) {
        this.reportid = reportid;
        this.caloriegoal = caloriegoal;
        this.date = date;
        this.totalcaloriesconsumed = totalcaloriesconsumed;
        this.totalsteps = totalsteps;
        this.userid = userid;
        this.totalcaloriesburned = totalcaloriesburned;
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public String getCaloriegoal() {
        return caloriegoal;
    }

    public void setCaloriegoal(String caloriegoal) {
        this.caloriegoal = caloriegoal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalcaloriesconsumed() {
        return totalcaloriesconsumed;
    }

    public void setTotalcaloriesconsumed(String totalcaloriesconsumed) {
        this.totalcaloriesconsumed = totalcaloriesconsumed;
    }

    public String getTotalsteps() {
        return totalsteps;
    }

    public void setTotalsteps(String totalsteps) {
        this.totalsteps = totalsteps;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    public String getTotalcaloriesburned() {
        return totalcaloriesburned;
    }

    public void setTotalcaloriesburned(String totalcaloriesburned) {
        this.totalcaloriesburned = totalcaloriesburned;
    }
}
