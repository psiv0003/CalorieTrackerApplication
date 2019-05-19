package poornima.com.calorietrackerapplication.Fragments;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import poornima.com.calorietrackerapplication.Models.Steps;
import poornima.com.calorietrackerapplication.Models.StepsDatabase;
import poornima.com.calorietrackerapplication.Others.StepsAdapter;
import poornima.com.calorietrackerapplication.R;
import poornima.com.calorietrackerapplication.WebServices.RestWS;

import static android.content.Context.MODE_PRIVATE;

public class CalTrackerScreen extends Fragment {
    View view;
    TextView calorieGoalTxt, totalStepsTxt, caloriesBurnedTxt, caloriesConsumedTxt;
    String calGoal, totalSteps, calBurned, calConsumed, userId, reportId;
    SharedPreferences preferences;
    String caloriesBurnedAtRest;
    StepsDatabase db = null;
    String  caloriesBurnedStep;
    List<Steps> StepsList = new ArrayList<>();
    Context context;
    int totalStepsVal=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.cal_tracker_fragment, container, false);

        getActivity().setTitle("Calorie Tracker");

        context = getActivity();
        db = Room.databaseBuilder(getActivity(),
                StepsDatabase.class, "steps_database")
                .fallbackToDestructiveMigration()
                .build();


        preferences = this.getActivity().getSharedPreferences("userDetails", MODE_PRIVATE);

        calGoal = preferences.getString("calorie_goal", "");
        userId = preferences.getString("user_id", "");

        calorieGoalTxt = (TextView) view.findViewById(R.id.cg1Txt2);
        totalStepsTxt = (TextView) view.findViewById(R.id.steps);
        caloriesBurnedTxt = (TextView) view.findViewById(R.id.burned);
        caloriesConsumedTxt = (TextView) view.findViewById(R.id.consumed);

        ReadDatabase rd = new ReadDatabase();
        rd.execute();

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf2.format(new Date());

        getCalConsumed getCalConsumed = new getCalConsumed();
        getCalConsumed.execute(userId,date);



        calorieGoalTxt.setText(calGoal);
        return view;
    }

    // to get the number of steps
    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<Steps> steps = db.StepsDAO().getAll();
            if (!(steps.isEmpty() || steps == null) ){
                String allUsers = "";
                for (Steps temp : steps) {

                    StepsList.add(temp);
                    totalStepsVal = totalStepsVal + temp.getSteps();




                }
                // return allUsers;
            }



            return  "";
        }
        @Override
        protected void onPostExecute(String details) {

            totalStepsTxt.setText(String.valueOf(totalStepsVal)+" ");

        }
    }

    //to get the calories consumed

    private class getCalConsumed extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            calConsumed = RestWS.getCaloriesConsumed(params[0], params[1]);

             caloriesBurnedAtRest = RestWS.getCaloriesBurnedAtRest(params[0]);
              caloriesBurnedStep = RestWS.getCalBurnedPerStep(params[0]);



            return  calConsumed;
        }
        @Override
        protected void onPostExecute(String details) {
            calculateCalories(calConsumed,caloriesBurnedStep,caloriesBurnedAtRest);
            caloriesConsumedTxt.setText(calConsumed);

        }
    }

    //get total calories burned

    public void calculateCalories(String calConsumed, String caloriesBurnedStep, String caloriesBurnedRest){

        Double consumed = 0.0;
        double burned = 0.0;
        double rest = 0.0;
        double total = 0.0;
        double steps = Double.valueOf(totalStepsVal);

        consumed = Double.parseDouble(calConsumed);
        burned = Double.parseDouble(caloriesBurnedStep);
        rest = Double.parseDouble(caloriesBurnedRest);

        total = (steps * burned) + rest;


        int valueX = (int) Math.rint(total);
        int c = (int) Math.rint(consumed);


        caloriesBurnedTxt.setText(String.valueOf(total));

    }
}
