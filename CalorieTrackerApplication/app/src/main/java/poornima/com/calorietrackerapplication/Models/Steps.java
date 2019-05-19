package poornima.com.calorietrackerapplication.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Steps {

    @PrimaryKey(autoGenerate = true)
    public int stepsId;


    @ColumnInfo(name = "time")
    public String time;


    @ColumnInfo(name = "steps")
    public int steps;

    public Steps(  String time, int steps) {
        //this.stepsId = stepsId;

        this.time = time;
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getStepsId() {
        return stepsId;
    }

    public void setStepsId(int stepsId) {
        this.stepsId = stepsId;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
