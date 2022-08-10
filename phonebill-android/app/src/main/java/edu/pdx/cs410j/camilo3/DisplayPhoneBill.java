package edu.pdx.cs410j.camilo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayPhoneBill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_phone_bill);

        PhoneBill foundBill = (PhoneBill) getIntent().getSerializableExtra("PHONE_BILL");
        TextView custName = findViewById(R.id.customerName);

        custName.setText("Customer: " + foundBill.getCustomer());

        ListView listView = findViewById(R.id.listOfPhoneBills);
        ArrayAdapter<PhoneCall> phCalls = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        phCalls.addAll(foundBill.getPhoneCalls());
        listView.setAdapter(phCalls);
    }
}