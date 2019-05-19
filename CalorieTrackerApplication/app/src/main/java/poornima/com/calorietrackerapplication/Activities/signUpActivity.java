package poornima.com.calorietrackerapplication.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.Guideline;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import poornima.com.calorietrackerapplication.Models.User;
import poornima.com.calorietrackerapplication.Models.UserCredential;
import poornima.com.calorietrackerapplication.R;
import poornima.com.calorietrackerapplication.WebServices.RestWS;

import static poornima.com.calorietrackerapplication.WebServices.RestWS.getuserId;

public class signUpActivity extends AppCompatActivity {

    //TODO - user validation
    //TODO - check if the id exsits

    EditText dobEditTxt, firstNameEditTxt, lastNameEditTxt, emailEditTxt, stepsPerMileTxt, passwordEdittxt, heightEditTxt, weightEditTxt, addressEditTxt,userNameTxt, postcodeEditTxt;
    RadioButton maleBtn, femaleBtn;
    Guideline guideline;
    final Context context = this;
    String activityLevelTxt;
    String todayDate;
    Boolean emailThere = true, userNameThere = true;

    RadioGroup genderGroup;
    TextView genderTxtView, activityTxtView, signUpTxtView;
    Button loginBtn;
    final Calendar myCalendar = Calendar.getInstance();
    Spinner activityLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        firstNameEditTxt= (EditText) findViewById(R.id.firstNameTxt);
        userNameTxt= (EditText) findViewById(R.id.usernameTxt);
        lastNameEditTxt= (EditText) findViewById(R.id.lastnameTxt);
        emailEditTxt= (EditText) findViewById(R.id.emailTxt);
        passwordEdittxt = (EditText) findViewById(R.id.passwordTxt);
        heightEditTxt= (EditText) findViewById(R.id.heightTxt);
        weightEditTxt= (EditText) findViewById(R.id.weightTxt);
        addressEditTxt= (EditText) findViewById(R.id.addressTxt);
        postcodeEditTxt= (EditText) findViewById(R.id.postcodeTxt);
        stepsPerMileTxt = (EditText) findViewById(R.id.stepsPerMileTxt);
        dobEditTxt= (EditText) findViewById(R.id.dobTxt);
        genderGroup = (RadioGroup) findViewById(R.id.radioGroup);
        activityLevel = (Spinner) findViewById(R.id.spinner);
        maleBtn = (RadioButton) findViewById(R.id.maleBtn);
        femaleBtn = (RadioButton) findViewById(R.id.female);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = sdf2.format(new Date());
        todayDate = todayDate + "T00:00:00+00:00";



        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.activitylevel, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevel.setAdapter(spinnerAdapter);
        activityLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                 activityLevelTxt = parent.getItemAtPosition(position).toString();
                if(activityLevelTxt != null){
                   // Toast.makeText(parent.getContext(), "Activity Level" + activityLevelTxt,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dobEditTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(signUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        loginBtn = (Button) findViewById(R.id.button2);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstNameEditTxt.length() == 0 || lastNameEditTxt.length() == 0 || emailEditTxt.length() == 0 || userNameTxt.length() == 0 || passwordEdittxt.length() == 0 || heightEditTxt.length()==0 || weightEditTxt.length()==0 || addressEditTxt.length() == 0 || postcodeEditTxt.length() == 0 || stepsPerMileTxt.length()==0 || dobEditTxt.length() == 0){
                    Toast.makeText(signUpActivity.this, "Please enter the values!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String fname = firstNameEditTxt.getText().toString();
                    String lname = lastNameEditTxt.getText().toString();
                    String email = emailEditTxt.getText().toString();
                    String username = userNameTxt.getText().toString();

                    String passwordToHash = passwordEdittxt.getText().toString();
                    String generatedPassword = null;
                    try {
                        // Create MessageDigest instance for MD5
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        //Add password bytes to digest
                        md.update(passwordToHash.getBytes());
                        //Get the hash's bytes
                        byte[] bytes = md.digest();
                        //This bytes[] has bytes in decimal format;
                        //Convert it to hexadecimal format
                        StringBuilder sb = new StringBuilder();
                        for(int i=0; i< bytes.length ;i++)
                        {
                            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                        }
                        //Get complete hashed password in hex format
                        generatedPassword = sb.toString();
                    }
                    catch (NoSuchAlgorithmException e)
                    {
                        e.printStackTrace();
                    }
                    int genderSelectedID = genderGroup.getCheckedRadioButtonId();
                    RadioButton gender = (RadioButton) findViewById(genderSelectedID);

                    String height = heightEditTxt.getText().toString();
                    String weight = weightEditTxt.getText().toString();
                    String address = addressEditTxt.getText().toString();
                    String postcode = postcodeEditTxt.getText().toString();
                    String dob = dobEditTxt.getText().toString()+"T00:00:00+10:00";
                    String stepsPerMile = stepsPerMileTxt.getText().toString();
                    String activity = activityLevelTxt;

                    String genderTxt="";

                    if(maleBtn.isChecked()){
                        genderTxt = "Male";
                    }
                    else if(femaleBtn.isChecked()){
                        genderTxt = "Female";

                    }

                    checkData checkData = new checkData();
                    checkData.execute(email);

                    checkUsername checkUsername = new checkUsername();
                    checkUsername.execute(username);

                    if(userNameThere == false && emailThere == false){
                        createUser userCreate = new createUser();
                        userCreate.execute(fname,lname,email,height,weight,address,postcode,dob,stepsPerMile,activity,genderTxt,generatedPassword,todayDate, username);
                    }
                    //need to check if email exsists
                }



            }




        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dobEditTxt.setText(sdf.format(myCalendar.getTime()));
    }

    private class checkData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {


            // return
            String emailData = RestWS.checkEmail(params[0]);



            return emailData;
        }

        @Override
        protected void onPostExecute(String result) {

            if(result.equals("[]")){

                emailThere = false;
            }
            else{
                emailThere = true;
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        signUpActivity.this);
                alertDialogBuilder.setTitle("Email Already Exsists!")
                        .setMessage("Oops! Looks like this email is already registered. Please enter another email-id")
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
            }


            // show it

            Log.d("POST", result);

        }

    }

    private class checkUsername extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {


            // return
            String usernameData = RestWS.checkUsername(params[0]);



            return usernameData;
        }

        @Override
        protected void onPostExecute(String result) {

            if(result.equals("[]")){

                userNameThere = false;
            }
            else{
                userNameThere = true;
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        signUpActivity.this);
                alertDialogBuilder.setTitle("Username Already Exsists!")
                        .setMessage("Oops! Looks like this username is already registered. Please enter another username")
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
            }


            // show it

            Log.d("POST", result);

        }

    }

    private class createUser extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            String count = RestWS.getuserId();
            String userId =  String.valueOf(Integer.parseInt(count) + 1);

            User user = new User(params[0],params[1],params[2],params[3],params[4], params[5], params[6], params[7], params[8], params[9], params[10],userId);

           // return
            RestWS.createUser(user);


            UserCredential uCred = new UserCredential(params[11],params[12], user, params[13]);
            //TODO - Usercred table

            RestWS.userCred(uCred);


            return "User Added ";
        }

        @Override
        protected void onPostExecute(String result) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    signUpActivity.this);
            alertDialogBuilder.setTitle("Account Created Successfully!")
                    .setMessage("Congrats on creating your account! Please login to ge started!")
                    .setCancelable(false)
                    .setPositiveButton("Okay",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent intent = new Intent(signUpActivity.this, loginActivity.class);
                                    startActivity(intent);
                                }
                            })
                    ;
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            Log.d("POST", result);

        }

    }


}
