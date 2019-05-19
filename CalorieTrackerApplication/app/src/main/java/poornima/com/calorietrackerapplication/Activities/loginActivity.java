package poornima.com.calorietrackerapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import poornima.com.calorietrackerapplication.R;
import poornima.com.calorietrackerapplication.WebServices.RestWS;

public class loginActivity extends AppCompatActivity {

    EditText usernameEditTxt, passwordEditTxt;
    Button signinBtn;
    TextView signUpTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        usernameEditTxt = (EditText) findViewById(R.id.emailTxt);
        passwordEditTxt = (EditText) findViewById(R.id.passwordTxt);
        signinBtn = (Button) findViewById(R.id.button);
        signUpTxt = (TextView) findViewById(R.id.signupTxt);

        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, signUpActivity.class);
                startActivity(intent);
            }
        });


        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("POST", "click");
                if(usernameEditTxt.length() == 0 || passwordEditTxt.length()==0){
                    Toast.makeText(loginActivity.this,"Please enter the values!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String username = usernameEditTxt.getText().toString();
                    String passwordToHash = passwordEditTxt.getText().toString();
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
                    Log.d("PASS", generatedPassword);
                    userLogin login = new userLogin(username, generatedPassword);
                    login.execute();
                }


            }
        });

    }


    private class userLogin extends AsyncTask<Void, Void, String> {
        private String username;
        private String password;

        userLogin(String username, String password){
            this.username = username;
            this.password = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            return RestWS.userLogin(username,password);
        }

        @Override
        protected void onPostExecute(String result) {
//            Log.d("POST", result);



            if(result.equals("[]")){
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        loginActivity.this);
                alertDialogBuilder.setTitle("Incorrect Login Details")
                        .setMessage("Oops! Looks like you have entered the incorrect details. Please check your username and password")
                        .setCancelable(false)
                        .setPositiveButton("Okay",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
            else{
                final JSONArray userDetailsJSonArray;

                try {
                    final JSONArray jsonObject = new JSONArray(result);
                    final int len = jsonObject.length();
                    JSONObject json_obj = jsonObject.getJSONObject(0);

                    String userDetails = json_obj.getString("userid");
                    JSONObject userObj =  new JSONObject(userDetails);
                    Log.d("userdetails ---", userObj.toString());
                    Bundle userDetailsBundle = new Bundle();

                    userDetailsBundle.putString("user_id", userObj.getString("userid"));
                    userDetailsBundle.putString("activitylevel", userObj.getString("activitylevel"));
                    userDetailsBundle.putString("address", userObj.getString("address"));
                    userDetailsBundle.putString("dob", userObj.getString("dob"));
                    userDetailsBundle.putString("email", userObj.getString("email"));
                    userDetailsBundle.putString("gender", userObj.getString("gender"));
                    userDetailsBundle.putString("height", userObj.getString("height"));
                    userDetailsBundle.putString("name", userObj.getString("name"));
                    userDetailsBundle.putString("postcode", userObj.getString("postcode"));
                    userDetailsBundle.putString("stepspermile", userObj.getString("stepspermile"));
                    userDetailsBundle.putString("surname", userObj.getString("surname"));
                    userDetailsBundle.putString("userid", userObj.getString("userid"));
                    userDetailsBundle.putString("weight", userObj.getString("weight"));

                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                    intent.putExtras (userDetailsBundle);
                    startActivity(intent);




                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

        }

    }
}
