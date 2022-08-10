package edu.pdx.cs410j.camilo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
//        PhoneBill aBill = null;
        try {
            PhoneBill aBill = readFromFile(nameString);
            if (aBill == null) {
                Toast.makeText(this, "Looks like maybe there's no bill by that name.\n" +
                        "Maybe check your spelling...", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(this, DisplayPhoneBill.class);
                intent.putExtra("PHONE_BILL", aBill);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong when reading the file.\n" +
                            "Maybe the file was corrupted or improperly formatted?",
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