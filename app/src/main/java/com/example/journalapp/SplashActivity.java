package com.example.journalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 5000;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView splashLogo = findViewById(R.id.splash_logo);
        Animation splashAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        splashLogo.startAnimation(splashAnimation);
        mediaPlayer = MediaPlayer.create(this, R.raw.splashmusic);
        mediaPlayer.start();

        new Handler().postDelayed(() -> {
            // Check if the user is logged in
            boolean isLoggedIn = checkUserLoggedIn();
            mediaPlayer.stop();
            mediaPlayer.release();

            if (isLoggedIn) {
                // If logged in, go to MainActivity
                Intent mainIntent = new Intent(SplashActivity.this, JournalListActivity.class);
                startActivity(mainIntent);
            } else {
                // If not logged in, go to LoginActivity
                Intent loginIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(loginIntent);
            }
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }

    private boolean checkUserLoggedIn() {
        // Implement your logic here to check if the user is logged in.
        // This might involve checking shared preferences, a database, or some other method of storing login state.
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }
}
