package edu.pdx.cs410j.camilo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class DisplayReadMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_read_me);
    }

//    @Override
//    public void onBackPressed() {
//        finish();
//    }

    // copied this over from
    // https://stackoverflow.com/questions/30679133/
    // override-up-button-in-action-bar?answertab=trending#tab-top
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goBack(View view) {
        finish();
    }
}