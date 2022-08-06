package edu.pdx.cs410j.camilo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddPhoneCall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_call);
    }

    public void addPhoneCall(View view) {
        EditText cust = findViewById(R.id.addCustomerName);
//        PhoneCall aCall = new PhoneCall();

        Toast.makeText(this, "NAME WAS: " + cust.getText(), Toast.LENGTH_LONG).show();
    }
}