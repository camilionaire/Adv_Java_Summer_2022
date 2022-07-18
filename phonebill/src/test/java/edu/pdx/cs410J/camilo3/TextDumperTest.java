package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperTest {

  SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy H:mm a");
  @Test
  void appointmentBookOwnerIsDumpedInTextFormat() {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(bill);

    String text = sw.toString();
    assertThat(text, containsString(customer));
  }

  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);

    File textFile = new File(tempDir, "apptbook.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    TextParser parser = new TextParser(new FileReader(textFile));
    PhoneBill read = parser.parse();
    assertThat(read.getCustomer(), equalTo(customer));
  }
  @Test
  void canParseTextWithPhoneNumbersByTextDumper(@TempDir File tempDir) throws IOException, ParserException, ParseException {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);

    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/2/2022 1:03 am"), sdf.parse("3/15/2022 10:39 am"));
    PhoneCall anotherCall = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/2/2022 1:03 am"), sdf.parse("3/15/2022 10:39 am"));

    bill.addPhoneCall(call);
    bill.addPhoneCall(anotherCall);

    File textFile = new File(tempDir, "apptbook.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    TextParser parser = new TextParser(new FileReader(textFile));
    PhoneBill read = parser.parse();
    assertThat(read.getCustomer(), equalTo(customer));
  }
}
