package cfd.ram.attendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by mansi on 2/2/18.
 */

public class SplashScreen extends Activity {
    int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Starts login activity
                Intent i = new Intent(SplashScreen.this, CategorySelectionActivity.class);
                startActivity(i);

                // close splash activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
