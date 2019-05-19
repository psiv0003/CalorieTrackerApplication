package poornima.com.calorietrackerapplication.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import poornima.com.calorietrackerapplication.Fragments.CalTrackerScreen;
import poornima.com.calorietrackerapplication.Fragments.DailyDietScreen;
import poornima.com.calorietrackerapplication.Fragments.MapScreen;
import poornima.com.calorietrackerapplication.Fragments.ReportScreen;
import poornima.com.calorietrackerapplication.Fragments.StepsScreen;
import poornima.com.calorietrackerapplication.Fragments.dashboardScreen;
import poornima.com.calorietrackerapplication.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String email, firstName, calorieGoal;
    Bundle BundleUD;
    Bundle userDetailsBundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent=getIntent();
        BundleUD=intent.getExtras();

        SharedPreferences.Editor editor = MainActivity.this.getSharedPreferences("userDetails", MODE_PRIVATE).edit();
        editor.putString("name",  BundleUD.getString("name"));
        editor.putString("email",  BundleUD.getString("email"));
        editor.putString("user_id",  BundleUD.getString("user_id"));

        editor.putString("user_id", BundleUD.getString("userid"));
        editor.putString("activitylevel", BundleUD.getString("activitylevel"));
        editor.putString("address", BundleUD.getString("address"));
        editor.putString("dob", BundleUD.getString("dob"));
       // editor.putString("password", BundleUD.getString("dob"));

        editor.putString("email", BundleUD.getString("email"));
        editor.putString("gender", BundleUD.getString("gender"));
        editor.putString("height", BundleUD.getString("height"));
        editor.putString("name", BundleUD.getString("name"));
        editor.putString("postcode", BundleUD.getString("postcode"));
        editor.putString("stepspermile", BundleUD.getString("stepspermile"));
        editor.putString("surname", BundleUD.getString("surname"));
        editor.putString("weight", BundleUD.getString("weight"));

        editor.commit();

      // =bundle.getString("name");

        email = BundleUD.getString("email");
        firstName = BundleUD.getString("name");

        setTitle(R.string.app_name);






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        TextView navUsername = (TextView) drawer.findViewById(R.id.nameNavTxt);
//        TextView emailTxt = (TextView) drawer.findViewById(R.id.emailNavTxt);
//
//        navUsername.setText(firstName);
//        emailTxt.setText(email);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new
                dashboardScreen()).commit();





    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment nextFragment = null;

        int flagVar = 0;


        if (id == R.id.diet) {
            nextFragment = new DailyDietScreen();

        }
        else if (id == R.id.home) {
            nextFragment = new dashboardScreen();

        }else if (id == R.id.steps) {
            nextFragment = new StepsScreen();

        } else if (id == R.id.calTracker) {
            nextFragment = new CalTrackerScreen();


        } else if (id == R.id.report) {

            nextFragment = new ReportScreen();

        } else if (id == R.id.map) {

          //  nextFragment = new MapScreen();
            flagVar = 100;


        }

        if(flagVar==0){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,
                    nextFragment).commit();

        }  else if(flagVar==100){
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }
        //  nextFragment.setArguments(getIntent().getExtras());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        TextView navUsername = (TextView) drawer.findViewById(R.id.nameNavTxt);
        TextView emailTxt = (TextView) drawer.findViewById(R.id.emailNavTxt);

        navUsername.setText(firstName);
        emailTxt.setText(email);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
