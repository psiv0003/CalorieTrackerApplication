package poornima.com.calorietrackerapplication.Models;

public class Food {
    private String calorieamount;
    private String category;
    private String fat;
    private String foodid;
    private String name;
    private String servingamt;
    private String servingunit;

    public Food(String calorieamount, String category, String fat, String foodid, String name, String servingamt, String servingunit) {
        this.calorieamount = calorieamount;
        this.category = category;
        this.fat = fat;
        this.foodid = foodid;
        this.name = name;
        this.servingamt = servingamt;
        this.servingunit = servingunit;
    }

    public String getCalorieamount() {
        return calorieamount;
    }

    public void setCalorieamount(String calorieamount) {
        this.calorieamount = calorieamount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServingamt() {
        return servingamt;
    }

    public void setServingamt(String servingamt) {
        this.servingamt = servingamt;
    }

    public String getServingunit() {
        return servingunit;
    }

    public void setServingunit(String servingunit) {
        this.servingunit = servingunit;
    }
}