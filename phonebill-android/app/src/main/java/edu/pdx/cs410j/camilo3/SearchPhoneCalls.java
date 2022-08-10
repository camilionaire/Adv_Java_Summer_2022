package edu.pdx.cs410j.camilo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import edu.pdx.cs410J.ParserException;

public class SearchPhoneCalls extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_phone_calls);
    }

    public void searchCallsOnClick(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a", Locale.US);
        EditText customer = findViewById(R.id.custNameSearch);
        EditText startDate = findViewById(R.id.startDateSearch);
        EditText endDate = findViewById(R.id.endDateSearch);
        EditText startTime = findViewById(R.id.startTextTimeSearch);
        EditText endTime = findViewById(R.id.endTextTimeSearch);
        Switch startAmPm = findViewById(R.id.startAMPMSearch);
        Switch endAmPm = findViewById(R.id.endAMPMSearch);

        String customerString = String.valueOf(customer.getText());
        String stDateString = String.valueOf(startDate.getText());
        String edDateString = String.valueOf(endDate.getText());
        String stTimeString = String.valueOf(startTime.getText());
        String edTimeString = String.valueOf(endTime.getText());

        String sdt = stDateString + " " + stTimeString + " " + (startAmPm.isChecked() ? "pm" : "am");
        String edt = edDateString + " " + edTimeString + " " + (endAmPm.isChecked() ? "pm" : "am");
        try {
            if (! PhoneCallChecker.isValidDate(stDateString) || ! PhoneCallChecker.isValidDate(edDateString)) {
                throw new PhoneCallChecker.ImproperDate();
            } else if (! PhoneCallChecker.isValidTime(stTimeString + " " + (startAmPm.isChecked() ? "pm" : "am")) ||
            ! PhoneCallChecker.isValidTime(edTimeString + " " + (endAmPm.isChecked() ? "pm" : "am"))) {
                throw new PhoneCallChecker.ImproperTime();
            }
            Date begin = sdf.parse(sdt);
            Date end = sdf.parse(edt);
            if ( ! PhoneCallChecker.isStartBeforeEnd(begin, end)) {
                throw new PhoneCallChecker.EndIsBeforeStart();
            }

            PhoneBill aBill = readFromFile(customerString);
            if (aBill == null) {
                Toast.makeText(this, "Looks like maybe there's no bill by that name.\n" +
                        "Maybe check your spelling...", Toast.LENGTH_LONG).show();
            } else {
                PhoneBill retBill = new PhoneBill(customerString);
                Collection<PhoneCall> calls = aBill.getPhoneCalls();

                // adds the phone calls to the return bill.
                for (PhoneCall call : calls) {
                    if (time1IsBeforeTime2(begin, call.getBeginTime()) && time1IsBeforeTime2(call.getBeginTime(), end)) {
                        retBill.addPhoneCall(call);
                    }
                }
                Intent intent = new Intent(this, DisplayPhoneBill.class);
                intent.putExtra("PHONE_BILL", retBill);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
                Toast.makeText(this, "Something went wrong\n" + e.getMessage(), Toast.LENGTH_LONG).show();
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

    /**
     * returns true if the start is before or equal to the end
     * false otherwise
     */
    static boolean time1IsBeforeTime2(Date time1, Date time2) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(
                time2.getTime() - time1.getTime());
        return seconds >= 0L;
    }

}