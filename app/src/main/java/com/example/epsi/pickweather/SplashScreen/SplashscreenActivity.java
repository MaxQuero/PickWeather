package com.example.epsi.pickweather.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.epsi.pickweather.Home.MainActivity;
import com.example.epsi.pickweather.R;

public class SplashscreenActivity extends Activity {



    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //Assign the splashscreen_activity_main layout to the SplashScreenActivity
        setContentView(R.layout.splashscreen_activity_main);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashscreenActivity.this, MainActivity.class);
                SplashscreenActivity.this.startActivity(mainIntent);
                //We call finish() function to prevent user to come back to splashScreen
                SplashscreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
