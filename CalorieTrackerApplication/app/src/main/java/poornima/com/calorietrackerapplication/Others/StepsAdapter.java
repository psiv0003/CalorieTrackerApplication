package poornima.com.calorietrackerapplication.Others;

import android.app.Activity;
import android.app.FragmentManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import poornima.com.calorietrackerapplication.Activities.MainActivity;
import poornima.com.calorietrackerapplication.Fragments.DailyDietScreen;
import poornima.com.calorietrackerapplication.Models.Steps;
import poornima.com.calorietrackerapplication.Models.StepsDatabase;
import poornima.com.calorietrackerapplication.R;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    Context mContext;
    List<Steps> steps;
    StepsDatabase db = null;

    private ArrayList<String> Steps = new ArrayList<>();


    public StepsAdapter(Activity mContext, List<Steps> steps) {
        this.mContext = mContext;
        this.steps = steps;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView time, steps, id;
        ImageView delete, edit;
        ConstraintLayout lay1;


        public MyViewHolder(View itemView) {
            super(itemView);

            time = (TextView) itemView.findViewById(R.id.timeTxt);
            steps = (TextView) itemView.findViewById(R.id.stepsTxt);
            id = (TextView) itemView.findViewById(R.id.stepsId);
            edit = (ImageView) itemView.findViewById(R.id.editBtn);

        }

    }


    public StepsAdapter(List<Steps> steps){this.steps = steps;}
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Steps stepsUser = steps.get(position);

        holder.time.setText(""+stepsUser.getTime());
        holder.steps.setText(""+stepsUser.getSteps());
        holder.id.setText(""+stepsUser.getStepsId());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //popup-to edit
                update(stepsUser.getStepsId(), stepsUser.getTime());
            }
        });




    }


    @Override
    public int getItemCount() {

        return steps.size();
    }


    public void update(int idSteps, String time){

        db = Room.databaseBuilder(mContext,
                StepsDatabase.class, "steps_database")
                .fallbackToDestructiveMigration()
                .build();

        LayoutInflater li = (LayoutInflater) LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.add_steps, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this.mContext);
        alertDialogBuilder.setView(promptsView);

        // final EditText userInputime = (EditText) promptsView.findViewById(R.id.setTime);

        final EditText steps = (EditText) promptsView.findViewById(R.id.setSteps);
        alertDialogBuilder
                .setTitle("Edit Step Entry at "+ time)
                .setCancelable(false)
                .setPositiveButton("Edit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {



                                //get time and steps

                                String usersteps = steps.getText().toString();
                                UpdateDatabase updateDatabase = new UpdateDatabase();
                                updateDatabase.execute(String.valueOf(idSteps),usersteps);



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

    private class UpdateDatabase extends AsyncTask<String, Void, String> {

        @Override protected String doInBackground(String... params) {
            Steps steps=null;
            Log.d("IDD", params[1]);

            steps = db.StepsDAO().findByID(Integer.parseInt(params[0]));
            steps.setSteps(Integer.parseInt(params[1]));
            db.StepsDAO().updateUsers(steps);


            return "";
        }
        @Override
        protected void onPostExecute(String details) {
           // textView_update.setText("Updated details: "+ details);


            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    mContext);
            alertDialogBuilder.setTitle("Edited Successfully")
                    .setCancelable(false)
                    .setPositiveButton("Okay",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                }
                            })
            ;
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            //TODO - add intent to main activity
          //  Intent intent = new Intent()
        }
    }



}
