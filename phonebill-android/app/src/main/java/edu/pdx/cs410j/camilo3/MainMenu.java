package edu.pdx.cs410j.camilo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

    public void addAPhoneCall(View view) {
        Toast.makeText(this, "Hello add a phone call!!!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddPhoneCall.class);
        startActivity(intent);
    }

    public void lookUpPhoneBill(View view) {
        Toast.makeText(this, "Hello look up phone bill!!!", Toast.LENGTH_SHORT).show();
    }

    public void searchPhoneBill(View view) {
        Toast.makeText(this, "Hello search phone bill!!!", Toast.LENGTH_SHORT).show();
    }
}