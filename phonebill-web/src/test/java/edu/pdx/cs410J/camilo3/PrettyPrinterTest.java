package edu.pdx.cs410J.camilo3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class PrettyPrinterTest {

    SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");

    @Test
    void text01CustomerIsDumpedInTextFormat() throws IOException {
        String customer = "Test Phone Bill";
        PhoneBill bill = new PhoneBill(customer);

        StringWriter sw = new StringWriter();
        PrettyPrinter pretty = new PrettyPrinter(sw);
        pretty.dump(bill);

        String text = sw.toString();
        assertThat(text, containsString("Customer name:\n" + customer));
    }
    @Test
    void test02CanPrettyPrintAPhoneBillWithJustANameToFileWithoutError(@TempDir File tempDir) throws IOException {
        String customer = "Test Phone Bill";
        PhoneBill bill = new PhoneBill(customer);

        // can remove the tempDir here if you want to take a look at the apptbook.txt file
        File textFile = new File(tempDir, "phonebill.txt");
        PrettyPrinter pretty = new PrettyPrinter(new FileWriter(textFile));
        pretty.dump(bill);
    }
    @Test
    void test03CanPrettyPrintAPhoneBillWithMultiplePhoneNumbersToFileWithoutError(@TempDir File tempDir) throws IOException, ParseException {
        String customer = "Test Phone Bill";
        PhoneBill bill = new PhoneBill(customer);

        PhoneCall call1 = new PhoneCall("503-867-5309", "800-666-1234",
                sdf.parse("03/2/2022 2:03 pm"), sdf.parse("3/02/2022 2:57 pm"));
        PhoneCall call2 = new PhoneCall("503-867-5309", "800-666-1234",
                sdf.parse("03/2/2022 12:03 am"), sdf.parse("3/02/2022 1:01 am"));
        PhoneCall call3 = new PhoneCall("503-867-5309", "800-666-1234",
                sdf.parse("03/2/2022 1:03 am"), sdf.parse("3/02/2022 1:37 am"));

        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);
        bill.addPhoneCall(call3);

        // can delete or add tempDir, to first arg of file to be able to look at file,
        // if that's something you want to do.
        File textFile = new File(tempDir, "phonebill.txt");
        PrettyPrinter pretty = new PrettyPrinter(new FileWriter(textFile));
        pretty.dump(bill);
    }

    @Test
    void test04AppointmentBookOwnerIsDumpedInTextFormatMultPhoneNumbers() throws ParseException {
        String customer = "Test Phone Bill";
        PhoneBill bill = new PhoneBill(customer);

        PhoneCall call1 = new PhoneCall("503-867-5309", "800-666-1234",
                sdf.parse("03/2/2022 2:03 pm"), sdf.parse("3/02/2022 2:57 pm"));
        PhoneCall call2 = new PhoneCall("503-867-5309", "800-666-1234",
                sdf.parse("03/2/2022 12:03 am"), sdf.parse("3/02/2022 1:01 am"));
        PhoneCall call3 = new PhoneCall("503-867-5309", "800-666-1234",
                sdf.parse("03/2/2022 1:03 am"), sdf.parse("3/02/2022 1:37 am"));

        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);
        bill.addPhoneCall(call3);

        StringWriter sw = new StringWriter();
        PrettyPrinter pretty = new PrettyPrinter(sw);
        pretty.dump(bill);

        String text = sw.toString();
        assertThat(text, containsString("Customer name:\n" + customer));
        assertThat(text, containsString("Caller:       Callee:       Call Begins:          Call Ends:            Time:"));
        assertThat(text, containsString("------------  ------------  --------------------  --------------------  --------"));
        assertThat(text, containsString("503-867-5309  800-666-1234  Mar 02, 22  12:03 AM  Mar 02, 22  01:01 AM  58 mins"));
        assertThat(text, containsString("503-867-5309  800-666-1234  Mar 02, 22  01:03 AM  Mar 02, 22  01:37 AM  34 mins"));
        assertThat(text, containsString("503-867-5309  800-666-1234  Mar 02, 22  02:03 PM  Mar 02, 22  02:57 PM  54 mins"));
    }
}
