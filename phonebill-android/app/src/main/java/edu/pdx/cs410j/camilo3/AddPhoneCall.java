package edu.pdx.cs410j.camilo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.pdx.cs410J.ParserException;

public class AddPhoneCall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_call);
    }

    private void writeBillToFile(PhoneBill aBill) throws IOException {
        File fileDir = this.getFilesDir();
        File billFile = new File(fileDir, aBill.getCustomer());
        FileWriter fw = new FileWriter(billFile);
        PrintWriter pw = new PrintWriter(fw);
        TextDumper td = new TextDumper(pw);
        td.dump(aBill);
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

    public void addPhoneCall(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a", Locale.US);
        EditText customer = findViewById(R.id.custNameLookUp);
        EditText callerNumber = findViewById(R.id.callerPhoneNum);
        EditText calleeNumber = findViewById(R.id.calleePhoneNum);
        EditText startDate = findViewById(R.id.startDateAdd);
        EditText endDate = findViewById(R.id.endDateAdd);
        EditText startTime = findViewById(R.id.startTextTimeAdd);
        EditText endTime = findViewById(R.id.endTextTimeAdd);
        Switch startAmPm = findViewById(R.id.startAMPMAdd);
        Switch endAmPm = findViewById(R.id.endAMPMAdd);

        String customerString = String.valueOf(customer.getText());
        String callerString = String.valueOf(callerNumber.getText());
        String calleeString = String.valueOf(calleeNumber.getText());
        String stDateString = String.valueOf(startDate.getText());
        String edDateString = String.valueOf(endDate.getText());
        String stTimeString = String.valueOf(startTime.getText());
        String edTimeString = String.valueOf(endTime.getText());

        String sdt = stDateString + " " + stTimeString + " " + (startAmPm.isChecked() ? "pm" : "am");
        String edt = edDateString + " " + edTimeString + " " + (endAmPm.isChecked() ? "pm" : "am");

        try {
            Date start = sdf.parse(sdt);
            Date end = sdf.parse(edt);
            Toast.makeText(this, "Customer: " + customer.getText(), Toast.LENGTH_LONG).show();

            PhoneBill aBill = readFromFile(customerString);
            if (aBill == null) {
                aBill = new PhoneBill(customerString);
            }
            PhoneCall aCall = new PhoneCall(callerString, calleeString, start, end);
            aBill.addPhoneCall(aCall);
            writeBillToFile(aBill);
            Toast.makeText(this, "We added this phonecall:\n" + aCall, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            String text = "NAME WAS: " + customerString + "\ncaller: " + callerNumber.getText() + "\ncallee: " + calleeNumber.getText() ;
            String text2 = "Start Date: " + startDate.getText() + " time: " + startTime.getText() + " pm?: " + startAmPm.isChecked();
            String text3 = "End Date: " + endDate.getText() + " time: " + endTime.getText() + " pm?: " + endAmPm.isChecked();
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
            Toast.makeText(this, text2, Toast.LENGTH_LONG).show();
            Toast.makeText(this, text3, Toast.LENGTH_LONG).show();
        }
    }
}