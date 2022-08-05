package edu.pdx.cs410j.camilo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    /** this is called when the readme button is pressed. */
    public void goToReadMe(View view) {
        Intent intent = new Intent(this, DisplayReadMe.class);
        startActivity(intent);
    }
}