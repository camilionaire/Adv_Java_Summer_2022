package edu.pdx.cs410j.camilo3;

import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class TextDumper {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  /**
   * @param bill
   *      the bill that dump will be reading from and dumping to writer.
   * parses the phonebill that is given as an argument
   */
  public void dump(PhoneBill bill) {
    SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");
    try (
            PrintWriter pw = new PrintWriter(this.writer)
    ) {
      // prints customer's name
      pw.println(bill.getCustomer());

      // prints out all of the phone calls
      Collection<PhoneCall> theCalls = bill.getPhoneCalls();
      for (PhoneCall aCall : theCalls) {
        pw.println(aCall.getCaller() + " " + aCall.getCallee() +
                " " + sdf.format(aCall.getBeginTime()).toLowerCase()
                + " " + sdf.format(aCall.getEndTime()).toLowerCase());
      }
      pw.flush();
    }
  }
}
