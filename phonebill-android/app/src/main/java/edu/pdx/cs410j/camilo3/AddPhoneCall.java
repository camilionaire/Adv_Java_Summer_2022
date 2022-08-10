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

    /**
     * creates the full on page.
     * @param savedInstanceState // where we came from?...
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_call);
    }

    /**
     * Does all the work to write out the bill
     * @param aBill the bill to be written out
     * @throws IOException if something goes wrong on the computer side
     */
    private void writeBillToFile(PhoneBill aBill) throws IOException {
        File fileDir = this.getFilesDir();
        File billFile = new File(fileDir, aBill.getCustomer());
        FileWriter fw = new FileWriter(billFile);
        PrintWriter pw = new PrintWriter(fw);
        TextDumper td = new TextDumper(pw);
        td.dump(aBill);
    }

    /**
     * reads from a file and returns a phone bill.
     * @param aName the name of the file and customer name
     * @return the phone bill or null.
     * @throws ParserException if the file is misformatted
     * @throws FileNotFoundException couldn't find the file I guess...
     */
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

    /**
     * Adds a phone call.  I'm sure there's an easier way.
     * @param view where we are coming from.
     */
    public void addPhoneCall(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a", Locale.US);
        EditText customer = findViewById(R.id.custNameAdd);
        EditText callerNumber = findViewById(R.id.callerPhoneNumAdd);
        EditText calleeNumber = findViewById(R.id.calleePhoneNumAdd);
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

            if (! PhoneCallChecker.isValidPhoneNumber(callerString) || ! PhoneCallChecker.isValidPhoneNumber(calleeString)) {
                throw new PhoneCallChecker.ImproperPhoneNumber();
            } else if (! PhoneCallChecker.isValidDate(stDateString) || ! PhoneCallChecker.isValidDate(edDateString)) {
                throw new PhoneCallChecker.ImproperDate();
            } else if (! PhoneCallChecker.isValidTime(stTimeString + " " + (startAmPm.isChecked() ? "pm" : "am")) ||
                    ! PhoneCallChecker.isValidTime(edTimeString + " " + (endAmPm.isChecked() ? "pm" : "am"))) {
                throw new PhoneCallChecker.ImproperTime();
            }
            Date start = sdf.parse(sdt);
            Date end = sdf.parse(edt);

            if ( ! PhoneCallChecker.isStartBeforeEnd(start, end)) {
                throw new PhoneCallChecker.EndIsBeforeStart();
            }
            ///// end of checks, everything else should be good minus system errors.

            PhoneBill aBill = readFromFile(customerString);
            if (aBill == null) {
                aBill = new PhoneBill(customerString);
            }

            PhoneCall aCall = new PhoneCall(callerString, calleeString, start, end);
            aBill.addPhoneCall(aCall);
            // writes and confirms success and exits.
            writeBillToFile(aBill);
            Toast.makeText(this, "We added this phonecall:\n" + aCall + "\nto this bill: " + customerString, Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "There was an error:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}