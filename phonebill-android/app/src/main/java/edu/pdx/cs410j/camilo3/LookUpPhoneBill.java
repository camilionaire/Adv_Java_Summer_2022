package edu.pdx.cs410j.camilo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import edu.pdx.cs410J.ParserException;

public class LookUpPhoneBill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up_phone_bill);
    }

    public void lookUpBillClick(View view) {
        EditText name = findViewById(R.id.custNameLookUp);
        String nameString = String.valueOf(name.getText());
        try {
            PhoneBill aBill = readFromFile(nameString);
            Toast.makeText(this, "ourBill: " + aBill, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong when reading the file.",
                    Toast.LENGTH_LONG).show();
        }

    }

    private PhoneBill readFromFile(String aName) throws ParserException, FileNotFoundException {
        File fileDir = this.getFilesDir();
        File maybeBill = new File(fileDir, aName);
        if (! maybeBill.exists()) {
            return null;
        } else {
            // here is where we'd return a phone bill
            FileReader fr = new FileReader(maybeBill);
            TextParser tp = new TextParser(fr);
            return tp.parse();
        }
    }
}