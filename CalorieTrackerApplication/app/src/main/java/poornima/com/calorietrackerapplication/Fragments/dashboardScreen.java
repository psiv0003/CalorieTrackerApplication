package poornima.com.calorietrackerapplication.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import poornima.com.calorietrackerapplication.Activities.MainActivity;
import poornima.com.calorietrackerapplication.Models.Report;
import poornima.com.calorietrackerapplication.Models.User;
import poornima.com.calorietrackerapplication.R;
import poornima.com.calorietrackerapplication.WebServices.RestWS;

import static android.content.Context.MODE_PRIVATE;
import static poornima.com.calorietrackerapplication.WebServices.RestWS.createReport;

public class dashboardScreen extends Fragment {
    View view;
    TextView name, setCalorieGoal;
    String email, firstName, calorieGoal, calConsumedTotal, calBurnedTotal, calRemaningTotal, stepsTotal, date;
    Button dateBtn;
    String userId;
    Boolean reportExsists;
    User user;
    String resp;
    String report;
    String calorieGoalForDay;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        getActivity().setTitle("Dashboard");

        name = (TextView) view.findViewById(R.id.nameTxt);
        dateBtn = (Button) view.findViewById(R.id.dateBtn);
        setCalorieGoal = (TextView) view.findViewById(R.id.cg1Txt2);


        stepsTotal = "0";
        calBurnedTotal = "0";
        calConsumedTotal = "0";
        calRemaningTotal = "0";


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 'at' HH:mm");
        String currentDateandTime = sdf.format(new Date());

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf2.format(new Date());
        date = date + "T00:00:00+00:00";
       // Log.d("DATE", date);

        preferences = this.getActivity().getSharedPreferences("userDetails", MODE_PRIVATE);
        editor =this.getActivity().getSharedPreferences("userDetails", MODE_PRIVATE).edit();

        String nameTxt = preferences.getString("name", "");
        userId = preferences.getString("user_id", "");

        user = new User(preferences.getString("name", ""),preferences.getString("surname", ""),preferences.getString("email", ""),preferences.getString("height", ""),preferences.getString("weight", ""),preferences.getString("address", ""),preferences.getString("postcode", ""),preferences.getString("dob", ""),preferences.getString("stepspermile", ""),preferences.getString("activitylevel", ""),preferences.getString("gender", ""), preferences.getString("user_id", ""));


        Log.d("BUND",nameTxt+"");


        name.setText("Hello "+ nameTxt +"!");
        dateBtn.setText(currentDateandTime);

        getReportDetails getReportDetails = new getReportDetails();
        getReportDetails.execute(userId,date);

        setCalorieGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setCalGoalfunc();
            }
        });
        return view;
    }
    public void setCalGoalfunc(){
        LayoutInflater li = (LayoutInflater) LayoutInflater.from(this.getActivity());
        View promptsView = li.inflate(R.layout.set_calorie_goal, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this.getActivity());
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.calGoalEditTxt);
        alertDialogBuilder
                .setTitle("Enter Calorie Goal")
                .setCancelable(false)
                .setPositiveButton("Set",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                               String calorieGoalUpdate = userInput.getText().toString();
                              //  setCalorieGoal.setText(calorieGoal);
                                updateCalories updateCalories = new updateCalories();
                                updateCalories.execute(userId, calorieGoalUpdate);


                                //TODO connect these values to some place

                            }
                        })
                .setNegativeButton("Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private class updateCalories extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String results;
        @Override
        public void onPreExecute(){
            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", "Loading...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            //need to check if it's an exsisting record or not
            if(reportExsists){

                Report reportuser = new Report((report), (strings[1]), date, (calConsumedTotal), (stepsTotal),user,(calBurnedTotal) );
                resp = RestWS.UpdateReport(reportuser, report);
                Log.d("RESP", resp);



            }else{
                //create new record - POST
                //get count of report
                String count = RestWS.reportId();
                int reportId = Integer.parseInt(count) + 1;
                report = String.valueOf(reportId);
                Report report = new Report(String.valueOf(reportId), (strings[1]), date, (calConsumedTotal),(stepsTotal),user,(calBurnedTotal) );
                resp =  RestWS.createReport(report);



            }
           // results =  RestWS.getFoodDetails();


            calorieGoalForDay = strings[1];
            editor.putString("calorie_goal", calorieGoalForDay);
            editor.commit();
            return resp;

        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();



            dashboardScreen dashboardScreen = new dashboardScreen();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,
                    dashboardScreen).commit();


        }
    }

    private class getReportDetails extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String results;
        @Override
        public void onPreExecute(){
            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", "Loading...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            results =  RestWS.getReportDetails(params[0], params[1]);

            return results;

        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Log.d("RES", result);
            try {
                Double calBurned, calRemaning, calConsumed, totalSteps;
                JSONObject jsonObject = new JSONObject(result);
                int calGoal;

                calBurned = Double.parseDouble(jsonObject.getString("Total Calories Burned"));
                calRemaning = Double.parseDouble(jsonObject.getString("Remainng Calories"));
                calConsumed = Double.parseDouble(jsonObject.getString("Total Calories Consumed"));
                calGoal = Integer.parseInt(jsonObject.getString("Calorie Goal"));
                totalSteps = Double.parseDouble(jsonObject.getString("Total Steps"));

                if(calBurned==0 && calRemaning == 0 && calConsumed == 0 && calGoal == 0 & totalSteps == 0){
                    Log.d("RES","No details for today");
                    setCalorieGoal.setText("Click to enter!");
                    reportExsists = false;


                }
                else{
                    reportExsists = true;
                    calorieGoal = String.valueOf(calGoal);
                    setCalorieGoal.setText(calorieGoal);
                    stepsTotal = jsonObject.getString("Total Steps");
                    calBurnedTotal = jsonObject.getString("Total Calories Burned");
                    calConsumedTotal = jsonObject.getString("Total Calories Consumed");
                    calRemaningTotal = jsonObject.getString("Remainng Calories");
                    calorieGoalForDay = calorieGoal;
                    report = jsonObject.getString("id");

                    editor.putString("calorie_goal", calorieGoalForDay);
                    editor.putString("total_steps", stepsTotal);
                    editor.putString("report_id", report);
                    editor.putString("report_date", date);

                    editor.commit();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


//            try {
//                displayItem(results);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        }
    }
}
