package edu.pdx.cs410j.camilo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayPhoneBill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_phone_bill);

        ListView listView = findViewById(R.id.listOfPhoneBills);

        ArrayAdapter<Integer> sums = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        sums.addAll(1, 2, 3, 4, 5);
        listView.setAdapter(sums);
    }
}