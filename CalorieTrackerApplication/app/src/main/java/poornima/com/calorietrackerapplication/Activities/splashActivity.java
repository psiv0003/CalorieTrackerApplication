package poornima.com.calorietrackerapplication.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import poornima.com.calorietrackerapplication.R;

public class splashActivity extends AppCompatActivity {
    private final int splash_lenght = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(splashActivity.this,loginActivity.class);
                splashActivity.this.startActivity(mainIntent);
                splashActivity.this.finish();
            }
        }, splash_lenght);
    }
}
