package group.cs2001.lightr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences("group.cs2001.lightr", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences.getBoolean("firstRun", true)) {
                    Intent homeIntent = new Intent(SplashScreen.this, MainStartupSettings.class);
                    startActivity(homeIntent);
                    finish();
                } else {
                    Intent homeIntent = new Intent(SplashScreen.this, MainMenu.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}