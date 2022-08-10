package edu.pdx.cs410j.camilo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

public class DisplayPhoneBill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_phone_bill);

        PhoneBill foundBill = (PhoneBill) getIntent().getSerializableExtra("PHONE_BILL");
        TextView custName = findViewById(R.id.customerName);

        custName.setText("Customer: " + foundBill.getCustomer());

        ListView listView = findViewById(R.id.listOfPhoneBills);
        ArrayAdapter<String> phCalls = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        Collection<PhoneCall> theList = foundBill.getPhoneCalls();
        for (PhoneCall aCall : theList) {
            String phDesc = "Caller: " + aCall.getCaller() +
                    ", Callee: " + aCall.getCallee() +
                    "\nCall Start: " + aCall.getBeginTimeString() +
                    "\nCall End:  " + aCall.getEndTimeString();
            phCalls.add(phDesc);
        }
//        phCalls.addAll(foundBill.getPhoneCalls());
        listView.setAdapter(phCalls);
    }

    public void exitClick(View view) {
        finish();
    }
}