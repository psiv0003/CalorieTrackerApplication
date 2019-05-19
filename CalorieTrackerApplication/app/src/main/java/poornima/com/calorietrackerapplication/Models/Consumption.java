package poornima.com.calorietrackerapplication.Models;

public class Consumption {
    private User userid;
    private Food foodid ;
    private String qtyamount ;
    private String date;
    private String consumptionid;

    public Consumption(User userid, Food foodid,  String date, String qtyamount, String consumptionid) {
       this.userid = userid;
        this.foodid = foodid;
        this.consumptionid = consumptionid;
        this.date = date;
        this.qtyamount = qtyamount;
    }

    public User getUserId() {
        return userid;
    }

    public void setUserId(User user) {
        this.userid = user;
    }

    public Food getFoodid() {
        return foodid;
    }

    public void setFoodid(Food foodid) {
        this.foodid = foodid;
    }

    public String getConsumptionid() {
        return consumptionid;
    }

    public void setConsumptionid(String consumptionid) {
        this.consumptionid = consumptionid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQtyamount() {
        return qtyamount;
    }

    public void setQtyamount(String qtyamount) {
        this.qtyamount = qtyamount;
    }
}
