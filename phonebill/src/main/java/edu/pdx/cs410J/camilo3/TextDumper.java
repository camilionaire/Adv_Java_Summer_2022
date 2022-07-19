package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.AppointmentBookDumper;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * TextDumper class implements PhoneBillParser
 * only has one method, dump.
 */
public class TextDumper implements PhoneBillDumper<PhoneBill> {
  private final Writer writer;

  /**
   * Creates a new <code>TextParser</code>
   *
   * @param writer
   *      the object of the Writer class that the text parser will be writing to
   */
  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  /**
   * @param bill
   *      the bill that dump will be reading from and dumping to writer.
   * parses the phonebill that is given as an argument
   * i didn't think i needed error handling here but am not sure.
   */
  @Override
  public void dump(PhoneBill bill) {
    SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ) {
      pw.println(bill.getCustomer());
      // this may be all I really needed to write in here I think...
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
