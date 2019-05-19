package poornima.com.calorietrackerapplication.Fragments;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import poornima.com.calorietrackerapplication.Models.Report;
import poornima.com.calorietrackerapplication.Models.Steps;
import poornima.com.calorietrackerapplication.Models.StepsDatabase;
import poornima.com.calorietrackerapplication.Models.User;
import poornima.com.calorietrackerapplication.Others.StepsAdapter;
import poornima.com.calorietrackerapplication.R;
import poornima.com.calorietrackerapplication.WebServices.RestWS;

import static android.content.Context.MODE_PRIVATE;

public class StepsScreen extends Fragment {
    StepsDatabase db = null;

    private AlarmManager alarmMgr;
    private Intent alarmIntent;

    private PendingIntent pendingIntent;
    private AlarmManager manager;

    RecyclerView lv ;
    List<Steps> StepsList = new ArrayList<>();
    SharedPreferences preferences;

    View view;
    Context context;
    Button addStepsBtn, updateBtn;
    Double totalCalories = 0.0;
    String report_id;
    String calorieGoal, calConsumed, userId, date, caloriesBurnedRest, caloriesBurnedStep, calPerStep;

    int totalSteps=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.steps_fragment, container, false);
        getActivity().setTitle("Steps");
        addStepsBtn = (Button) view.findViewById(R.id.addEntry);

        context = getActivity();
        db = Room.databaseBuilder(getActivity(),
                StepsDatabase.class, "steps_database")
                .fallbackToDestructiveMigration()
                .build();

         lv = (RecyclerView) view.findViewById(R.id.list);


        preferences = this.getActivity().getSharedPreferences("userDetails", MODE_PRIVATE);

        calorieGoal = preferences.getString("calorie_goal", "");
        userId = preferences.getString("user_id", "");

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf2.format(new Date());
       // date = date + "T00:00:00+00:00";
        Log.d("DATE", date);


        Log.d("GOAAL", calorieGoal);


        ReadDatabase rd = new ReadDatabase();
        rd.execute();



        updateBtn = (Button) view.findViewById(R.id.delBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTable();
            }

        });

        addStepsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addStepsPopUp();
            }
        });




        return view;
    }

    public void  addStepsPopUp(){

        LayoutInflater li = (LayoutInflater) LayoutInflater.from(this.getActivity());
        View promptsView = li.inflate(R.layout.add_steps, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this.getActivity());
        alertDialogBuilder.setView(promptsView);

       // final EditText userInputime = (EditText) promptsView.findViewById(R.id.setTime);

        final  EditText steps = (EditText) promptsView.findViewById(R.id.setSteps);
        alertDialogBuilder
                .setTitle("Enter Number of Steps")
                .setCancelable(false)
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                //TODO connect these values to some place

                                //get time and steps

                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                String time = sdf.format(new Date());

                                String usersteps = steps.getText().toString();
                                addSteps addSteps = new  addSteps();
                                addSteps.execute(time, usersteps);

                                StepsScreen rSum = new StepsScreen();
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.content_frame,
                                        rSum).commit();


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

    private class addSteps extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... strings) {
            Steps steps = new Steps(strings[0], Integer.parseInt(strings[1]) );
            long id = db.StepsDAO().insert(steps);

            Log.d("IDD", String.valueOf(id));
            return null;
        }

        @Override
        protected void onPostExecute(String details) {
           // textView_insert.setText("Added Record: " + details);
        }
    }


    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<Steps> steps = db.StepsDAO().getAll();
            if (!(steps.isEmpty() || steps == null) ){
                String allUsers = "";
                for (Steps temp : steps) {

                    StepsList.add(temp);
                    totalSteps = totalSteps + temp.getSteps();




                }
                // return allUsers;
            }



            return  "";
        }
        @Override
        protected void onPostExecute(String details) {
            Log.d("LALA", details);
            StepsAdapter adapter = new StepsAdapter(getActivity(),  StepsList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);


            Log.d("PLEASEEEEEEEEE", "Newsssss");
            //  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            lv.setLayoutManager(mLayoutManager);

            lv.setItemAnimator(new DefaultItemAnimator());
            lv.setAdapter(adapter);
            lv.setNestedScrollingEnabled(true);

            //textView_read.setText("All data: " + details);
        }
    }

    public void  updateTable(){

        // get the The user calorie set goal (this will be entered daily by the user in the home screen)
        //calorieGoal

        //get the the total calories consumed for that day (based on REST calculation methods),
        getCalConsumed getCalConsumed = new getCalConsumed();
        getCalConsumed.execute(userId,date);
        //calConsumed
        //get the the total number of steps (based on the daily steps table’s data)

      //  Log.d("total steps", totalSteps);

        //get the the total calories burned for that day (based on REST calculation methods)


     //   Log.d("total calories burned", totalCalories.toString());

       //now POST and Delete the data

    }

    private class getCalConsumed extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            calConsumed = RestWS.getCaloriesConsumed(params[0], params[1]);

            caloriesBurnedRest = RestWS.getCaloriesBurnedAtRest(params[0]);

            caloriesBurnedStep = RestWS.getCalBurnedPerStep(params[0]);

            Log.d("caloriesBurnedStep", "" +caloriesBurnedStep);
            Log.d("caloriesBurnedRest", caloriesBurnedRest + "");
            calculateCalories(calConsumed,caloriesBurnedStep,caloriesBurnedRest);
            return  " ";
        }
        @Override
        protected void onPostExecute(String details) {

        }
    }

    public void calculateCalories(String calConsumed, String caloriesBurnedStep, String caloriesBurnedRest){

        Double consumed = 0.0;
        double burned = 0.0;
        double rest = 0.0;
        double total = 0.0;
        double steps = Double.valueOf(totalSteps);

        consumed = Double.parseDouble(calConsumed);
        burned = Double.parseDouble(caloriesBurnedStep);
        rest = Double.parseDouble(caloriesBurnedRest);

        total = (steps * burned) + rest;

        totalCalories = total;
        int valueX = (int) Math.rint(total);
        int c = (int) Math.rint(consumed);

        Log.d("PLEASE", totalCalories.toString() + ":::::"+ valueX);

        //calorieGoal
        //totalCalories
        //totalSteps
        //consumed

         date = preferences.getString("report_date", "");
        report_id = preferences.getString("report_id", "");

        //post


        updateReportDate updateReportDate = new updateReportDate();
        updateReportDate.execute(String.valueOf(c),String.valueOf(valueX), String.valueOf(totalSteps));



    }


    //post

    private class updateReportDate extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {



            User user = new User(preferences.getString("name", ""),preferences.getString("surname", ""),preferences.getString("email", ""),preferences.getString("height", ""),preferences.getString("weight", ""),preferences.getString("address", ""),preferences.getString("postcode", ""),preferences.getString("dob", ""),preferences.getString("stepspermile", ""),preferences.getString("activitylevel", ""),preferences.getString("gender", ""), preferences.getString("user_id", ""));



            Report report = new Report( (report_id), calorieGoal, date,  strings[0], strings[2],user, strings[1]);

             RestWS.UpdateReport(report,report_id);

            return "Added";
        }

        @Override
        protected void onPostExecute(String details) {

            DeleteDatabase deleteDatabase = new DeleteDatabase();
            deleteDatabase.execute();
        }
    }

    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db.StepsDAO().deleteAll();
            return null;
        }
        protected void onPostExecute(Void param) {
            Toast.makeText(getActivity(), "Updated Succesfully!",Toast.LENGTH_SHORT).show();

            StepsScreen del = new StepsScreen();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,
                    del).commit();
        }
    }



}
